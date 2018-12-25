package io.bitjob.schedule.base.service.impl;

import io.bitjob.dao.TaskMapper;
import io.bitjob.dao.TimerTaskConfigMapper;
import io.bitjob.domain.Task;
import io.bitjob.domain.TaskEnum;
import io.bitjob.domain.TimerTaskConfig;
import io.bitjob.schedule.base.service.TaskExcuteService;
import io.bitjob.service.cache.TimerTaskConfigCache;
import io.bitjob.util.DateUtil;
import io.bitjob.util.GsonUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 创建定时每月、每日或者每小时等类型执行的任务

 * Date: 15-5-7
 * Time: 下午2:51
 */

public class GenerateTimerTaskExecuteServiceImpl implements TaskExcuteService {

    private static final Log log = LogFactory.getLog(GenerateTimerTaskExecuteServiceImpl.class);
    @Resource(name = "taskMapper")
    protected TaskMapper taskMapper;
    @Resource(name = "timerTaskConfigCache")
    TimerTaskConfigCache timerTaskConfigCache;
    @Resource(name = "timerTaskConfigMapper")
    private TimerTaskConfigMapper timerTaskConfigMapper;
    @Autowired
    private PlatformTransactionManager transactionManager;

    public static void main(String[] args) {

        SimpleDateFormat simpleDateFormat = DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        System.out.println(simpleDateFormat.format(date));
        Calendar now = Calendar.getInstance();
        //now.set( now.get(Calendar.YEAR),1,28);
        // now.set(Calendar.YEAR,2014);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.MONTH, 4);
        now.set(Calendar.SECOND, 0);
        // now.set(Calendar.DAY_OF_MONTH,now.get(Calendar.DAY_OF_MONTH)+1);
        now.set(Calendar.DAY_OF_MONTH, 13);
        System.out.println(simpleDateFormat.format(now.getTime()));
        long days = (date.getTime() - now.getTime().getTime()) / (1000 * 60 * 60 * 24);
        System.out.println(days);
        now = Calendar.getInstance();
        System.out.println("年: " + now.get(Calendar.YEAR));
        System.out.println("月: " + (now.get(Calendar.MONTH) + 1) + "");
        System.out.println("日: " + now.get(Calendar.DAY_OF_MONTH));
        System.out.println("时: " + now.get(Calendar.HOUR_OF_DAY));
        System.out.println("分: " + now.get(Calendar.MINUTE));
        System.out.println("秒: " + now.get(Calendar.SECOND));

    }

    @Override
    public boolean process(Object object) throws Exception {
        boolean result = true;
        // 事务控制
        TransactionStatus status = null;
        try {
            SimpleDateFormat yyyymmddhhmmssDateFormat = DateUtil.getDateFormat("yyyyMMddHHmmss");
            Assert.notNull(object);
            TimerTaskConfig timerTaskConfig = (TimerTaskConfig) object;
            Integer taskTimerType = timerTaskConfig.getTaskTimerType();
            TimerTaskConfig timerTaskConfigFromCache = timerTaskConfigCache.timerTaskConfig_taskType_Map.get(taskTimerType);
            if (timerTaskConfigFromCache == null || timerTaskConfigFromCache.getStatus() == 0) {//如果不存在或者停止了，不执行后面的
                return false;
            }
            String dataType = timerTaskConfig.getDataType();
            Calendar now = Calendar.getInstance();
            now.setTime(timerTaskConfig.getBiztime());
            Task timerTask = new Task();//定时任务
            if (dataType.equals("month")) {//每月执行
                now.set(Calendar.MONTH, now.get(Calendar.MONTH) + 1);
                now.set(Calendar.DAY_OF_MONTH, timerTaskConfig.getDay().intValue());
                now.set(Calendar.HOUR_OF_DAY, timerTaskConfig.getHour().intValue());
                now.set(Calendar.MINUTE, timerTaskConfig.getMinute().intValue());
                now.set(Calendar.SECOND, timerTaskConfig.getSecond().intValue());
            } else if (dataType.equals("day")) {//每日执行
                now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) + 1);
                now.set(Calendar.HOUR_OF_DAY, timerTaskConfig.getHour().intValue());
                now.set(Calendar.MINUTE, timerTaskConfig.getMinute().intValue());
                now.set(Calendar.SECOND, timerTaskConfig.getSecond().intValue());
            } else if (dataType.equals("hour")) {//每小时执行
                now.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY) + 1);
                now.set(Calendar.MINUTE, timerTaskConfig.getMinute().intValue());
                now.set(Calendar.SECOND, timerTaskConfig.getSecond().intValue());
            } else if (dataType.equals("minute")) {//每分钟执行
                now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + 1);
                now.set(Calendar.SECOND, timerTaskConfig.getSecond().intValue());
            } else {
                //TODO
            }
            timerTask.setBizTime(timerTaskConfig.getBiztime());
            timerTask.setTaskType(timerTaskConfig.getTaskTimerType());
            timerTask.setTaskStatus(TaskEnum.TaskStatus.init.status());
            StringBuffer timerTaskUUid = new StringBuffer();
            timerTaskUUid.append(timerTaskConfig.getTaskTimerType()).append("_");//taskType
            timerTaskUUid.append(timerTaskConfig.getTaskTimerKey()).append("_");//taskTimerKey
            timerTaskUUid.append(yyyymmddhhmmssDateFormat.format(now.getTime()));//yyyymmddhhmmss
            timerTask.setUuid(timerTaskUUid.toString());
            timerTask.setRetryCount(0);
            Task dataTask = new Task();
            dataTask.setBizTime(timerTask.getBizTime());
            timerTask.setTaskData(GsonUtils.toJson(dataTask));

            // 开始事务
            status = this.initTansactionStatus(transactionManager,
                    TransactionDefinition.PROPAGATION_REQUIRED);
            taskMapper.save(timerTask);//保存定时任务

           /* Calendar mainTaskBizTime = Calendar.getInstance();
            if(dataType.equals("month")){
                mainTaskBizTime.set(Calendar.MONTH,mainTaskBizTime.get(Calendar.MONTH)+1);////月初执行，下一个月
                mainTaskBizTime.set(Calendar.DAY_OF_MONTH,1);
                mainTaskBizTime.set(Calendar.HOUR_OF_DAY,0);
                mainTaskBizTime.set(Calendar.MINUTE,0);
                mainTaskBizTime.set(Calendar.SECOND,0);
            }else if(dataType.equals("day")){
                mainTaskBizTime.set(Calendar.DAY_OF_MONTH,mainTaskBizTime.get(Calendar.DAY_OF_MONTH)+1);//每日凌晨执行，下一天
                mainTaskBizTime.set(Calendar.HOUR_OF_DAY,0);
                mainTaskBizTime.set(Calendar.MINUTE,0);
                mainTaskBizTime.set(Calendar.SECOND,0);
            }else if(dataType.equals("hour")){
                mainTaskBizTime.set(Calendar.HOUR_OF_DAY,mainTaskBizTime.get(Calendar.HOUR_OF_DAY)+1);//下一个小时执行
                mainTaskBizTime.set(Calendar.MINUTE,0);
                mainTaskBizTime.set(Calendar.SECOND,0);
            }else if(dataType.equals("minute")){
                mainTaskBizTime.set(Calendar.MINUTE,mainTaskBizTime.get(Calendar.MINUTE)+1);//下一分钟执行
                mainTaskBizTime.set(Calendar.SECOND,0);
            }else{
                //TODO
            }*/
            timerTaskConfig.setBiztime(now.getTime());
            timerTaskConfigMapper.updateByPrimaryKeySelective(timerTaskConfig);//更新分发任务的下次执行时间
            transactionManager.commit(status);
        } catch (Exception e) {
            log.error("split task exception:" + ExceptionUtils.getStackTrace(e));
            result = false;
            transactionManager.rollback(status);
            throw e;
        } finally {
            return result;
        }
    }

    /**
     * 定义事物
     *
     * @param transactionManager
     * @param propagetion
     * @return
     */
    protected TransactionStatus initTansactionStatus(
            PlatformTransactionManager transactionManager, int propagetion) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();// 事务定义类
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        return transactionManager.getTransaction(def);
    }
}


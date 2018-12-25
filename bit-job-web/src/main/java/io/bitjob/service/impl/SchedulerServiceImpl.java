package io.bitjob.service.impl;

import io.bitjob.domain.Constants;
import io.bitjob.domain.Result;
import io.bitjob.domain.TriggerResult;
import io.bitjob.service.AlarmService;
import io.bitjob.service.SchedulerService;
import io.bitjob.service.listener.SchedulerSignalEnum;
import io.bitjob.service.listener.SchedulerZookConfig;
import io.bitjob.util.GsonUtils;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**

 * Date: 16-11-25
 * Time: 上午9:07
 */
@Service("schedulerService")
public class SchedulerServiceImpl implements SchedulerService {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);
    @Resource(name = "schedulerMap")
    private Map<String, Scheduler> schedulerMap;

    @Override
    public List<Result> doExecute(SchedulerZookConfig zookConfig) {
        logger.info("设置Scheduler开始");
        try {
            Integer signalType = zookConfig.getSignalType();
            if (signalType == SchedulerSignalEnum.STOP_SINGLE_TYPE.type()) {
                Scheduler scheduler = schedulerMap.get(zookConfig.getSchedulerName());
                scheduler.standby();
            }
            if (signalType == SchedulerSignalEnum.START_SINGLE_TYPE.type()) {
                Scheduler scheduler = schedulerMap.get(zookConfig.getSchedulerName());
                scheduler.start();
            }
            if (signalType == SchedulerSignalEnum.STOP_ALL_TYPE.type()) {
                Iterator<String> schedulerNameIt = schedulerMap.keySet().iterator();
                while (schedulerNameIt.hasNext()) {
                    String key = schedulerNameIt.next();
                    Scheduler scheduler = schedulerMap.get(key);
                    scheduler.standby();
                }
            }
            if (signalType == SchedulerSignalEnum.START_ALL_TYPE.type()) {
                Iterator<String> schedulerNameIt = schedulerMap.keySet().iterator();
                while (schedulerNameIt.hasNext()) {
                    String key = schedulerNameIt.next();
                    Scheduler scheduler = schedulerMap.get(key);
                    scheduler.start();
                }
            }
            if (signalType == SchedulerSignalEnum.UPDATE_REPEAT_TYPE.type()) {
                Scheduler scheduler = schedulerMap.get(zookConfig.getSchedulerName());
                updateRepeatInterByName(scheduler, zookConfig.getSchedulerName(), zookConfig.getTriggerName(), zookConfig.getRepeatInterval());
            }
        } catch (Exception e) {
            String msgAlert = " 设置Scheduler相关报错，请处理，具体信息：" + GsonUtils.toJson(zookConfig);
            logger.error("设置Scheduler相关报错:", e);

            AlarmService.alarm(Constants.ALERT_KEY, msgAlert);
        }
        return null;
    }

    @Override
    public List<Result> scheduerDetailList() {
        List<Result> listResult = new ArrayList<Result>();
        try {
            Iterator<String> schedulerNameIt = schedulerMap.keySet().iterator();
            while (schedulerNameIt.hasNext()) {
                Result result = new Result();
                String key = schedulerNameIt.next();
                Scheduler scheduler = schedulerMap.get(key);
                result.setKey(key);
                result.setName(scheduler.getSchedulerName());
                String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
                List<TriggerResult> triggerResultList = new ArrayList<TriggerResult>();
                for (String triggerName : triggerNames) {

                    SimpleTrigger trigger = (SimpleTrigger) scheduler.getTrigger(triggerName, Scheduler.DEFAULT_GROUP);
                    //执行频率
                    long repeatInterval = trigger.getRepeatInterval();
                    TriggerResult triggerResult = new TriggerResult();
                    triggerResult.setTriggerName(triggerName);
                    triggerResult.setRepeatInterval(repeatInterval);
                    triggerResultList.add(triggerResult);
                }

                result.setTriggerResultList(triggerResultList);
                if (scheduler.isStarted()) {
                    result.setStatus("1");
                    if (scheduler.isInStandbyMode()) {
                        result.setStatus("0");
                    }
                } else {
                    result.setStatus("0");
                }
                listResult.add(result);
            }

            return listResult;
        } catch (Exception e) {

            logger.error("查询所有Scheduler详情报错:", e);
            return null;
        }
    }

    private void updateRepeatInterByName(Scheduler scheduler, String schedulerName, String triggerName, String repeatInterval) throws Exception {

        try {

            String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
            for (String triggerNameVar : triggerNames) {
                if (triggerNameVar.equals(triggerName)) {
                    SimpleTrigger trigger = (SimpleTrigger) scheduler.getTrigger(triggerName, Scheduler.DEFAULT_GROUP);
                    trigger.setRepeatInterval(Long.parseLong(repeatInterval));
                    scheduler.rescheduleJob(triggerName, Scheduler.DEFAULT_GROUP, trigger);
                    break;
                }

            }

        } catch (Exception e) {
            throw e;
        }
    }

    public Result notifySchedulerCluster(SchedulerZookConfig zookConfig) {
        Result result = new Result();
        try {
            boolean flag = true;
            result.setStatus("true");
            if (!flag) {
                result.setStatus("false");
                result.setName("通知集群更新失败");
            }
            return result;
        } catch (Exception e) {
            logger.info("通知集群更新失败", e);
            result.setStatus("false");
            result.setName("通知集群更新失败");
            return result;
        } finally {

        }
    }
}

package io.bitjob.biz;

import io.bitjob.biz.thread.TaskTypeThread;
import io.bitjob.domain.TaskServiceConfig;
import io.bitjob.service.cache.TaskServiceConfigCache;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BizScheduleExecutorV2 {
    private static final Log log = LogFactory.getLog(BizScheduleExecutorV2.class);
    @Resource(name = "bizTaskThreadPool")
    private ThreadPoolTaskExecutor bizTaskThreadPool;

    @Resource(name = "commonThreadPool")
    private ThreadPoolTaskExecutor commonThreadPool;

    @Resource(name = "taskServiceConfigCache")
    private TaskServiceConfigCache taskServiceConfigCache;
    /**
     * 存放每个类型，上一次执行的时间
     */
    private ConcurrentHashMap<Integer, Date> taskTypeLastExecuteTimeMap = new ConcurrentHashMap<Integer, Date>();

    public void execute() throws Exception {
        Date now = new Date();
        //log.info("BizScheduleExecutorV2执行时间:" + DateUtil.formatDate(new Date(), "yyyy-mm-dd hh:mm:ss"));
        Map<Integer, TaskServiceConfig> valid_taskServiceConfig_taskType_Map = taskServiceConfigCache.getValid_taskServiceConfig_taskType_Map();
        try {
            for (Integer taskType : valid_taskServiceConfig_taskType_Map.keySet()) {
                Date lastExecuteTime = taskTypeLastExecuteTimeMap.get(taskType);
                if (lastExecuteTime == null) {
                    taskTypeLastExecuteTimeMap.put(taskType, new Date());
                } else {
                    Integer repeatInterval = valid_taskServiceConfig_taskType_Map.get(taskType).getRepeatInterval();//单位是秒
                    if (repeatInterval == null || repeatInterval <= 0) {
                        repeatInterval = 10;
                    }
                    Date nextExecuteTime = DateUtils.addSeconds(lastExecuteTime, repeatInterval);//下次待执行时间(即马上要执行)
                    long milliseconds = nextExecuteTime.getTime() - now.getTime();//下次执行的时间小于当前时间，立即执行
                    if (milliseconds < 0) {
                        taskTypeLastExecuteTimeMap.put(taskType, nextExecuteTime);
                    } else {
                        continue;
                    }
                }
                TaskTypeThread taskTypeThread = new TaskTypeThread(taskType);
                taskTypeThread.setCommonThreadPool(commonThreadPool);
                bizTaskThreadPool.execute(taskTypeThread);
            }
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                log.error("BizScheduleExecutorV2 " + " data is null:" + e);
            } else {
                log.error("BizScheduleExecutorV2 :", e);
            }
            throw e;
        }
    }


}

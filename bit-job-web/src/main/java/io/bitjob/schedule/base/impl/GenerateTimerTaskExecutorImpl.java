package io.bitjob.schedule.base.impl;

import io.bitjob.domain.TimerTaskConfig;
import io.bitjob.schedule.base.executor.AbstractScheduleExecutor;
import io.bitjob.schedule.base.service.TaskExcuteService;
import io.bitjob.schedule.base.thread.SimpleWorkerThread;
import io.bitjob.schedule.base.util.WorkerUtil;
import io.bitjob.service.TimerTaskConfigService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 任务调度--生成定时任务
 *
 * @author yangkuan
 */
public class GenerateTimerTaskExecutorImpl extends AbstractScheduleExecutor<TimerTaskConfig> {

    private static final Log log = LogFactory.getLog(GenerateTimerTaskExecutorImpl.class);

    /**
     * 配置每次批处理个数
     */
    private Integer batchSize = 10;
    /**
     * 线程池
     */
    @Autowired
    private ThreadPoolTaskExecutor taskunLockThreadPool;

    @Resource(name = "generateTimerTaskExecuteService")
    private TaskExcuteService generateTimerTaskExecuteService;

    @Resource(name = "timerTaskConfigService")
    private TimerTaskConfigService timerTaskConfigService;

    @Override
    public void execute() throws Exception {
        try {
            TimerTaskConfig timerTaskConfig = new TimerTaskConfig();
            timerTaskConfig.setStatus(1);//启动状态的
            timerTaskConfig.setBiztime(new Date());
            List<TimerTaskConfig> timerTaskConfigList = timerTaskConfigService.findByBizTime(timerTaskConfig);
            if (timerTaskConfigList != null && timerTaskConfigList.size() > 0) {
                int listSize = timerTaskConfigList.size();
                int batchTimes = WorkerUtil.computeBatchTimes(listSize, batchSize);
                for (int i = 0; i < batchTimes; i++) {
                    SimpleWorkerThread swt = new SimpleWorkerThread();
                    int from = WorkerUtil.computeBatchFromIndex(listSize, batchSize, i);
                    int to = WorkerUtil.computeBatchToIndex(listSize, batchSize, i);
                    swt.setTaskList(timerTaskConfigList.subList(from, to));
                    swt.setSimpleTaskService(generateTimerTaskExecuteService);
                    this.setSimpleWorkerThreadList(taskunLockThreadPool, generateTimerTaskExecuteService, timerTaskConfigList.subList(from, to));
                }
            }
        } catch (Exception e) {
            log.error("子任务调度--生成定时任务异常:" + e);
            throw e;
        }
    }


    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

    public ThreadPoolTaskExecutor getTaskunLockThreadPool() {
        return taskunLockThreadPool;
    }

    public void setTaskunLockThreadPool(ThreadPoolTaskExecutor taskunLockThreadPool) {
        this.taskunLockThreadPool = taskunLockThreadPool;
    }


}

package io.bitjob.schedule.base.impl;

import io.bitjob.domain.Constants;
import io.bitjob.domain.Task;
import io.bitjob.schedule.base.executor.AbstractScheduleExecutor;
import io.bitjob.schedule.base.service.TaskExcuteService;
import io.bitjob.schedule.base.thread.SimpleWorkerThread;
import io.bitjob.schedule.base.util.WorkerUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Date;
import java.util.List;

/**
 * 任务调度--定期重置被锁定的任务
 *
 * @author yangkuan
 */
public class TaskUnLockScheduleExecutorImpl extends AbstractScheduleExecutor<Task> {

    private static final Log log = LogFactory.getLog(TaskUnLockScheduleExecutorImpl.class);
    private final static String METRICS_KEY = Constants.METRICS_ERROR_KEY + "subtaskunlock";
    /**
     * 配置任务最大锁定时间（秒）
     */
    private Integer splitSeconds;
    /**
     * 配置失败重试次数
     */
    private Integer retryCount;
    /**
     * 配置每次批处理个数
     */
    private Integer batchSize;
    /**
     * 线程池
     */
    @Autowired
    private ThreadPoolTaskExecutor taskunLockThreadPool;
    /**
     * 失败重置服务层
     */
    @Autowired
    private TaskExcuteService taskUnLockService;

    @Override
    public void execute() throws Exception {
        try {
            //如果没有配置则使用默认值
            if (splitSeconds == null) {
                splitSeconds = Constants.TASK_CONFIG_TASKLOCK_DEFAULT_MAXSECONDS;
            }
            if (retryCount == null) {
                retryCount = Constants.TASK_CONFIG_DEFAULT_RETRYCOUNT;
            }
            if (batchSize == null) {
                batchSize = Constants.TASK_BATCH_DEFAULT_SIZE;
            }
            Date date = new Date();
            date = new Date(date.getTime() - splitSeconds.intValue() * 1000);//拿splitSeconds秒前锁定的数据
            List<Task> lockTaskList = taskService.selectLockedTask(date, retryCount);
            if (lockTaskList != null && lockTaskList.size() > 0) {
                int listSize = lockTaskList.size();
                int batchTimes = WorkerUtil.computeBatchTimes(listSize, batchSize);
                for (int i = 0; i < batchTimes; i++) {
                    SimpleWorkerThread swt = new SimpleWorkerThread();
                    int from = WorkerUtil.computeBatchFromIndex(listSize, batchSize, i);
                    int to = WorkerUtil.computeBatchToIndex(listSize, batchSize, i);
                    swt.setTaskList(lockTaskList.subList(from, to));
                    swt.setSimpleTaskService(taskUnLockService);
                    this.setSimpleWorkerThreadList(taskunLockThreadPool, taskUnLockService, lockTaskList.subList(from, to));
                }
            }
        } catch (Exception e) {
            log.error("子任务调度--定期重置被锁定的子任务异常:" + e);
            throw e;
        }
    }

    public Integer getSplitSeconds() {
        return splitSeconds;
    }

    public void setSplitSeconds(Integer splitSeconds) {
        this.splitSeconds = splitSeconds;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
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

    public TaskExcuteService getTaskUnLockService() {
        return taskUnLockService;
    }

    public void setTaskUnLockService(TaskExcuteService taskUnLockService) {
        this.taskUnLockService = taskUnLockService;
    }
}

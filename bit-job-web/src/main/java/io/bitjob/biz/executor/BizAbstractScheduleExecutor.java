package io.bitjob.biz.executor;


import io.bitjob.biz.dispatcher.TaskDispatcherService;
import io.bitjob.biz.thread.BizWorkerThread;
import io.bitjob.domain.Task;
import io.bitjob.service.TaskService;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;


public abstract class BizAbstractScheduleExecutor implements ScheduleExecutor {


    /**
     * 配置每次批处理个数
     */
    protected Integer batchSize;


    /**
     * 池化处理
     *
     * @param threadPoolTaskExecutor
     * @param taskService
     * @param taskDispatcher
     * @param list
     */
    protected void setWorkerThreadList(ThreadPoolTaskExecutor threadPoolTaskExecutor, TaskService taskService, TaskDispatcherService taskDispatcher, List<Task> list) {
        BizWorkerThread workerThread = new BizWorkerThread();
        workerThread.setQueue(list);
        workerThread.setTaskService(taskService);
        workerThread.setTaskDispatcher(taskDispatcher);
        threadPoolTaskExecutor.execute(workerThread);
    }


}


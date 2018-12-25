package io.bitjob.schedule.base.executor;


import io.bitjob.schedule.base.service.TaskExcuteService;
import io.bitjob.schedule.base.thread.SimpleWorkerThread;
import io.bitjob.service.impl.TaskServiceImpl;
import io.bitjob.util.dbrouter.bean.DbContextHolder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;


/**
 * 调度执行接口
 *
 * @author qiaoshaohua
 * @ClassName: ScheduleExecutor
 * @Description: TODO(描述 ：)
 * @date 2012-2-9 下午04:26:47
 */
public abstract class AbstractScheduleExecutor<T> implements ScheduleExecutor {
    /**
     * 任务业务层接口
     */

    protected TaskServiceImpl taskService;

    /**
     * 配置每次批处理个数
     */
    protected Integer batchSize;

    protected String tableIndex;

    /**
     * 池化处理
     *
     * @param threadPoolTaskExecutor
     * @param standardTaskService
     * @param list
     */
    protected void setSimpleWorkerThreadList(ThreadPoolTaskExecutor threadPoolTaskExecutor, TaskExcuteService standardTaskService, List<T> list) {
        SimpleWorkerThread workerThread = new SimpleWorkerThread();
        workerThread.setTaskList(list);
        workerThread.setSimpleTaskService(standardTaskService);
        workerThread.setTableIndex(tableIndex);
        threadPoolTaskExecutor.execute(workerThread);
    }

    public TaskServiceImpl getTaskService() {
        return taskService;
    }

    public void setTaskService(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    public String getTableIndex() {
        return tableIndex;
    }

    public void setTableIndex(String tableIndex) {
        this.tableIndex = tableIndex;
        DbContextHolder.setTableIndex(tableIndex);
    }


}


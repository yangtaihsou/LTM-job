package io.bitjob.schedule.base.thread;


import io.bitjob.schedule.base.service.TaskExcuteService;
import io.bitjob.util.dbrouter.bean.DbContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * 简单任务线程
 *
 * @author yangkuan
 * @ClassName: SimpleWorkerThread
 * @Description: 简单任务线程
 * @date 2012-3-20 下午02:20:58
 */
public class SimpleWorkerThread implements Runnable {
    private static final Log log = LogFactory.getLog(SimpleWorkerThread.class);

    /**
     * 简单任务服务接口
     */
    private TaskExcuteService simpleTaskService;

    /**
     * 处理对象列表
     */
    private List<? extends Object> taskList;

    private String tableIndex;

    @Override
    public void run() {
        long t1 = System.currentTimeMillis();
        if (log.isDebugEnabled())
            log.debug("当前线程:" + Thread.currentThread().getName());
        try {
            DbContextHolder.setTableIndex(tableIndex);
            if (taskList != null && taskList.size() > 0) {
                for (Object task : taskList) {
                    simpleTaskService.process(task);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("任务处理异常：" + e);
        }
        long t5 = System.currentTimeMillis();
        //log.info("任务执行时间："+(t5-t1)+"ms");
    }

    public TaskExcuteService getSimpleTaskService() {
        return simpleTaskService;
    }

    public void setSimpleTaskService(TaskExcuteService simpleTaskService) {
        this.simpleTaskService = simpleTaskService;
    }

    public List<? extends Object> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<? extends Object> taskList) {
        this.taskList = taskList;
    }

    public String getTableIndex() {
        return tableIndex;
    }

    public void setTableIndex(String tableIndex) {
        this.tableIndex = tableIndex;
    }
}

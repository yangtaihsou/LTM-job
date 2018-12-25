package io.bitjob.biz.thread;

import io.bitjob.biz.dispatcher.TaskDispatcher;
import io.bitjob.domain.Task;
import io.bitjob.domain.TaskServiceConfig;
import io.bitjob.service.TaskService;
import io.bitjob.service.cache.TaskServiceConfigCache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by yangkuan on 15/5/22.
 */
public class HandlerTaskCallable implements Callable<List<TaskHandlerResult>> {
    private static final Log log = LogFactory.getLog(HandlerTaskCallable.class);
    private List<Task> taskQueue;
    private Integer taskType;

    @Override
    public List<TaskHandlerResult> call() throws Exception {
        List<TaskHandlerResult> taskHandlerResultList = null;//记录执行结果

        long start = System.currentTimeMillis();
        if (taskQueue != null && taskQueue.size() > 0) {
            WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
            TaskService taskService = (TaskService) applicationContext.getBean("taskService");
            TaskDispatcher taskDispatcher = (TaskDispatcher) applicationContext.getBean("taskDispatcher");
            TaskServiceConfigCache taskServiceConfigCache = (TaskServiceConfigCache) applicationContext.getBean("taskServiceConfigCache");
            taskHandlerResultList = new ArrayList<TaskHandlerResult>();
            Boolean batchLockFlag = batchLock(taskService, taskServiceConfigCache.getValidConfigByType(taskType));
            for (Task task : taskQueue) {
                TaskHandlerResult taskHandlerResult = new TaskHandlerResult();
                taskHandlerResult.setTaskuuid(task.getUuid());
                try {
               /* TaskServiceConfig taskServiceConfig = taskServiceConfigCache.taskServiceConfig_taskType_Map.get(((TaskDto) t).getTaskType());
                if(taskServiceConfig==null||taskServiceConfig.getStatus().intValue()==0){//不存在或者没有启动，继续锁定
                    return;
                }*/
                    //任务锁定
                    if (!batchLockFlag) {
                        boolean lockResult = taskService.lock(task);//①
                        //锁定失败进行下一个
                        if (!lockResult) {//②
                            taskHandlerResult.setResult(Boolean.FALSE);
                            taskHandlerResult.setResultmsg("该任务已经被其他机器锁定");
                            log.info(task.getTaskType().toString() + "的该任务被其他机器锁定"); //主要是为了统计锁冲突数量
                            continue;
                        }
                    }

                    boolean resultProcess = taskDispatcher.dispatch(task);//任务执行//③
                    if (resultProcess) {//如果执行成功，则更改任务状态为执行完成
                        taskHandlerResult.setResult(Boolean.TRUE);
                        boolean finishResult = taskService.finish(task);//④
                        taskHandlerResult.setResultmsg("执行任务成功");
                        if (!finishResult) {
                            taskHandlerResult.setResult(Boolean.TRUE);
                            taskHandlerResult.setResultmsg("执行任务成功，但事后更改状态为完成失败");
                        }
                    } else {//一般不会执行到这一步，会直接抛到异常
                        taskHandlerResult.setResult(Boolean.FALSE);
                        taskHandlerResult.setResultmsg("执行任务失败");//目前没有返回详细说明
                    }
                } catch (Exception e) {
                    log.error("HandlerTaskCallable run is error", e);
                    taskHandlerResult.setResult(Boolean.FALSE);
                    taskHandlerResult.setResultmsg("执行任务出现异常:" + e.getMessage());
                } finally {
                    taskHandlerResultList.add(taskHandlerResult);
                }
            }
        }
        //log.info(taskType + "的执行线程耗时：" + (System.currentTimeMillis() - start));
        return taskHandlerResultList;
    }

    /**
     * 对待执行的数据进行批量锁定。更新了部分，也返回true。
     * 那么没有被锁定的数据，会再次被执行。
     * 随意如果要使用这个方法，那么请保证执行的任务是幂等可重试的。
     *
     * @return
     */
    private Boolean batchLock(TaskService taskService, TaskServiceConfig taskServiceConfig) {
        if (taskServiceConfig != null) {
            Integer lockType = taskServiceConfig.getLockType();
            if (lockType == null || lockType == 0) {
                return Boolean.FALSE;
            }
        } else {
            return Boolean.FALSE;
        }
        try {
            if (taskQueue != null) {
                int count = taskQueue.size();
                if (count > 0) {
                    Task firstTask = taskQueue.get(0);
                    Task endTask = taskQueue.get(count - 1);
                    long range = endTask.getId() - firstTask.getId();
                    if (range == (count - 1)) {//如果批量获取的任务，数据范围是一致的，那么批量锁定
                        Integer lockedCount = taskService.batchLock(firstTask.getId(), endTask.getId());
                        if (lockedCount != count) {
                            log.info("任务类型:" + taskType + "。只锁定了部分。HandlerTaskCallable batchLock  count is=" + lockedCount + ",expected count=" + count);
                            if (lockedCount == 0 || lockedCount < range / 2) {
                                return Boolean.FALSE;
                            }
                        }
                        return Boolean.TRUE;
                    }
                }
            }
        } catch (Exception e) {
            log.error("HandlerTaskCallable batchLock is error", e);
            return Boolean.FALSE;
        } finally {
        }
        return Boolean.FALSE;
    }

    public List<Task> getTaskQueue() {
        return taskQueue;
    }

    public void setTaskQueue(List<Task> taskQueue) {
        this.taskQueue = taskQueue;
    }


    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }
}

class TaskHandlerResult {
    String taskuuid;
    Boolean result;
    String resultmsg;

    public String getResultmsg() {
        return resultmsg;
    }

    public void setResultmsg(String resultmsg) {
        this.resultmsg = resultmsg;
    }

    public String getTaskuuid() {
        return taskuuid;
    }

    public void setTaskuuid(String taskuuid) {
        this.taskuuid = taskuuid;
    }

    public Boolean isResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

}
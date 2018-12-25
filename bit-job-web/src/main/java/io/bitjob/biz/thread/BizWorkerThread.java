package io.bitjob.biz.thread;

import io.bitjob.biz.dispatcher.TaskDispatcher;
import io.bitjob.domain.Task;
import io.bitjob.service.TaskService;
import io.bitjob.service.cache.TaskServiceConfigCache;
import io.bitjob.util.GsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;
import java.util.List;

/**

 * Date: 14-7-15
 * Time: 下午3:38
 */

@Deprecated
public class BizWorkerThread<T> implements Runnable {
    private static final Log log = LogFactory.getLog(BizWorkerThread.class);
    @Resource(name = "taskServiceConfigCache")
    TaskServiceConfigCache taskServiceConfigCache;
    private TaskService taskService;
    private TaskDispatcher taskDispatcher;
    private List<T> list;

    public void run() {
        for (T t : list) {
            long startTime = System.currentTimeMillis();
            try {
                //任务锁定
                boolean lockResult = taskService.lock((Task) t);
                //锁定失败进行下一个
                if (!lockResult) {
                    Task task = (Task) t;
                    log.info(task.getTaskType().toString() + "该任务已经被其他机器锁定:" + GsonUtils.toJson(t));
                    continue;
                }
                boolean resultProcess = taskDispatcher.dispatch((Task) t);//任务执行
                if (resultProcess) {//如果执行成功，则更改任务状态为执行完成
                    taskService.finish((Task) t);
                }
            } catch (Exception e) {
                log.error(((Task) t).getUuid() + "BizWorkerThread run is error", e);
                String errorMsg = e.getMessage();
                Task task = (Task) t;
                if (errorMsg != null && !errorMsg.equals("")) {
                    task.setErrorMsg(errorMsg);
                } else {
                    errorMsg = e.getCause().getMessage();
                    task.setErrorMsg(errorMsg);
                }

                taskService.updateErrorMsg((Task) t);

            } finally {
                long endTime = System.currentTimeMillis() - startTime;
                log.info("执行线程" + ((Task) t).getUuid() + "耗时" + (endTime / 1000));
            }
        }
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    public void setQueue(List<T> list) {
        this.list = list;
    }

    public void setTaskDispatcher(TaskDispatcher taskDispatcher) {
        this.taskDispatcher = taskDispatcher;
    }
}

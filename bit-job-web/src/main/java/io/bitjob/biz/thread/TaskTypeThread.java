package io.bitjob.biz.thread;

import com.google.common.collect.HashBasedTable;
import io.bitjob.biz.service.ServerInfoExplore;
import io.bitjob.domain.*;
import io.bitjob.schedule.base.util.WorkerUtil;
import io.bitjob.service.TaskService;
import io.bitjob.service.cache.TaskServiceConfigCache;
import io.bitjob.service.cache.ThreadPoolPersistence;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.Future;

/**

 * Date: 14-7-15
 * Time: 下午3:38
 */
public class TaskTypeThread implements Runnable {
    private static final Log log = LogFactory.getLog(TaskTypeThread.class);
    private Integer taskType;
    private ThreadPoolTaskExecutor commonThreadPool;

    public TaskTypeThread(Integer taskType) {
        this.taskType = taskType;
    }

    @Override
    public void run() {
        WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
        ThreadPoolPersistence threadPoolPersistence = (ThreadPoolPersistence) applicationContext.getBean("threadPoolPersistence");

        Map<String, ThreadPoolTaskExecutor> threadPoolMap = threadPoolPersistence.getThreadPoolMap();
        ThreadPoolTaskExecutor threadPoolTaskExecutor;
        if (threadPoolMap != null && threadPoolMap.get(String.valueOf(taskType)) != null) {
            threadPoolTaskExecutor = threadPoolMap.get(String.valueOf(taskType));
        } else {
            String msgAlert = "任务类型为:" + taskType + "的处理线程池为空!使用通用线程池";
            //log.info(msgAlert);
            // Profiler.businessAlarm(Constants.ALERT_KEY, System.currentTimeMillis(), "任务类型为:" + taskType + "的处理线程池为空!");
            //AlarmService.alarm(Constants.ALERT_KEY, msgAlert);
            threadPoolTaskExecutor = commonThreadPool;
        }
        TaskService taskService = (TaskService) applicationContext.getBean("taskService");
        ServerInfoExplore serverInfoExplore = (ServerInfoExplore) applicationContext.getBean("serverInfoExplore");
        TaskServiceConfigCache taskServiceConfigCache = (TaskServiceConfigCache) applicationContext.getBean("taskServiceConfigCache");
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("taskType", taskType);
        parameterMap.put("taskStatus", TaskEnum.TaskStatus.init.status());
        parameterMap.put("bizTime", new Date());
        int index = limitIndex(serverInfoExplore);
        TaskServiceConfig taskServiceConfig = taskServiceConfigCache.getValidConfigByType(taskType);
        Integer totalSize = taskServiceConfig.getTaskTotalSize();//一次查询的数据总量
        Integer perSize = taskServiceConfig.getTaskSizePerThread();//HandlerTaskCallable处理的数据量
        if (totalSize == null || totalSize.intValue() <= 0) {
            totalSize = 100;
        }

        parameterMap.put("index", index);
        parameterMap.put("instanceCount", serverInfoExplore.instanceCount);
/*
        parameterMap.put("index", index * totalSize);
        parameterMap.put("countSize",  totalSize);*/

        List<Task> listSubTask = taskService.findTaskByMapParm(parameterMap);

        if (listSubTask != null && listSubTask.size() > 0) {
            if (perSize == null || perSize.intValue() <= 0) {
                perSize = Constants.TASK_BATCH_DEFAULT_SIZE;
            }
            int listSize = listSubTask.size();
            int batchTimes = WorkerUtil.computeBatchTimes(listSize,
                    perSize);
            List<Future<List<TaskHandlerResult>>> taskFutureResultList = new ArrayList<Future<List<TaskHandlerResult>>>();
            for (int i = 0; i < batchTimes; i++) {
                int from = WorkerUtil.computeBatchFromIndex(listSize,
                        perSize, i);
                int to = WorkerUtil.computeBatchToIndex(listSize,
                        perSize, i);
                HandlerTaskCallable handlerTaskCallable = new HandlerTaskCallable();
                handlerTaskCallable.setTaskQueue(listSubTask.subList(from, to));//每个callable执行数据的条数是batchSize。默认10条
                handlerTaskCallable.setTaskType(taskType);
                Future<List<TaskHandlerResult>> taskHandlerResultFuture = threadPoolTaskExecutor.submit(handlerTaskCallable);
                taskFutureResultList.add(taskHandlerResultFuture);//结果只是参考使用而已，因为每个callable的batch条数据处理完成需要花费较多时间。
            }
            /**以下代码可以打印日志。**/
  /*          for (Future<List<TaskHandlerResult>> taskHandlerResultFuture : taskFutureResultList) {
                try {
                    List<TaskHandlerResult> listFuture = taskHandlerResultFuture.get(1000, TimeUnit.MILLISECONDS);
                    for (TaskHandlerResult taskHandlerResult : listFuture) {
                        log.info("执行结果,任务类型为:" + taskType + ",uuid=" + taskHandlerResult.getTaskuuid() + ",结果=" + taskHandlerResult.getResultmsg());
                    }
                } catch (Exception e) {
                    log.info("任务类型为:" + taskType + ",获取阻塞线程执行结果失败");
                }
            }*/
        }
    }

    private Integer limitIndex(ServerInfoExplore serverInfoExplore) {
        Integer index = 0;
        try {
            String catalinaBase = System.getProperty("catalina.base");//当前tomcat实例的路径
            InetAddress addr = InetAddress.getLocalHost();
            String localIp = addr.getHostAddress();//本机ip
            //   log.info("得到本实例catalinaBase=" + catalinaBase + ",localIp=" + localIp);
            HashBasedTable<String, String, ServerInstanceInfo> serverInstanceInfoHbase = serverInfoExplore.getServerInstanceInfoHbase();
            if (serverInstanceInfoHbase != null) {
                ServerInstanceInfo serverInstanceInfo = serverInstanceInfoHbase.get(localIp, catalinaBase);
                if (serverInstanceInfo != null) {
                    index = Integer.parseInt(String.valueOf(serverInstanceInfo.getId()));
                    //  log.info("本实例catalinaBase=" + catalinaBase + ",localIp=" + localIp + "的index=" + index);
                } else if (serverInfoExplore.getIndex() != null) {
                    index = serverInfoExplore.getIndex();
                    serverInstanceInfo = serverInstanceInfoHbase.get(localIp, String.valueOf(index));
                    if (serverInstanceInfo != null) {
                        index = Integer.parseInt(String.valueOf(serverInstanceInfo.getId()));
                        // log.info("本实例catalinaBase=" + catalinaBase + ",localIp=" + localIp + "的serverInfoExplore.index=" + index);
                    }
                }
            }
        } catch (Exception e) {
            log.info("得到本实例的查询索引，发生错误:" + e.getMessage());
        }
        return index;
    }


    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public ThreadPoolTaskExecutor getCommonThreadPool() {
        return commonThreadPool;
    }

    public void setCommonThreadPool(ThreadPoolTaskExecutor commonThreadPool) {
        this.commonThreadPool = commonThreadPool;
    }
}

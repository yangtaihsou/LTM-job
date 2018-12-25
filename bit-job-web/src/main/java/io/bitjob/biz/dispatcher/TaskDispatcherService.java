package io.bitjob.biz.dispatcher;

import io.bitjob.domain.Task;
import io.bitjob.domain.TaskServiceConfig;
import io.bitjob.schedule.base.DataHandlerService;
import io.bitjob.service.cache.TaskServiceConfigCache;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

/**
 * 将子任务分发给具体的service处理
 */
@Service("taskDispatcher")
public class TaskDispatcherService implements TaskDispatcher {
    private static final Logger log = Logger.getLogger(TaskDispatcherService.class);

    @Resource(name = "taskServiceConfigCache")
    TaskServiceConfigCache taskServiceConfigCache;

    public boolean dispatch(Task obj) throws Exception {
        boolean result = false;
        long startTime1 = System.currentTimeMillis();
        try {
            Assert.notNull(obj);
            // log.info("serviceDispatcher receive task,uuid="+obj.getUuid()+":"+obj.getTaskData());
            TaskServiceConfig taskServiceConfig = taskServiceConfigCache.taskServiceConfig_taskType_Map.get(obj.getTaskType());
            if (taskServiceConfig == null || taskServiceConfig.getStatus().intValue() == 0) {//不存在或者没有启动，继续锁定。可以去掉了
                return false;
            }
            WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
            DataHandlerService handlerService = (DataHandlerService) applicationContext.getBean(taskServiceConfig.getTaskService());
            if (handlerService != null) {
                Assert.notNull(handlerService);
                result = handlerService.handler(obj, null);
            } else {
                log.error("任务id" + obj.getId() + ",uuid" + obj.getUuid() + ",tasktype" + obj.getTaskType());
            }
        } catch (Exception e) {
            log.error(" TaskDispatcherService error,uuid=" + obj.getUuid(), e);

            throw e;
        } finally {
            long endTime = System.currentTimeMillis() - startTime1;
            //log.info(obj.getUuid() + "分发子任务耗时" + (endTime / 1000));

        }
        return result;
    }


}

package io.bitjob.biz.service;

import io.bitjob.domain.Task;
import io.bitjob.schedule.base.DataHandlerService;
import io.bitjob.service.cache.TaskServiceConfigCache;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**

 * Date: 14-7-30
 * Time: 下午3:34
 */
@Service("mqProductService")
public class NotifyProducerService implements DataHandlerService<Task> {
    private static final Logger log = Logger.getLogger(NotifyProducerService.class);
    @Resource(name = "taskServiceConfigCache")
    TaskServiceConfigCache taskServiceConfigCache;
    private HashMap<String, String> fmqTopicMap;

    @Override
    public boolean handler(Task task, Map<String, Object> paramMap) throws Exception {
        //log.info("FMQ发送任务开始,uuid=" + task.getUuid() + ",taskData=" + task.getTaskData());
        int taskType = task.getTaskType();
        String taskData = task.getTaskData();
        String uuid = task.getUuid();
        String topic = taskServiceConfigCache.taskServiceConfig_taskType_Map.get(taskType).getJmqTopic();
        String app = taskServiceConfigCache.taskServiceConfig_taskType_Map.get(taskType).getJmqApp();
        if (topic != null) {

        }
        return false;
    }
}

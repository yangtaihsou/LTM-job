package io.bitjob.service.cache;

import io.bitjob.dao.TaskServiceConfigMapper;
import io.bitjob.domain.TaskServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**

 * Date: 15-5-12
 * Time: 上午11:13
 */
@Service("taskServiceConfigCache")
public class TaskServiceConfigCache implements Cache {
    private final static Logger log = LoggerFactory.getLogger(TaskServiceConfigCache.class);
    public Map<Integer, TaskServiceConfig> taskServiceConfig_taskType_Map = new LinkedHashMap<Integer, TaskServiceConfig>();
    public Map<Integer, TaskServiceConfig> valid_taskServiceConfig_taskType_Map = new LinkedHashMap<Integer, TaskServiceConfig>();
    public Map<Integer, TaskServiceConfig> taskServiceConfig_taskParentType_Map = new LinkedHashMap<Integer, TaskServiceConfig>();
    public List<Integer> valid_taskTypeList = new ArrayList<Integer>();
    @Resource(name = "taskServiceConfigMapper")
    private TaskServiceConfigMapper taskServiceConfigMapper;
    @Resource(name = "threadPoolPersistence")
    private ThreadPoolPersistence threadPoolPersistence;

    @PostConstruct
    public void init() {
        List<TaskServiceConfig> taskServiceConfigList = taskServiceConfigMapper.queryBySelective(null);
        Map<Integer, TaskServiceConfig> taskServiceConfig_taskType_Map1 = new LinkedHashMap<Integer, TaskServiceConfig>();
        Map<Integer, TaskServiceConfig> valid_taskServiceConfig_taskType_Map1 = new LinkedHashMap<Integer, TaskServiceConfig>();
        Map<Integer, TaskServiceConfig> taskServiceConfig_taskParentType_Map1 = new LinkedHashMap<Integer, TaskServiceConfig>();
        List<Integer> valid_taskTypeList1 = new ArrayList<Integer>();
        for (TaskServiceConfig taskServiceConfig : taskServiceConfigList) {
            taskServiceConfig_taskType_Map1.put(taskServiceConfig.getTaskType(), taskServiceConfig);
            if (taskServiceConfig.getStatus() != null && taskServiceConfig.getStatus() == 1) {//有效
                valid_taskServiceConfig_taskType_Map1.put(taskServiceConfig.getTaskType(), taskServiceConfig);
                valid_taskTypeList1.add(taskServiceConfig.getTaskType());
            }
            Integer taskParentType = taskServiceConfig.getTaskParentType();
            if (taskParentType != null) {
                taskServiceConfig_taskParentType_Map1.put(taskParentType, taskServiceConfig);
            }
        }
        taskServiceConfig_taskType_Map = taskServiceConfig_taskType_Map1;
        taskServiceConfig_taskParentType_Map = taskServiceConfig_taskParentType_Map1;
        valid_taskServiceConfig_taskType_Map = valid_taskServiceConfig_taskType_Map1;
        valid_taskTypeList = valid_taskTypeList1;

        threadPoolPersistence.reload();
    }

    @Override
    public void reload() {
        this.init();
    }

    @Bean(name = "taskServiceConfig_taskParentType_Map")
    public Map<Integer, TaskServiceConfig> getTaskServiceConfig_taskParentType_Map() {
        return taskServiceConfig_taskParentType_Map;
    }

    @Bean(name = "valid_taskServiceConfig_taskType_Map")
    public Map<Integer, TaskServiceConfig> getValid_taskServiceConfig_taskType_Map() {
        return valid_taskServiceConfig_taskType_Map;
    }


    @Bean(name = "valid_taskTypeList")
    public List<Integer> getvalid_taskTypeList() {
        return valid_taskTypeList;
    }

    public Map<Integer, TaskServiceConfig> getTaskServiceConfig_taskType_Map() {
        return taskServiceConfig_taskType_Map;
    }

    public void setTaskServiceConfig_taskType_Map(Map<Integer, TaskServiceConfig> taskServiceConfig_taskType_Map) {
        this.taskServiceConfig_taskType_Map = taskServiceConfig_taskType_Map;
    }

    public TaskServiceConfig getConfigByType(Integer type) {
        return taskServiceConfig_taskType_Map.get(type);
    }

    public TaskServiceConfig getValidConfigByType(Integer type) {
        return valid_taskServiceConfig_taskType_Map.get(type);
    }
}

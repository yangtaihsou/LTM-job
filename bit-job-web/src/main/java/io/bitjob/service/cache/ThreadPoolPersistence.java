package io.bitjob.service.cache;

import io.bitjob.dao.ThreadPoolPropertiesMapper;
import io.bitjob.domain.TaskServiceConfig;
import io.bitjob.domain.ThreadPoolProperties;
import io.bitjob.service.DiscardPolicyService;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**

 * Date: 16-11-16
 * Time: 下午5:47
 */
@Service("threadPoolPersistence")
public class ThreadPoolPersistence implements Cache {

    @Resource(name = "taskServiceConfigCache")
    TaskServiceConfigCache taskServiceConfigCache;

    @Resource(name = "valid_taskServiceConfig_taskType_Map")
    Map<Integer, TaskServiceConfig> valid_taskServiceConfig_taskType_Map;

    @Resource(name = "threadPoolPropertiesMapper")
    private ThreadPoolPropertiesMapper threadPoolPropertiesMapper;
    /*   @Resource(name = "bizRejectionPolicy")
       private DiscardPolicyWithBizScheduleLog bizRejectionPolicy;*/
    private Map<String, ThreadPoolTaskExecutor> threadPoolMap = new HashMap<String, ThreadPoolTaskExecutor>();

    //@PostConstruct
    public void init() {
        Map<Integer, TaskServiceConfig> new_valid_taskServiceConfig_taskType_Map = taskServiceConfigCache.getValid_taskServiceConfig_taskType_Map();
        if (new_valid_taskServiceConfig_taskType_Map != null) {
            valid_taskServiceConfig_taskType_Map = new_valid_taskServiceConfig_taskType_Map;
        }
        List<ThreadPoolProperties> poolPropertiesList = threadPoolPropertiesMapper.findAll();
        if (poolPropertiesList != null) {
            for (ThreadPoolProperties poolProperties : poolPropertiesList) {
                Integer taskType = poolProperties.getTaskType();
                if (valid_taskServiceConfig_taskType_Map.get(taskType) != null) {//有效的任务类型，才创建线程池
                    ThreadPoolTaskExecutor threadPoolTaskExecutor = threadPoolMap.get(String.valueOf(taskType));
                    threadPoolTaskExecutor = createThreadPoolTaskExecutor(threadPoolTaskExecutor, poolProperties, valid_taskServiceConfig_taskType_Map.get(taskType).getTaskTypeDesc());
                    // bizRejectionPolicy.setThreadName(valid_taskServiceConfig_taskType_Map.get(taskType).getTaskTypeDesc());
                    // threadPoolTaskExecutor.setRejectedExecutionHandler(bizRejectionPolicy);
                    threadPoolMap.put(String.valueOf(taskType), threadPoolTaskExecutor);
                } else {
                    ThreadPoolTaskExecutor threadPoolTaskExecutor = threadPoolMap.get(String.valueOf(taskType));
                    if (threadPoolTaskExecutor != null) {
                        threadPoolTaskExecutor.destroy();
                        threadPoolMap.remove(String.valueOf(taskType));
                    }
                }
            }
        }
    }

    private ThreadPoolTaskExecutor createThreadPoolTaskExecutor(ThreadPoolTaskExecutor oldThreadPoolTaskExecutor, ThreadPoolProperties poolProperties, String threadName) {
        Integer corePoolSize = poolProperties.getCorePoolSize();
        Integer maxPoolSize = poolProperties.getMaxPoolSize();
        Integer queueCapacity = poolProperties.getQueueCapacity();
        Integer keepAliveSeconds = poolProperties.getKeepAliveSeconds();
        Boolean allowCoreThreadTimeOut = poolProperties.getAllowCoreThreadTimeOut();
        ThreadPoolTaskExecutor threadPoolTaskExecutor;
        if (oldThreadPoolTaskExecutor == null) {
            threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        } else {
            threadPoolTaskExecutor = oldThreadPoolTaskExecutor;
        }
        if (corePoolSize != null && corePoolSize > 0) {
            threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        } else {
            threadPoolTaskExecutor.setCorePoolSize(10);
        }
        if (maxPoolSize != null && maxPoolSize > 0) {
            threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
            if (maxPoolSize < corePoolSize)
                threadPoolTaskExecutor.setMaxPoolSize(threadPoolTaskExecutor.getCorePoolSize());
        } else {
            threadPoolTaskExecutor.setMaxPoolSize(100);
        }

        if (keepAliveSeconds != null && keepAliveSeconds > 0) {
            threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        } else {
            threadPoolTaskExecutor.setKeepAliveSeconds(5);
        }
        if (queueCapacity != null && queueCapacity > 0) {
            threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        } else {
            threadPoolTaskExecutor.setQueueCapacity(200);
        }
        if (allowCoreThreadTimeOut != null) {
            threadPoolTaskExecutor.setAllowCoreThreadTimeOut(allowCoreThreadTimeOut);
        } else {
            threadPoolTaskExecutor.setAllowCoreThreadTimeOut(Boolean.FALSE);
        }
        if (oldThreadPoolTaskExecutor == null) {
            threadPoolTaskExecutor.initialize();
            DiscardPolicyService discardPolicy = new DiscardPolicyService();
            discardPolicy.setThreadName(threadName);
            threadPoolTaskExecutor.setRejectedExecutionHandler(discardPolicy);
            //也可以对discardPolicyService进行xml多例配置，通过bean赋值异常策略的实现
            //WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
            //DiscardPolicyService handlerService = (DiscardPolicyService) applicationContext.getBean("discardPolicyService);
        } else {
            threadPoolTaskExecutor.destroy();
            threadPoolTaskExecutor.initialize();
        }
        return threadPoolTaskExecutor;
    }


    public Map<String, ThreadPoolTaskExecutor> getThreadPoolMap() {
        return threadPoolMap;
    }


    @Override
    public void reload() {
        //如果任务类型变化，任务的线程池也应该变化。更新此缓存
        init();
    }
}

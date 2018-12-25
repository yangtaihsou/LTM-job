package io.bitjob.service.cache;

import io.bitjob.dao.TimerTaskConfigMapper;
import io.bitjob.domain.TimerTaskConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**

 * Date: 15-5-12
 * Time: 上午11:13
 */
@Service("timerTaskConfigCache")
public class TimerTaskConfigCache implements Cache {
    private final static Logger log = LoggerFactory.getLogger(TimerTaskConfigCache.class);
    public Map<Integer, TimerTaskConfig> timerTaskConfig_taskType_Map = new HashMap<Integer, TimerTaskConfig>();
    @Resource(name = "timerTaskConfigMapper")
    private TimerTaskConfigMapper timerTaskConfigMapper;

    @PostConstruct
    public void init() {
        List<TimerTaskConfig> timerTaskConfigList = timerTaskConfigMapper.queryBySelective(null);
        Map<Integer, TimerTaskConfig> taskServiceConfig_taskType_Map1 = new HashMap<Integer, TimerTaskConfig>();
        for (TimerTaskConfig timerTaskConfig : timerTaskConfigList) {
            taskServiceConfig_taskType_Map1.put(timerTaskConfig.getTaskTimerType(), timerTaskConfig);
        }
        timerTaskConfig_taskType_Map = taskServiceConfig_taskType_Map1;
    }

    @Override
    public void reload() {
        this.init();
    }
}

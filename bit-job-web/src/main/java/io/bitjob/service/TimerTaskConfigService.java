package io.bitjob.service;

import io.bitjob.domain.TimerTaskConfig;

import java.util.List;

public interface TimerTaskConfigService extends BaseService<TimerTaskConfig> {

    public Boolean deleteByUniqueIndextaskTimerType(Integer taskTimerType);

    public Boolean deleteByUniqueIndextaskTimerKey(String taskTimerKey);

    public List<TimerTaskConfig> findByBizTime(TimerTaskConfig timerTaskConfig);


}
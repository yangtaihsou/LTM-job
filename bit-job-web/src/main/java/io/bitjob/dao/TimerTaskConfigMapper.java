package io.bitjob.dao;


import io.bitjob.domain.TimerTaskConfig;
import io.bitjob.util.PageQuery;
import io.bitjob.util.Query;

import java.util.List;

public interface TimerTaskConfigMapper {

    public List<TimerTaskConfig> findAll();

    public long findCount();

    public List<TimerTaskConfig> queryBySelective(Query<TimerTaskConfig> timerTaskConfig);

    public long queryCountBySelective(Query<TimerTaskConfig> timerTaskConfig);

    public TimerTaskConfig queryByPrimaryKey(Long id);

    public int deleteByPrimaryKey(Long id);

    public int updateByPrimaryKeySelective(TimerTaskConfig timerTaskConfig);


    public int update(TimerTaskConfig timerTaskConfig);

    public Long save(TimerTaskConfig timerTaskConfig);

    public List<TimerTaskConfig> queryBySelectiveForPagination(PageQuery<TimerTaskConfig> timerTaskConfig);

    public long queryCountBySelectiveForPagination(PageQuery<TimerTaskConfig> timerTaskConfig);

    public int deleteByUniqueIndextaskTimerType(TimerTaskConfig timerTaskConfig);

    public int deleteByUniqueIndexsubTaskType(TimerTaskConfig timerTaskConfig);

    public int deleteByUniqueIndexsubTaskKey(TimerTaskConfig timerTaskConfig);

    public int deleteByUniqueIndextaskTimerKey(TimerTaskConfig timerTaskConfig);


    public List<TimerTaskConfig> findByBizTime(TimerTaskConfig timerTaskConfig);
}
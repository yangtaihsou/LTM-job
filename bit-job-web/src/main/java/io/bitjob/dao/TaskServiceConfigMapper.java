package io.bitjob.dao;

import io.bitjob.domain.TaskServiceConfig;
import io.bitjob.util.PageQuery;
import io.bitjob.util.Query;

import java.util.List;

public interface TaskServiceConfigMapper {

    public List<TaskServiceConfig> findAll();

    public long findCount();

    public List<TaskServiceConfig> queryBySelective(Query<TaskServiceConfig> qrxtaskserviceconfig);

    public long queryCountBySelective(Query<TaskServiceConfig> qrxtaskserviceconfig);

    public TaskServiceConfig queryByPrimaryKey(Long id);

    public int deleteByPrimaryKey(Long id);

    public int updateByPrimaryKeySelective(TaskServiceConfig qrxtaskserviceconfig);

    public int update(TaskServiceConfig qrxtaskserviceconfig);

    public Long save(TaskServiceConfig qrxtaskserviceconfig);

    public List<TaskServiceConfig> queryBySelectiveForPagination(PageQuery<TaskServiceConfig> qrxtaskserviceconfig);

    public long queryCountBySelectiveForPagination(PageQuery<TaskServiceConfig> qrxtaskserviceconfig);


}
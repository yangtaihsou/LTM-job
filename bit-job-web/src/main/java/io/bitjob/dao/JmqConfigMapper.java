package io.bitjob.dao;

import io.bitjob.domain.JmqConfig;
import io.bitjob.util.PageQuery;
import io.bitjob.util.Query;

import java.util.List;

public interface JmqConfigMapper {


    public List<JmqConfig> queryBySelective(Query<JmqConfig> jmqConfig);

    public Long queryCountBySelective(JmqConfig jmqConfig);

    public JmqConfig queryByPrimaryKey(Long id);


    public Integer save(JmqConfig jmqConfig);

    public List<JmqConfig> queryBySelectiveForPagination(PageQuery<JmqConfig> jmqConfig);

    public Integer updateByPrimaryKey(JmqConfig jmqConfig);


}
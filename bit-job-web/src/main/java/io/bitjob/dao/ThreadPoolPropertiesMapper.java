package io.bitjob.dao;


import io.bitjob.domain.ThreadPoolProperties;
import io.bitjob.util.PageQuery;
import io.bitjob.util.Query;

import java.util.List;

public interface ThreadPoolPropertiesMapper {

    public List<ThreadPoolProperties> findAll();

    public long findCount();

    public List<ThreadPoolProperties> queryBySelective(Query<ThreadPoolProperties> threadPoolProperties);

    public long queryCountBySelective(Query<ThreadPoolProperties> threadPoolProperties);

    public ThreadPoolProperties queryByPrimaryKey(Long id);

    public int deleteByPrimaryKey(Long id);

    public int updateByPrimaryKeySelective(ThreadPoolProperties threadPoolProperties);

    public Long save(ThreadPoolProperties threadPoolProperties);

    public List<ThreadPoolProperties> queryBySelectiveForPagination(PageQuery<ThreadPoolProperties> threadPoolProperties);

    public long queryCountBySelectiveForPagination(PageQuery<ThreadPoolProperties> threadPoolProperties);


}
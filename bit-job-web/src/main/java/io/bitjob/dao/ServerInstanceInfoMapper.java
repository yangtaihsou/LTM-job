package io.bitjob.dao;

import io.bitjob.domain.ServerInstanceInfo;
import io.bitjob.util.PageQuery;
import io.bitjob.util.Query;

import java.util.List;

public interface ServerInstanceInfoMapper {

    public List<ServerInstanceInfo> findAll();

    public long findCount();

    public List<ServerInstanceInfo> queryBySelective(Query<ServerInstanceInfo> serverinstanceinfo);

    public long queryCountBySelective(Query<ServerInstanceInfo> serverinstanceinfo);

    public ServerInstanceInfo queryByPrimaryKey(Long id);

    public int deleteByPrimaryKey(Long id);

    public int updateByPrimaryKeySelective(ServerInstanceInfo serverinstanceinfo);

    public Long save(ServerInstanceInfo serverinstanceinfo);

    public List<ServerInstanceInfo> queryBySelectiveForPagination(PageQuery<ServerInstanceInfo> serverinstanceinfo);

    public long queryCountBySelectiveForPagination(PageQuery<ServerInstanceInfo> serverinstanceinfo);


}
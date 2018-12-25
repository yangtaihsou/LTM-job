package io.bitjob.service.impl;

import io.bitjob.dao.ServerInstanceInfoMapper;
import io.bitjob.domain.ServerInstanceInfo;
import io.bitjob.service.ServerInstanceInfoService;
import io.bitjob.util.PageQuery;
import io.bitjob.util.Query;
import io.bitjob.util.exception.ErpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("serverinstanceinfoService")
public class ServerInstanceInfoServiceImpl implements ServerInstanceInfoService {

    private static final Logger logger = LoggerFactory.getLogger(ServerInstanceInfoServiceImpl.class);
    @Autowired
    private ServerInstanceInfoMapper serverinstanceinfoMapper;

    public List<ServerInstanceInfo> findAll() {
        List<ServerInstanceInfo> serverinstanceinfoList = serverinstanceinfoMapper.findAll();
        return serverinstanceinfoList;
    }

    public Long findCount() {
        Long count = serverinstanceinfoMapper.findCount();
        return count;
    }

    public List<ServerInstanceInfo> queryBySelective(Query<ServerInstanceInfo> serverinstanceinfo) {
        List<ServerInstanceInfo> serverinstanceinfoList = serverinstanceinfoMapper.queryBySelective(serverinstanceinfo);
        return serverinstanceinfoList;
    }

    public Long queryCountBySelective(Query<ServerInstanceInfo> serverinstanceinfo) {
        Long count = serverinstanceinfoMapper.queryCountBySelective(serverinstanceinfo);
        return count;
    }

    public ServerInstanceInfo queryByPrimaryKey(Integer id) {
  /*      ServerInstanceInfo serverinstanceinfo = serverinstanceinfoMapper.queryByPrimaryKey(id);
        return serverinstanceinfo;*/
        return null;
    }

    @Override
    public Boolean deleteByPrimaryKey(Integer id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Boolean deleteByPrimaryKey(Long id) {
        int result = serverinstanceinfoMapper.deleteByPrimaryKey(id);
        return result == 0 ? false : true;

    }

    public Boolean updateByPrimaryKeySelective(ServerInstanceInfo serverinstanceinfo) {
        int result = serverinstanceinfoMapper.updateByPrimaryKeySelective(serverinstanceinfo);
        return result == 0 ? false : true;
    }

    public Boolean save(ServerInstanceInfo serverinstanceinfo) throws Exception {
        try {
            Long id = serverinstanceinfoMapper.save(serverinstanceinfo);
            return id == 0 ? false : true;
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                ErpException erpException = new ErpException("", "请检查是否唯一");
                e = erpException;
            }
            throw e;
        }
    }

    @Override
    public Boolean batchSave(List<ServerInstanceInfo> serverInstanceInfos) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Boolean batchDelete(List<ServerInstanceInfo> serverInstanceInfos) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Boolean batchDeleteAndSave(List<ServerInstanceInfo> saveList, List<ServerInstanceInfo> deleteList) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<ServerInstanceInfo> queryBySelectiveForPagination(PageQuery<ServerInstanceInfo> serverinstanceinfo) {
        List<ServerInstanceInfo> serverinstanceinfoList = serverinstanceinfoMapper.queryBySelective(serverinstanceinfo);
        return serverinstanceinfoList;
    }

    @Override
    public Long queryCountBySelectiveForPagination(PageQuery<ServerInstanceInfo> query) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Long queryCountBySelectiveForPagination(Query<ServerInstanceInfo> serverinstanceinfo) {
        Long count = serverinstanceinfoMapper.queryCountBySelective(serverinstanceinfo);
        return count;
    }


}
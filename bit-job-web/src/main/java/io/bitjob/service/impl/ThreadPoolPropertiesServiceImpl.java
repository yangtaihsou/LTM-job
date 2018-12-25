package io.bitjob.service.impl;

import io.bitjob.dao.ThreadPoolPropertiesMapper;
import io.bitjob.domain.ThreadPoolProperties;
import io.bitjob.service.ThreadPoolPropertiesService;
import io.bitjob.util.PageQuery;
import io.bitjob.util.Query;
import io.bitjob.util.exception.ErpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("threadPoolPropertiesService")
public class ThreadPoolPropertiesServiceImpl implements ThreadPoolPropertiesService {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolPropertiesServiceImpl.class);
    @Autowired
    private ThreadPoolPropertiesMapper threadPoolPropertiesMapper;

    public List<ThreadPoolProperties> findAll() {
        List<ThreadPoolProperties> threadPoolPropertiesList = threadPoolPropertiesMapper.findAll();
        return threadPoolPropertiesList;
    }

    public Long findCount() {
        Long count = threadPoolPropertiesMapper.findCount();
        return count;
    }

    public List<ThreadPoolProperties> queryBySelective(Query<ThreadPoolProperties> threadPoolProperties) {
        List<ThreadPoolProperties> threadPoolPropertiesList = threadPoolPropertiesMapper.queryBySelective(threadPoolProperties);
        return threadPoolPropertiesList;
    }

    public Long queryCountBySelective(Query<ThreadPoolProperties> threadPoolProperties) {
        Long count = threadPoolPropertiesMapper.queryCountBySelective(threadPoolProperties);
        return count;
    }

    public ThreadPoolProperties queryByPrimaryKey(Integer id) {
/*     	ThreadPoolProperties threadPoolProperties = threadPoolPropertiesMapper.queryByPrimaryKey(id);
        return threadPoolProperties;*/
        return null;
    }

    public Boolean deleteByPrimaryKey(Integer id) {
  /*   	int result = threadPoolPropertiesMapper.deleteByPrimaryKey(Long.parseLong(id));
        return result == 0 ? false : true;*/
        return null;
    }

    public Boolean updateByPrimaryKeySelective(ThreadPoolProperties threadPoolProperties) {
        int result = threadPoolPropertiesMapper.updateByPrimaryKeySelective(threadPoolProperties);
        return result == 0 ? false : true;
    }

    public Boolean save(ThreadPoolProperties threadPoolProperties) throws Exception {
        try {
            Long id = threadPoolPropertiesMapper.save(threadPoolProperties);
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
    public Boolean batchSave(List<ThreadPoolProperties> threadPoolPropertieses) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Boolean batchDelete(List<ThreadPoolProperties> threadPoolPropertieses) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Boolean batchDeleteAndSave(List<ThreadPoolProperties> saveList, List<ThreadPoolProperties> deleteList) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<ThreadPoolProperties> queryBySelectiveForPagination(PageQuery<ThreadPoolProperties> threadPoolProperties) {
        List<ThreadPoolProperties> threadPoolPropertiesList = threadPoolPropertiesMapper.queryBySelective(threadPoolProperties);
        return threadPoolPropertiesList;
    }

    public Long queryCountBySelectiveForPagination(PageQuery<ThreadPoolProperties> threadPoolProperties) {
        Long count = threadPoolPropertiesMapper.queryCountBySelective(threadPoolProperties);
        return count;
    }


}
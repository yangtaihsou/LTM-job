package io.bitjob.service.impl;


import io.bitjob.dao.TimerTaskConfigMapper;
import io.bitjob.domain.TimerTaskConfig;
import io.bitjob.service.TimerTaskConfigService;
import io.bitjob.util.PageQuery;
import io.bitjob.util.Query;
import io.bitjob.util.exception.ErpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Service("timerTaskConfigService")
public class TimerTaskConfigServiceImpl implements TimerTaskConfigService {

    private static final Logger logger = LoggerFactory.getLogger(TimerTaskConfigServiceImpl.class);
    @Autowired
    private TimerTaskConfigMapper timerTaskConfigMapper;
    @Autowired
    private PlatformTransactionManager transactionManager;

    public List<TimerTaskConfig> findAll() {
        List<TimerTaskConfig> timerTaskConfigList = timerTaskConfigMapper.findAll();
        return timerTaskConfigList;
    }

    public Long findCount() {
        Long count = timerTaskConfigMapper.findCount();
        return count;
    }

    public List<TimerTaskConfig> queryBySelective(Query<TimerTaskConfig> timerTaskConfig) {
        List<TimerTaskConfig> timerTaskConfigList = timerTaskConfigMapper.queryBySelective(timerTaskConfig);
        return timerTaskConfigList;
    }

    public Long queryCountBySelective(Query<TimerTaskConfig> timerTaskConfig) {
        Long count = timerTaskConfigMapper.queryCountBySelective(timerTaskConfig);
        return count;
    }

    @Override
    public TimerTaskConfig queryByPrimaryKey(Integer id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Boolean deleteByPrimaryKey(Integer id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public TimerTaskConfig queryByPrimaryKey(Long id) {
        TimerTaskConfig timerTaskConfig = timerTaskConfigMapper.queryByPrimaryKey(id);
        return timerTaskConfig;
    }

    public Boolean deleteByPrimaryKey(Long id) {
        int result = timerTaskConfigMapper.deleteByPrimaryKey(id);
        return result == 0 ? false : true;

    }

    public Boolean updateByPrimaryKeySelective(TimerTaskConfig timerTaskConfig) {
        int result = timerTaskConfigMapper.updateByPrimaryKeySelective(timerTaskConfig);
        return result == 0 ? false : true;
    }

    public Boolean save(TimerTaskConfig timerTaskConfig) throws Exception {
        try {
            Long id = timerTaskConfigMapper.save(timerTaskConfig);
            return id == 0 ? false : true;
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                ErpException erpException = new ErpException("", "请检查是否唯一");
                e = erpException;
            }
            throw e;
        }
    }

    public List<TimerTaskConfig> queryBySelectiveForPagination(PageQuery<TimerTaskConfig> timerTaskConfig) {
        List<TimerTaskConfig> timerTaskConfigList = timerTaskConfigMapper.queryBySelective(timerTaskConfig);
        return timerTaskConfigList;
    }

    @Override
    public Long queryCountBySelectiveForPagination(PageQuery<TimerTaskConfig> query) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Long queryCountBySelectiveForPagination(Query<TimerTaskConfig> timerTaskConfig) {
        Long count = timerTaskConfigMapper.queryCountBySelective(timerTaskConfig);
        return count;
    }

    public Boolean deleteByUniqueIndextaskTimerType(Integer taskTimerType) {
        TimerTaskConfig timerTaskConfig = new TimerTaskConfig();
        timerTaskConfig.setTaskTimerType(taskTimerType);
        int result = timerTaskConfigMapper.deleteByUniqueIndextaskTimerType(timerTaskConfig);
        return result == 0 ? false : true;
    }

    public Boolean deleteByUniqueIndextaskTimerKey(String taskTimerKey) {
        TimerTaskConfig timerTaskConfig = new TimerTaskConfig();
        timerTaskConfig.setTaskTimerKey(taskTimerKey);
        int result = timerTaskConfigMapper.deleteByUniqueIndextaskTimerKey(timerTaskConfig);
        return result == 0 ? false : true;
    }

    @Override
    public List<TimerTaskConfig> findByBizTime(TimerTaskConfig timerTaskConfig) {
        return timerTaskConfigMapper.findByBizTime(timerTaskConfig);
    }

    protected TransactionStatus initTansactionStatus(
            PlatformTransactionManager transactionManager, int propagetion) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();// 事务定义类
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        return transactionManager.getTransaction(def);
    }


    @Override
    public Boolean batchSave(List<TimerTaskConfig> timerTaskConfigList) {
        // 事务控制
        TransactionStatus status = null;
        try {
            // 开始事务
            status = this.initTansactionStatus(transactionManager,
                    TransactionDefinition.PROPAGATION_REQUIRED);
            for (TimerTaskConfig timerTaskConfig : timerTaskConfigList) {
                timerTaskConfigMapper.save(timerTaskConfig);
            }
            transactionManager.commit(status);
            return true;
        } catch (Exception e) {
            transactionManager.rollback(status);
            logger.error("批量保存失败", e);
            return false;
        }
    }

    @Override
    public Boolean batchDelete(List<TimerTaskConfig> timerTaskConfigList) {
        // 事务控制
        TransactionStatus status = null;
        try {
            // 开始事务
            status = this.initTansactionStatus(transactionManager,
                    TransactionDefinition.PROPAGATION_REQUIRED);
            for (TimerTaskConfig timerTaskConfig : timerTaskConfigList) {
                timerTaskConfigMapper.deleteByUniqueIndextaskTimerType(timerTaskConfig);
                timerTaskConfigMapper.deleteByUniqueIndextaskTimerKey(timerTaskConfig);
                timerTaskConfigMapper.deleteByUniqueIndextaskTimerType(timerTaskConfig);
            }
            transactionManager.commit(status);
            return true;
        } catch (Exception e) {
            transactionManager.rollback(status);
            logger.error("批量删除失败", e);
            return false;
        }
    }

    @Override
    public Boolean batchDeleteAndSave(List<TimerTaskConfig> saveList, List<TimerTaskConfig> deleteList) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public Boolean batchDeleteAndSave(List<TimerTaskConfig> timerTaskConfigList) {
        // 事务控制
        TransactionStatus status = null;
        try {
            // 开始事务
            status = this.initTansactionStatus(transactionManager,
                    TransactionDefinition.PROPAGATION_REQUIRED);
            for (TimerTaskConfig timerTaskConfig : timerTaskConfigList) {
                timerTaskConfigMapper.deleteByUniqueIndextaskTimerType(timerTaskConfig);
                timerTaskConfigMapper.deleteByUniqueIndextaskTimerKey(timerTaskConfig);
                timerTaskConfigMapper.deleteByUniqueIndextaskTimerType(timerTaskConfig);
                timerTaskConfigMapper.save(timerTaskConfig);
            }
            transactionManager.commit(status);
            return true;
        } catch (Exception e) {
            transactionManager.rollback(status);
            logger.error("批量删除又保存失败", e);
            return false;
        }
    }
}
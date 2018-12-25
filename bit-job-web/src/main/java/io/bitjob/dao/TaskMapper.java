package io.bitjob.dao;

import io.bitjob.domain.Task;
import io.bitjob.util.PageQuery;
import io.bitjob.util.Query;

import java.util.List;
import java.util.Map;

public interface TaskMapper {

    public List<Task> findAll();

    public long findCount();

    public List<Task> queryBySelective(Query<Task> task);

    public long queryCountBySelective(Query<Task> task);

    public Task queryByPrimaryKey(Long id);

    public int deleteByPrimaryKey(Long id);

    public int updateByPrimaryKeySelective(Task task);


    public Long save(Task task);

    public List<Task> queryBySelectiveForPagination(PageQuery<Task> task);

    public long queryCountBySelectiveForPagination(PageQuery<Task> task);

    public int deleteByUniqueIndexuuid(Task task);


    public int finishTask(Task task);


    public int lockTask(Task task);

    public List<Task> selectLockedTask(Task task);

    public List<Task> findByTaskType(Task task);


    public List<Task> findTaskByMapParm(Map map);


    public List<Task> findFromDate(Task task);

    public int unLockTask(Task object);

    public int resetTaskById(Task task);


    public int resetTaskByQuery(PageQuery<Task> task);

    public int updateResultStatus(Task task);


    /**
     * 更新异常信息
     *
     * @param task
     * @return
     * @throws Exception
     */
    public int updateErrorMsg(Task task);

    /**
     * 删除已经完成的任务
     *
     * @return
     */
    public int deleteFinishedTask(Long start, Long end);

    /**
     * 批量锁定任务
     *
     * @param start end
     * @return
     * @throws Exception
     */
    public Integer batchLock(Long start, Long end);
}
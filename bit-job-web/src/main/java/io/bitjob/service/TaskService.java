package io.bitjob.service;

import io.bitjob.domain.Task;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**

 * Date: 14-7-15
 * Time: 下午3:42
 */
public interface TaskService<T> {
    /**
     * 查找锁定的任务
     *
     * @param updateTime
     * @param retryCount
     * @return
     * @throws Exception
     */
    public List<Task> selectLockedTask(Date updateTime, Integer retryCount) throws Exception;


    /**
     * 锁定任务
     *
     * @param task
     * @return
     * @throws Exception
     */
    public boolean lock(Task task) throws Exception;

    /**
     * 根据时间和任务类型，查询任务
     *
     * @param taskType
     * @param status
     * @param startTime
     * @return
     */
    public List<Task> findByTaskType(Integer taskType, Integer status, Date startTime);

    /**
     * 完成任务
     *
     * @param task
     * @return
     * @throws Exception
     */
    boolean finish(Task task) throws Exception;

    /**
     * 查询biztime小于当前时间的任务
     *
     * @param nowTime
     * @param status
     * @return
     */
    public List<Task> findFromDate(Date nowTime, Integer status);


    /**
     * 更新异常信息
     *
     * @param task
     * @return
     * @throws Exception
     */
    public boolean updateErrorMsg(Task task);


    public List<Task> findTaskByMapParm(Map map);


    /**
     * 批量锁定任务
     *
     * @param start end
     * @return
     * @throws Exception
     */
    public Integer batchLock(Long start, Long end) throws Exception;
}

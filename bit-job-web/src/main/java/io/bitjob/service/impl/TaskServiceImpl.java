package io.bitjob.service.impl;

import io.bitjob.dao.TaskMapper;
import io.bitjob.domain.Task;
import io.bitjob.domain.TaskEnum;
import io.bitjob.service.TaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**

 * Date: 15-5-6
 * Time: 下午12:44
 */
@Service("taskService")
public class TaskServiceImpl implements TaskService {

    @Resource(name = "taskMapper")
    protected TaskMapper taskMapper;

    @Override
    public boolean finish(Task task) throws Exception {
        task.setTaskStatus(TaskEnum.TaskStatus.finish.status());
        return taskMapper.finishTask(task) > 0 ? true : false;
    }

    @Override
    public List<Task> findFromDate(Date nowTime, Integer status) {
        Task task = new Task();
        task.setTaskStatus(status);
        task.setBizTime(nowTime);
        return taskMapper.findFromDate(task);
    }

    @Override
    public boolean updateErrorMsg(Task task) {
        return taskMapper.updateErrorMsg(task) == 1 ? true : false;
    }

    @Override
    public List<Task> findTaskByMapParm(Map map) {
        try {
            return taskMapper.findTaskByMapParm(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
        }
    }

    @Override
    public Integer batchLock(Long start, Long end) throws Exception {
        return taskMapper.batchLock(start, end);
    }

    @Override
    public List<Task> selectLockedTask(Date updateTime, Integer retryCount) throws Exception {
        //taskStatus=#taskStatusLock# and updateTime < #updateTime# and retryCount < #retryCount#  limit 0,100
        Task task = new Task();
        task.setLastUpdate(updateTime);
        task.setRetryCount(retryCount);
        return taskMapper.selectLockedTask(task);

    }

    @Override
    public boolean lock(Task task) throws Exception {
        task.setTaskStatus(TaskEnum.TaskStatus.lock.status());
        return taskMapper.lockTask(task) > 0 ? true : false;
    }


    @Override
    public List<Task> findByTaskType(Integer taskType, Integer status, Date startTime) {
        Task task = new Task();
        task.setTaskStatus(status);
        task.setTaskType(taskType);
        task.setBizTime(startTime);
        return taskMapper.findByTaskType(task);
    }

}

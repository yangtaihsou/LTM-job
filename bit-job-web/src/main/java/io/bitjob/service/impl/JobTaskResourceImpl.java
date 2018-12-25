package io.bitjob.service.impl;

import io.bitjob.dao.TaskMapper;
import io.bitjob.domain.Task;
import io.bitjob.export.JobResult;
import io.bitjob.export.ResultCode;
import io.bitjob.export.TaskDto;
import io.bitjob.export.resource.JobTaskResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**

 * Date: 17-12-14
 * Time: 上午10:24
 */
@Service("jobTaskResource")
public class JobTaskResourceImpl implements JobTaskResource {
    private static final Logger logger = LoggerFactory.getLogger(JobTaskResourceImpl.class);
    @Resource(name = "taskMapper")
    protected TaskMapper taskMapper;

    @Override
    public JobResult addTask(TaskDto taskDto) {
        JobResult jobResult = new JobResult();
        jobResult.setSuccess(false);
        if (taskDto == null) {
            jobResult.setCode(ResultCode.DataValidateException.code());
            jobResult.setInfo("对象不能为空。");
            return jobResult;
        }
        if (taskDto.getBizOrderId() == null || taskDto.getBizOrderId().equals("")) {
            jobResult.setCode(ResultCode.DataValidateException.code());
            jobResult.setInfo("bizOrderId不能为空。");
            return jobResult;
        }
        if (taskDto.getUuid() == null || taskDto.getUuid().equals("")) {
            jobResult.setCode(ResultCode.DataValidateException.code());
            jobResult.setInfo("uuid不能为空。");
            return jobResult;
        }
        if (taskDto.getTaskData() == null || taskDto.getTaskData().equals("")) {
            jobResult.setCode(ResultCode.DataValidateException.code());
            jobResult.setInfo("taskData不能为空。");
            return jobResult;
        }
        if (taskDto.getTaskType() == null) {
            jobResult.setInfo("taskType不能为空。");
            jobResult.setCode(ResultCode.DataValidateException.code());
            return jobResult;
        }
        try {

            Task task = buildTask(taskDto);
            Long id = taskMapper.save(task);
            jobResult.setSuccess(true);
            jobResult.setCode(ResultCode.Success.code());
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                logger.info("增加任务重复:" + e.getMessage());
                jobResult.setSuccess(true);
                jobResult.setCode(ResultCode.RfidExistException.code());
                jobResult.setInfo(taskDto.getUuid() + "-uuid重复");
            } else {
                jobResult.setSuccess(false);
                logger.info("增加任务系统异常:" + e.getMessage());
                jobResult.setCode(ResultCode.SystemException.code());
                jobResult.setInfo("系统异常，请联系对方系统！");
            }
        }
        return jobResult;
    }

    private Task buildTask(TaskDto taskDto) {
        Task task = new Task();
        task.setBizOrderId(taskDto.getBizOrderId());
        task.setJrOrderId(taskDto.getJrOrderId());
        task.setTaskData(taskDto.getTaskData());
        task.setTaskType(taskDto.getTaskType());
        task.setUuid(taskDto.getUuid());
        task.setBizTime(new Date());
        if (taskDto.getStartTime() != null) {
            task.setBizTime(taskDto.getStartTime());
        }
        task.setRetryCount(0);
        task.setTaskStatus(0);
        task.setResultStatus(0);
        return task;
    }
}

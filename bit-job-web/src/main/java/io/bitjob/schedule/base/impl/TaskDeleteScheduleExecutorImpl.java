package io.bitjob.schedule.base.impl;

import io.bitjob.dao.TaskMapper;
import io.bitjob.domain.Task;
import io.bitjob.domain.TaskEnum;
import io.bitjob.schedule.base.executor.AbstractScheduleExecutor;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 任务调度--定期重置被锁定的任务
 *
 * @author yangkuan
 */
public class TaskDeleteScheduleExecutorImpl extends AbstractScheduleExecutor<Task> {

    private static final Log log = LogFactory.getLog(TaskDeleteScheduleExecutorImpl.class);

    @Resource(name = "taskMapper")
    protected TaskMapper taskMapper;

    private Integer days;

    @Override
    public void execute() throws Exception {
        try {
            Task task = new Task();
            if (days >= 0) {
                days = -3;
            }
            task.setBizTime(DateUtils.addDays(new Date(), days));
            task.setTaskStatus(TaskEnum.TaskStatus.finish.status());
            List<Task> taskList = taskMapper.findFromDate(task);
            if (taskList != null && taskList.size() > 0) {
                int count = taskList.size();
                Long first = taskList.get(0).getId();
                Long end = taskList.get(count - 1).getId();
                int deleteCount;
                if (first < end) {
                    deleteCount = taskMapper.deleteFinishedTask(first, end);
                } else {
                    deleteCount = taskMapper.deleteFinishedTask(end, first);
                }
                log.info("计划删除任务条数:" + count + ",删除已完成的任务条数:" + deleteCount);
            }
        } catch (Exception e) {
            log.error("子任务调度--删除已完成的任务:" + e);
            throw e;
        }
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}

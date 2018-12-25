package io.bitjob.schedule.base.service.impl;

import io.bitjob.dao.TaskMapper;
import io.bitjob.domain.Task;
import io.bitjob.schedule.base.service.TaskExcuteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**

 * Date: 15-5-6
 * Time: 下午4:00
 */
@Service("taskUnLockService")
public class TaskNoLockExcuteServiceImpl implements TaskExcuteService {

    @Resource(name = "taskMapper")
    protected TaskMapper taskMapper;

    @Override
    public boolean process(Object object) throws Exception {
        return taskMapper.unLockTask((Task) object) > 0 ? true : false;
    }
}

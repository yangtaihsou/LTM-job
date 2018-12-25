package io.bitjob.service;

import io.bitjob.domain.Result;
import io.bitjob.service.listener.SchedulerZookConfig;

import java.util.List;

/**

 * Date: 16-11-25
 * Time: 上午9:06
 */
public interface SchedulerService {

    public List<Result> doExecute(SchedulerZookConfig zookConfig);

    public Result notifySchedulerCluster(SchedulerZookConfig zookConfig);

    public List<Result> scheduerDetailList();
}

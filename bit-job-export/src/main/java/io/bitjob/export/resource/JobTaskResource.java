package io.bitjob.export.resource;

import io.bitjob.export.JobResult;
import io.bitjob.export.TaskDto;

/**

 * Date: 17-12-14
 * Time: 上午10:19
 */
public interface JobTaskResource {
    public JobResult addTask(TaskDto taskDto);
}

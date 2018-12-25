package io.bitjob.biz.dispatcher;

import io.bitjob.domain.Task;

/**

 * Date: 15-5-6
 * Time: 下午6:15
 */
public interface TaskDispatcher {
    /**
     * @param obj
     * @return
     * @throws Exception
     */
    boolean dispatch(Task obj) throws Exception;
}

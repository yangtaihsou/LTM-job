package io.bitjob.schedule.base.service;


/**
 * 简单任务接口
 *
 * @author yangkuan
 * @ClassName: TaskExcuteService
 * @Description: 简单任务接口
 * @date 2012-3-20 下午02:20:25
 */
public interface TaskExcuteService {

    /**
     * 处理
     *
     * @param object
     * @return
     * @throws Exception
     */
    public boolean process(Object object) throws Exception;

}
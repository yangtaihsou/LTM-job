package io.bitjob.schedule.base.executor;

/**
 * 调度执行接口
 *
 * @author qiaoshaohua
 * @ClassName: ScheduleExecutor
 * @Description: TODO(描述 ：)
 * @date 2012-2-9 下午04:26:47
 */
public interface ScheduleExecutor<T> {

    /**
     * Description: quartz入口
     *
     * @throws Exception
     */
    public void execute() throws Exception;
}

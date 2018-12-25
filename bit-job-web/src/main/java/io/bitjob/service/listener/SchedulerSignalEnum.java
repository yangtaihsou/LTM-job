package io.bitjob.service.listener;

/**

 * Date: 14-9-9
 * Time: 下午5:14
 */
public enum SchedulerSignalEnum {

    START_ALL_TYPE(1, "启动所有的Scheduler"),
    STOP_ALL_TYPE(2, "停止所有的Scheduler"),
    START_SINGLE_TYPE(3, "启动指定的Scheduler"),
    STOP_SINGLE_TYPE(4, "停止指定的Scheduler"),
    UPDATE_REPEAT_TYPE(5, "更新scheduler的调度时间"),
    VIEW_TYPE(6, "预览所有的Scheduler");
    private final Integer type;
    private final String desc;

    private SchedulerSignalEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer type() {
        return type;
    }

    public String desc() {
        return desc;
    }

}

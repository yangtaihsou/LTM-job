package io.bitjob.service.listener;


/**

 * Date: 16-11-22
 * Time: 上午10:59
 */
public class SchedulerZookConfig {
    private Integer signalType;//信号类型
    private String triggerName;
    private String schedulerName;
    private String repeatInterval;

    public Integer getSignalType() {
        return signalType;
    }

    public void setSignalType(Integer signalType) {
        this.signalType = signalType;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getSchedulerName() {
        return schedulerName;
    }

    public void setSchedulerName(String schedulerName) {
        this.schedulerName = schedulerName;
    }

    public String getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(String repeatInterval) {
        this.repeatInterval = repeatInterval;
    }
}

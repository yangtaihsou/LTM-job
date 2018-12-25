package io.bitjob.domain;

/**
 * Created with IntelliJ IDEA.
 * User: yangkuan
 * Date: 14-2-12
 * Time: 上午10:54
 * To change this template use File | Settings | File Templates.
 */
public class TriggerResult {
    private String triggerName;
    private long repeatInterval;

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public long getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(long repeatInterval) {
        this.repeatInterval = repeatInterval;
    }
}

package io.bitjob.domain;

import java.util.List;

/**
 *
 */
public class Result {

    /**
     * 调度key
     */
    private String key;
    /**
     * 任务调度描述
     */
    private String name;
    /**
     * 任务调度开启状态
     */
    private String status;

    private List<TriggerResult> triggerResultList;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TriggerResult> getTriggerResultList() {
        return triggerResultList;
    }

    public void setTriggerResultList(List<TriggerResult> triggerResultList) {
        this.triggerResultList = triggerResultList;
    }
}

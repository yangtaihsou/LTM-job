/**
 * Copyright(c) 2004- www.360buy.com
 * io.bitjob.domain.TaskDto.java
 */
package io.bitjob.export;

import java.io.Serializable;
import java.util.Date;

/**

 * @date
 */
public class TaskDto implements Serializable {


    /**
     * 业务单号。不可为空，可以重复
     */
    private String bizOrderId;
    /**
     * 订单号可为空
     */
    private String jrOrderId;

    /**
     * 任务类型 非空
     */
    private Integer taskType;
    /**
     * 任务数据 非空
     */
    private String taskData;
    /**
     * UUID 非空
     */
    private String uuid;
    /**
     * 指定任务开始执行时间。可为空，默认当前系统时间
     */
    private Date startTime;


    public String getBizOrderId() {
        return bizOrderId;
    }

    public void setBizOrderId(String bizOrderId) {
        this.bizOrderId = bizOrderId;
    }

    public String getJrOrderId() {
        return jrOrderId;
    }

    public void setJrOrderId(String jrOrderId) {
        this.jrOrderId = jrOrderId;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public String getTaskData() {
        return taskData;
    }

    public void setTaskData(String taskData) {
        this.taskData = taskData;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
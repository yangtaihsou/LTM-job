/**
 * Copyright(c) 2004- www.360buy.com
 * TimerTaskConfig.java
 */
package io.bitjob.domain;

import java.io.Serializable;
import java.util.Date;

/**

 * @date
 */
public class TimerTaskConfig implements Serializable {


    /**
     *
     */
    private Long id;


    /**
     * 执行任务的类型
     */
    private Integer taskTimerType;


    /**
     * 定时任务标识
     */
    private String taskTimerKey;


    /**
     * 定时任务描述
     */
    private String taskTimerDesc;


    /**
     * 数据类型 month表示每月执行一次，day表示每天执行一次，hour表示每小时执行一次,minute表示每分钟执行
     */
    private String dataType;


    /**
     *
     */
    private Integer year;


    /**
     *
     */
    private Integer month;


    /**
     *
     */
    private Integer day;


    /**
     *
     */
    private Integer hour;


    /**
     *
     */
    private Integer minute;


    /**
     *
     */
    private Integer second;


    /**
     *
     */
    private Date biztime;


    /**
     * 任务状态。0:停止，1:启动
     */
    private Integer status;


    /**
     *
     */
    private Date createtime;


    /**
     *
     */
    private Date lastupdatetime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTaskTimerType() {
        return taskTimerType;
    }

    public void setTaskTimerType(Integer taskTimerType) {
        this.taskTimerType = taskTimerType;
    }

    public String getTaskTimerKey() {
        return taskTimerKey;
    }

    public void setTaskTimerKey(String taskTimerKey) {
        this.taskTimerKey = taskTimerKey;
    }

    public String getTaskTimerDesc() {
        return taskTimerDesc;
    }

    public void setTaskTimerDesc(String taskTimerDesc) {
        this.taskTimerDesc = taskTimerDesc;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public Date getBiztime() {
        return biztime;
    }

    public void setBiztime(Date biztime) {
        this.biztime = biztime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getLastupdatetime() {
        return lastupdatetime;
    }

    public void setLastupdatetime(Date lastupdatetime) {
        this.lastupdatetime = lastupdatetime;
    }


}
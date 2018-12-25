/**
 * Copyright(c) 2004- www.360buy.com
 * TaskServiceConfig.java
 */
package io.bitjob.domain;

import java.io.Serializable;
import java.util.Date;

/**

 * @date
 */
public class TaskServiceConfig implements Serializable {


    /**
     *
     */
    private Long id;


    /**
     * 任务类型
     */
    private Integer taskType;


    /**
     * 任务类型key
     */
    private String taskTypeKey;


    /**
     * 任务类型描述
     */
    private String taskTypeDesc;


    /**
     * serviceId
     */
    private String taskService;


    /**
     * 父任务类型
     */
    private Integer taskParentType;


    /**
     * 父任务类型描述
     */
    private String taskParentTypeDesc;


    /**
     * 任务状态。0停止，1启动
     */
    private Integer status;

    /**
     * 从task表里拉取的数据量
     */
    private Integer taskTotalSize;


    /**
     * 线程池单个核心线程处理的task量。线程池核心线程数量=taskSizePerThread/taskTotalSize
     */
    private Integer taskSizePerThread;
    /**
     * 0:任务线程将数据逐条加上乐观锁,1:任务线程将数据批量锁
     */
    private Integer lockType;
    /**
     * 单位秒。执行频率。每多长时间执行一次
     */
    private Integer repeatInterval;

    /**
     *
     */
    private Date createtime;


    /**
     * 任务状态。0:停止，1:启动
     */
    private Date lastupdatetime;


    private String jmqApp;

    private String jmqTopic;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public String getTaskTypeKey() {
        return taskTypeKey;
    }

    public void setTaskTypeKey(String taskTypeKey) {
        this.taskTypeKey = taskTypeKey;
    }

    public String getTaskTypeDesc() {
        return taskTypeDesc;
    }

    public void setTaskTypeDesc(String taskTypeDesc) {
        this.taskTypeDesc = taskTypeDesc;
    }

    public String getTaskService() {
        return taskService;
    }

    public void setTaskService(String taskService) {
        this.taskService = taskService;
    }

    public Integer getTaskParentType() {
        return taskParentType;
    }

    public void setTaskParentType(Integer taskParentType) {
        this.taskParentType = taskParentType;
    }

    public String getTaskParentTypeDesc() {
        return taskParentTypeDesc;
    }

    public void setTaskParentTypeDesc(String taskParentTypeDesc) {
        this.taskParentTypeDesc = taskParentTypeDesc;
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


    public Integer getTaskTotalSize() {
        return taskTotalSize;
    }

    public void setTaskTotalSize(Integer taskTotalSize) {
        this.taskTotalSize = taskTotalSize;
    }

    public Integer getTaskSizePerThread() {
        return taskSizePerThread;
    }

    public void setTaskSizePerThread(Integer taskSizePerThread) {
        this.taskSizePerThread = taskSizePerThread;
    }

    public Integer getLockType() {
        return lockType;
    }

    public void setLockType(Integer lockType) {
        this.lockType = lockType;
    }

    public Integer getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(Integer repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public String getJmqApp() {
        return jmqApp;
    }

    public void setJmqApp(String jmqApp) {
        this.jmqApp = jmqApp;
    }

    public String getJmqTopic() {
        return jmqTopic;
    }

    public void setJmqTopic(String jmqTopic) {
        this.jmqTopic = jmqTopic;
    }
}
package io.bitjob.domain;

public class TaskWebSearchVo extends BaseBean {

    private static final long serialVersionUID = -3895674872183304002L;
    private Integer taskId;
    private Integer taskStatus;
    private String taskType;
    private String beginTime;
    private String endTime;
    private Integer operatorStatus;
    private Integer retryCount;

    /**
     * 接收到的流水号
     */
    private String reqNo;

    /**
     * 父申请单号
     */
    private String parentRfId;

    /**
     * 生成的申请单号
     */
    private String rfId;

    /**
     * 识别码
     */
    private String appId;

    private String resultStatus;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getOperatorStatus() {
        return operatorStatus;
    }

    public void setOperatorStatus(Integer operatorStatus) {
        this.operatorStatus = operatorStatus;
    }


    public String getReqNo() {
        return reqNo;
    }

    public void setReqNo(String reqNo) {
        this.reqNo = reqNo;
    }

    public String getParentRfId() {
        return parentRfId;
    }

    public void setParentRfId(String parentRfId) {
        this.parentRfId = parentRfId;
    }

    public String getRfId() {
        return rfId;
    }

    public void setRfId(String rfId) {
        this.rfId = rfId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }
}

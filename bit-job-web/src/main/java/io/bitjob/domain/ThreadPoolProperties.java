package io.bitjob.domain;

import java.util.Date;

/**

 * Date: 16-11-17
 * Time: 上午8:40
 */
public class ThreadPoolProperties {
    private Integer id;
    private Integer taskType;

    private Integer corePoolSize;

    private Integer maxPoolSize;

    private Integer keepAliveSeconds;

    private Boolean allowCoreThreadTimeOut;

    private Integer queueCapacity;

    /**
     * 最后修改时间
     */

    private Date lastUpdate;


    /**
     * 创建时间
     */

    private Date createTime;

    /**
     * 提示应该设置的线程数量
     */
    private Integer warnningCorePoolSize;

    private Long taskCount;//线程池需要执行的任务数量。
    private Long completedTaskCount;//线程池在运行过程中已完成的任务数量。小于或等于taskCount。
    private Integer largestPoolSize;//线程池曾经创建过的最大线程数量。通过这个数据可以知道线程池是否满过。如等于线程池的最大大小，则表示线程池曾经满了。
    private Integer getPoolSize;//线程池的线程数量。如果线程池不销毁的话，池里的线程不会自动销毁，所以这个大小只增不+ getActiveCount：获取活动的线程数。
    private Integer activeCount;
    private Integer queueSize;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public Integer getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(Integer keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public Boolean getAllowCoreThreadTimeOut() {
        return allowCoreThreadTimeOut;
    }

    public void setAllowCoreThreadTimeOut(Boolean allowCoreThreadTimeOut) {
        this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
    }

    public Integer getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(Integer queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getWarnningCorePoolSize() {
        return warnningCorePoolSize;
    }

    public void setWarnningCorePoolSize(Integer warnningCorePoolSize) {
        this.warnningCorePoolSize = warnningCorePoolSize;
    }

    public Long getCompletedTaskCount() {
        return completedTaskCount;
    }

    public void setCompletedTaskCount(Long completedTaskCount) {
        this.completedTaskCount = completedTaskCount;
    }

    public Long getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(Long taskCount) {
        this.taskCount = taskCount;
    }

    public Integer getLargestPoolSize() {
        return largestPoolSize;
    }

    public void setLargestPoolSize(Integer largestPoolSize) {
        this.largestPoolSize = largestPoolSize;
    }

    public Integer getGetPoolSize() {
        return getPoolSize;
    }

    public void setGetPoolSize(Integer getPoolSize) {
        this.getPoolSize = getPoolSize;
    }

    public Integer getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(Integer activeCount) {
        this.activeCount = activeCount;
    }

    public Integer getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(Integer queueSize) {
        this.queueSize = queueSize;
    }
}

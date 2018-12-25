package io.bitjob.service;

import io.bitjob.domain.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 定时任务的线程被拒绝记录日志
 */
@Service("discardPolicyService")
public class DiscardPolicyService extends ThreadPoolExecutor.DiscardPolicy {

    static final AtomicInteger rejectedLogTaskCount = new AtomicInteger(0);
    private static final Log log = LogFactory.getLog(DiscardPolicyService.class);
    private String threadName;

    public DiscardPolicyService() {

    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        int counts = rejectedLogTaskCount.incrementAndGet();
        String msg = String.format("Thread pool 耗尽了!" +
                        " Thread Name: %s, Pool Size: %d (active: %d, core: %d, max: %d, largest: %d), TaskDto: %d (completed: %d), Executor status:(isShutdown:%s, isTerminated:%s, isTerminating:%s), in %s!",
                threadName, e.getPoolSize(), e.getActiveCount(), e.getCorePoolSize(), e.getMaximumPoolSize(), e.getLargestPoolSize(),
                e.getTaskCount(), e.getCompletedTaskCount(), e.isShutdown(), e.isTerminated(), e.isTerminating(), String.valueOf(counts));
        log.info(msg);
        String msgAlert = "定时任务队列已满，任务被拒绝！拒绝总数：" + counts + "，任务：" + threadName;
        // Profiler.businessAlarm(Constants.ALERT_KEY, System.currentTimeMillis(), msgAlert);
        AlarmService.alarm(Constants.ALERT_KEY, msg);
        super.rejectedExecution(r, e);
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }
}

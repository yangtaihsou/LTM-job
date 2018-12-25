package io.bitjob.biz.executor;

import io.bitjob.domain.Constants;
import io.bitjob.service.AlarmService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 定时任务的线程被拒绝记录日志
 */
public class DiscardPolicyWithBizScheduleLog extends ThreadPoolExecutor.DiscardPolicy {

    static final AtomicInteger rejectedLogTaskCount = new AtomicInteger(0);
    private static final Log log = LogFactory.getLog(DiscardPolicyWithBizScheduleLog.class);
    private String threadName;

    public DiscardPolicyWithBizScheduleLog() {

    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        int counts = rejectedLogTaskCount.incrementAndGet();
        String msg = String.format("Thread pool 耗尽了!" +
                        " Thread Name: %s, Pool Size: %d (active: %d, core: %d, max: %d, largest: %d), TaskDto: %d (completed: %d), Executor status:(isShutdown:%s, isTerminated:%s, isTerminating:%s), in %s!",
                threadName, e.getPoolSize(), e.getActiveCount(), e.getCorePoolSize(), e.getMaximumPoolSize(), e.getLargestPoolSize(),
                e.getTaskCount(), e.getCompletedTaskCount(), e.isShutdown(), e.isTerminated(), e.isTerminating(), "");
        log.info(msg);
        log.info("定时任务队列已满，任务被拒绝！拒绝总数：" + counts + "，任务：" + r.toString());
        //Profiler.businessAlarm(Constants.ALERT_KEY, System.currentTimeMillis(), r.toString() + "，已经拒绝的任务个数：" + counts);
        String msgAlert = "旧版本业务定时任务队列已满，任务被拒绝！拒绝总数：" + counts + "，任务：" + threadName;
        AlarmService.alarm(Constants.ALERT_KEY, msgAlert);
        super.rejectedExecution(r, e);
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }
}

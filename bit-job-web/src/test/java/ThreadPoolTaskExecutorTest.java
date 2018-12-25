import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**

 * Date: 16-11-16
 * Time: 下午5:22
 */
public class ThreadPoolTaskExecutorTest {
    public static void main(String[] args) {
        ThreadPoolTaskExecutor ipFinderThreads = new ThreadPoolTaskExecutor();

        ipFinderThreads.setCorePoolSize(20);
        ipFinderThreads.setMaxPoolSize(3000);
        ipFinderThreads.setKeepAliveSeconds(0);
        ipFinderThreads.setQueueCapacity(100);
      /*  ipFinderThreads.execute(new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while(true){
                    i++;
                    System.out.println("this is 线程"+i);
                }


            }
        }));*/
        ipFinderThreads.initialize();
        System.out.println(ipFinderThreads.getCorePoolSize());
        TestWorkerThread workerThread = new TestWorkerThread();
        ipFinderThreads.execute(workerThread);
        ThreadPoolExecutor threadPoolExecutor = ipFinderThreads.getThreadPoolExecutor();
        ipFinderThreads.destroy();
        ipFinderThreads.setCorePoolSize(200);
        ipFinderThreads.initialize();
        System.gc();
        System.out.println(ipFinderThreads.getCorePoolSize());

    }
}


class TestWorkerThread<T> implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}

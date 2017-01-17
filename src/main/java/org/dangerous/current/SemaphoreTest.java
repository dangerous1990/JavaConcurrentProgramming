package org.dangerous.current;

import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2017/1/17.
 */
public class SemaphoreTest {

    final static int MAX_QPS = 10;
    final static Semaphore semaphore = new Semaphore(MAX_QPS);
    final static Semaphore EXCUTE_COUNT = new Semaphore(MAX_QPS);

    public static void main(String[] args) throws Exception {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            public void run() {
                semaphore.release(MAX_QPS -semaphore.availablePermits());
            }
        }, 1000, 1000, TimeUnit.MILLISECONDS);
        //lots of concurrent calls:100 *
        ExecutorService pool = Executors.newFixedThreadPool(100);
        for (int i = 100; i > 0; i--) {
            final int x = i;
            pool.submit(new Runnable() {
                public void run() {
                    for (int j = 1000; j > 0; j--) {
                        semaphore.acquireUninterruptibly(1);
                        remoteCall(x, j);
                    }
                }
            });
        }
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.HOURS);
        System.out.println("DONE");

    }


    private static void remoteCall(int i, int j) {
        System.out.println(String.format("%s - %s: %d %d", new Date(),
                Thread.currentThread(), i, j));
    }


}


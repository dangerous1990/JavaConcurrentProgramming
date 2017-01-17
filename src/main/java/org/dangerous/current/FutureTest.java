package org.dangerous.current;

import java.util.concurrent.*;

/**
 * FutureTask
 * Created by Administrator on 2017/1/17.
 */
public class FutureTest {

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        FutureTask<Integer> futureTask1 = new FutureTask<Integer>(new Callable<Integer>() {
            public Integer call() throws Exception {
                return 1;
            }
        });
        FutureTask<Integer> futureTask2 = new FutureTask<Integer>(new Callable<Integer>() {
            public Integer call() throws Exception {
                return 2;
            }
        });
        es.submit(futureTask1);
        es.submit(futureTask2);
        try {
            Integer a = futureTask1.get();
            Integer b = futureTask2.get();
            System.out.println("等待所有执行完");
            System.out.println(a*b);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        es.shutdown();
    }
}

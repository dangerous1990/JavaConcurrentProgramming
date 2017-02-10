package org.dangerous.current.thread.pool;

import java.util.concurrent.*;

/**
 * 配置一个线程池，内容大小 coreSize =1 ,maxiumPoolSize =2 ,queueSize =1,
 * 提交3个任务，每个任务时间执行时间都很长。执行不完，三个线程如何运行。
 * A任务 判断当前workersHashSet的size < coreSize,创建一个线程加入workersHashSet,启动线程。
 * B任务 判断当前workersHashSet的size < coreSize 不成立，加入workqueue队列
 * 双重检查？
 * C任务 队列已满，workers Size < maxiumPoolSize 创一个线程加入wokersHashSet,启动线程。
 * 扩展（加入D任务）
 * D任务 D任务 workers大小 = maxiumPoolSize
 * D任务遗弃
 * Created by Administrator on 2017/2/10.
 */
public class ThreadFactoryTest {


    public static void main(String[] args) {
        ExecutorService es = new ThreadPoolExecutor(1, 2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1));
        es.submit(new RunableTest("A"));
        es.submit(new RunableTest("B"));
        es.submit(new RunableTest("C"));
        es.submit(new RunableTest("D"));
        es.shutdown();
    }


    static class RunableTest implements Runnable {
        private String name;

        RunableTest(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println(name);
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

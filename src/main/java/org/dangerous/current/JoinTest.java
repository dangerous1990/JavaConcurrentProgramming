package org.dangerous.current;

/**
 * 在主线程中控制3个线程的执行顺序
 * Created by Administrator on 2017/1/17.
 */
public class JoinTest {
    public static void main(String[] args) throws InterruptedException {
        Thread a = new Thread(new ThreadA("A"));
        Thread b = new Thread(new ThreadA("B"));
        Thread c = new Thread(new ThreadA("C"));
        a.start();
        a.join();
        b.start();
        b.join();
        c.start();
        c.join();
    }

    static class ThreadA implements Runnable {
        private String str;

        ThreadA(String str) {
            this.str = str;
        }

        public void run() {
            System.out.println(str);
        }
    }
}

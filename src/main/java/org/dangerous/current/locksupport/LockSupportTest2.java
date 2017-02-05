package org.dangerous.current.locksupport;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.LockSupport;

/**
 * 有3个线程ABC。按照ABC来运行（A线程输出A，B线程输出B，C线程输出C，以此类推，循环输出）
 * Created by Administrator on 2017/1/18.
 */
public class LockSupportTest2 {
    private static final Semaphore only = new Semaphore(1);
    private static ThreadA A = new ThreadA(only, "A");
    private static ThreadA B = new ThreadA(only, "B");
    private static ThreadA C = new ThreadA(only, "C");

    public static void main(String[] args) throws InterruptedException {
        Thread a = new Thread(A);
        Thread b = new Thread(B);
        Thread c = new Thread(C);
        A.setOtherThread(b);
        B.setOtherThread(c);
        C.setOtherThread(a);

        a.start();
        b.start();
        c.start();
        Thread.sleep(500L);
        a.interrupt();
        b.interrupt();
        c.interrupt();


    }

    static class ThreadA implements Runnable {
        private Semaphore only;
        private String str;

        public Thread getOtherThread() {
            return otherThread;
        }

        public void setOtherThread(Thread otherThread) {
            this.otherThread = otherThread;
        }

        private Thread otherThread;

        public ThreadA(Semaphore only, String str) {
            this.only = only;
            this.str = str;
        }

        public void run() {
            while (true && !Thread.interrupted()) {
                try {
                    only.acquire(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(str);
                only.release();
                LockSupport.unpark(otherThread);
                LockSupport.park();
            }
        }
    }

}

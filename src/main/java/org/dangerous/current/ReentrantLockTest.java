package org.dangerous.current;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 有3个线程ABC。按照ABC来运行（A线程输出A，B线程输出B，C线程输出C，以此类推，循环输出）
 * Created by Administrator on 2017/1/16.
 */
public class ReentrantLockTest {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        Condition conditionC = lock.newCondition();
        CyclicBarrier cb = new CyclicBarrier(3);
        Thread a = new Thread(new ThreadA(conditionA, conditionB, conditionC, lock,cb));
        Thread b = new Thread(new ThreadB(conditionA, conditionB, conditionC, lock,cb));
        Thread c = new Thread(new ThreadC(conditionA, conditionB, conditionC, lock,cb));
        a.start();
        b.start();
        c.start();
        try {
            Thread.sleep(200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        a.interrupt();
        b.interrupt();
        c.interrupt();

    }

    static class ThreadA implements Runnable {
        Condition conditionA;
        Condition conditionB;
        Condition conditionC;
        CyclicBarrier cb;
        ReentrantLock lock = new ReentrantLock();

        ThreadA(Condition conditionA, Condition conditionB, Condition conditionC, ReentrantLock lock, CyclicBarrier cb) {
            this.conditionA = conditionA;
            this.conditionB = conditionB;
            this.conditionC = conditionC;
            this.lock = lock;
            this.cb = cb;
        }


        public void run() {
            try {
                lock.lock();
                while (true) {
                    System.out.println("A");
                    conditionB.signal();
                    conditionA.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }  finally {
                lock.unlock();
            }
        }
    }

    static class ThreadB implements Runnable {
        Condition conditionA;
        Condition conditionB;
        Condition conditionC;
        CyclicBarrier cb;
        ReentrantLock lock = new ReentrantLock();

        ThreadB(Condition conditionA, Condition conditionB, Condition conditionC, ReentrantLock lock,CyclicBarrier cb) {
            this.conditionA = conditionA;
            this.conditionB = conditionB;
            this.conditionC = conditionC;
            this.lock = lock;
            this.cb = cb;
        }


        public void run() {
            try {
                lock.lock();
                while (true) {
                    System.out.println("B");
                    conditionC.signal();
                    conditionB.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }  finally {
                lock.unlock();
            }
        }
    }

    static class ThreadC implements Runnable {
        Condition conditionA;
        Condition conditionB;
        Condition conditionC;
        ReentrantLock lock = new ReentrantLock();
        CyclicBarrier cb;
        ThreadC(Condition conditionA, Condition conditionB, Condition conditionC, ReentrantLock lock,CyclicBarrier cb) {
            this.conditionA = conditionA;
            this.conditionB = conditionB;
            this.conditionC = conditionC;
            this.lock = lock;
        }


        public void run() {
            try {
                lock.lock();
                while (true) {
                    System.out.println("C");
                    System.out.println("--------------");
                    conditionA.signal();
                    conditionC.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

}

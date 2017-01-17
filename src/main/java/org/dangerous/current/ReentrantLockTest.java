package org.dangerous.current;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 有3个线程ABC。按照ABC来运行（A线程输出A，B线程输出B，C线程输出C，以此类推，循环输出）
 * 不能循环输出
 * Created by Administrator on 2017/1/16.
 */
public class ReentrantLockTest {

    public static void main(String[] args)  {
        ReentrantLock lock = new ReentrantLock();
        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        Condition conditionC = lock.newCondition();
        Thread a = new Thread(new ThreadA(conditionA,conditionB,conditionC,lock));
        Thread b = new Thread(new ThreadB(conditionA,conditionB,conditionC,lock));
        Thread c = new Thread(new ThreadC(conditionA,conditionB,conditionC,lock));
        a.start();
        b.start();
        c.start();
        try {
            Thread.sleep(500L);
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
        ReentrantLock lock = new ReentrantLock();

        ThreadA(Condition conditionA,Condition conditionB,Condition conditionC,ReentrantLock lock) {
            this.conditionA = conditionA;
            this.conditionB = conditionB;
            this.conditionC = conditionC;
            this.lock = lock;
        }


        public void run() {
            try {
                lock.lock();
                while (true){
                    conditionA.await();
                    System.out.println("A");
                    conditionB.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
    static class ThreadB implements Runnable {
        Condition conditionA;
        Condition conditionB;
        Condition conditionC;
        ReentrantLock lock = new ReentrantLock();

        ThreadB(Condition conditionA,Condition conditionB,Condition conditionC,ReentrantLock lock) {
            this.conditionA = conditionA;
            this.conditionB = conditionB;
            this.conditionC = conditionC;
            this.lock = lock;
        }


        public void run() {
            try {
                lock.lock();
                while (true){
                    conditionA.signal();
                    conditionB.await();
                    System.out.println("B");
                    conditionC.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }
    static class ThreadC implements Runnable {
        Condition conditionA;
        Condition conditionB;
        Condition conditionC;
        ReentrantLock lock = new ReentrantLock();

        ThreadC(Condition conditionA,Condition conditionB,Condition conditionC,ReentrantLock lock) {
            this.conditionA = conditionA;
            this.conditionB = conditionB;
            this.conditionC = conditionC;
            this.lock = lock;
        }


        public void run() {
            try {
                lock.lock();
                while(true) {
                    conditionB.signal();
                    conditionC.await();
                    System.out.println("C");
                    System.out.println("--------------");
                    conditionA.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }

}

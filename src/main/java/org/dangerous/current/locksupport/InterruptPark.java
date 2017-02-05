package org.dangerous.current.locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * locksupport 会响应interrupt状态，但是不会抛出InterruptedException
 * Created by Administrator on 2017/2/5.
 */
public class InterruptPark {
    public static void main(String[] args)  {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                LockSupport.park();
                System.out.println("Thread is over ");
            }
        });
        t.start();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //中断线程
        t.interrupt();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main thread is over");


    }
}

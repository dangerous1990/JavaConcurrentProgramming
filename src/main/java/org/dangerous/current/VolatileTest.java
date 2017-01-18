package org.dangerous.current;


/**
 * vloatile 操作不具有原子性，结果不等于1000，部分结果出现被覆盖情况
 * Created by Administrator on 2017/1/18.
 */
public class VolatileTest {
    public static volatile int count  = 0;

    public static void main(String[] args) {
        for (int i = 0; i <1000 ; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(5L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    VolatileTest.count++;
                }
            }).start();
        }
        try {
            Thread.sleep(1000L);
            System.out.println(count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

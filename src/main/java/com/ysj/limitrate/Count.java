package com.ysj.limitrate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Count {
    public static void main(String[] args) {
        final Semaphore semaphore = new Semaphore(3);
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //模拟每隔3秒钟，将计数器清零，在此期间过来的请求可以获取到了其中一个计数器的资源后，处理自己的请求业务
                semaphore.release(3);
                System.out.println();
            }
        }, 3000, 4000, TimeUnit.MILLISECONDS);

        //模拟源源不断的请求
        while (true) {
            try {
                semaphore.acquire();
                System.out.println("获取到计数器，开始处理自己的业务");
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("finished ~");
        }
    }
}

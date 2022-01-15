package com.ysj.limitrate;

import sun.jvm.hotspot.debugger.win32.coff.TestDebugInfo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class BaseLimiter implements Limiter{
    static Semaphore semaphore;
    int rate;
    TimeUnit timeUnit;

    BaseLimiter(int rate,TimeUnit timeUnit){
        this.rate = rate;
        this.timeUnit = timeUnit;
        semaphore = new Semaphore(this.rate);
    }

    @Override
    public boolean tryEnter() {
        return semaphore.tryAcquire();
    }

    @Override
    public void release(){
    }

    public void testLimiter(){
        this.release();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        if(tryEnter()){
                            System.out.println("enter system");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}

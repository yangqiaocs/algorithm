package com.ysj.limitrate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CountLimiter extends BaseLimiter{

    CountLimiter(int rate, TimeUnit timeUnit) {
        super(rate, timeUnit);
    }

    @Override
    public void release(){
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    semaphore.release(3);
                    System.out.println("complete release 3");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },1L,1, this.timeUnit);
    }

    public static void main(String[] args) {
        Limiter limiter = new CountLimiter(3,TimeUnit.SECONDS);
        limiter.testLimiter();
    }
}
package com.ysj.limitrate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class CountLimiter extends BaseLimiter{

    CountLimiter(int rate, TimeUnit timeUnit) {
        super(rate, timeUnit);
    }

    @Override
    public void requestGen(){
        //1s产生10个产生一个请求,直接加入队列，在consume中判断其能否被消费
        ScheduledExecutorService requestGenService = Executors.newScheduledThreadPool(1);
        requestGenService.scheduleAtFixedRate(
        new Runnable() {
            @Override
            public void run() {
                sequence++;
                try {
                    requestQueue.offer(sequence);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },100L,100L,TimeUnit.MILLISECONDS);
    }

    @Override
    public void produce(){
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    semaphore.release(rate);
                    System.out.println("complete release " + rate);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },1L,1, this.timeUnit);
    }

    @Override
    public void consume(){
        ScheduledExecutorService consumeService = Executors.newScheduledThreadPool(1);
        consumeService.scheduleAtFixedRate(new Runnable() {
            @Override
            @SuppressWarnings("InfiniteLoopStatement")
            public void run() {
                while (true){
                    try {
                        if(requestQueue.size()>0) {
                            if (semaphore.tryAcquire()) {
                                Long curRequestSequence = requestQueue.poll();
                                System.out.println("request " + curRequestSequence + " enter system," +
                                        "rest " + requestQueue.size() + " request");
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        },100L,100L, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) {
        Limiter limiter = new CountLimiter(3,TimeUnit.SECONDS);
        limiter.testLimiter();
    }
}

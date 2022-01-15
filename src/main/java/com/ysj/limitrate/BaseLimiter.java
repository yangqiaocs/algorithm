package com.ysj.limitrate;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class BaseLimiter implements Limiter{
    //资源池
    volatile Semaphore semaphore;
    //请求队列
    volatile Queue<Long> requestQueue = new LinkedList<Long>();
    //流量速率
    int rate;
    //阻塞队列上限
    Long blockQueueSize;
    //时间单位
    TimeUnit timeUnit;
    //标识请求序号
    long sequence = 0L;

    BaseLimiter(int rate,TimeUnit timeUnit){
        this.rate = rate;
        this.timeUnit = timeUnit;
        this.semaphore = new Semaphore(this.rate);
    }

    //带有阻塞队列的限流器
    BaseLimiter(int rate,TimeUnit timeUnit,long blockQueueSize){
        this.rate = rate;
        this.timeUnit = timeUnit;
        this.blockQueueSize = blockQueueSize;
        this.semaphore = new Semaphore(this.rate);
    }

    @Override
    public void produce() {
    }

    @Override
    public void consume() {
    }

    @Override
    public void requestGen(){
        //每毫秒产生一个请求
        ScheduledExecutorService requestGenService = Executors.newScheduledThreadPool(1);
        requestGenService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                sequence++;
                try {
                    if(requestQueue.size()<blockQueueSize){
                        requestQueue.offer(sequence);
                    }
                    System.out.println(sequence+"enter queue, "
                            + "rest"+ requestQueue.size()+" request");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },1L,1L, TimeUnit.MILLISECONDS);
    }

    public void testLimiter(){
        requestGen();
        produce();
        consume();
    }
}

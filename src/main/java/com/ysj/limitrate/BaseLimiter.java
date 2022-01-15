package com.ysj.limitrate;

import sun.jvm.hotspot.debugger.win32.coff.TestDebugInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class BaseLimiter implements Limiter{
    //资源池
    static Semaphore semaphore;
    //请求队列
    List<Long> requestQueue = new ArrayList<Long>();
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
        semaphore = new Semaphore(this.rate);
    }

    //带有阻塞队列的限流器
    BaseLimiter(int rate,TimeUnit timeUnit,long blockQueueSize){
        this.rate = rate;
        this.timeUnit = timeUnit;
        this.blockQueueSize = blockQueueSize;
        this.requestQueue = new ArrayList<Long>();
        semaphore = new Semaphore(this.rate);
    }

    @Override
    public void produce() {
    }

    @Override
    public void consume() {
    }

    public void testLimiter(){
        this.produce();
        this.consume();
    }
}

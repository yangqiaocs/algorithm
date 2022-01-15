package com.ysj.limitrate;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class BaseLimiter implements Limiter {
	//定时服务
	ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
	//资源池
	volatile Semaphore semaphore;
	//请求队列
	volatile Queue<Long> requestQueue = new LinkedList<>();
	int produceRate;
	//流量速率
	int consumeRate;
	//阻塞队列上限
	Long blockQueueSize;
	//标识请求序号
	long sequence = 0L;

	//带有阻塞队列的限流器
	BaseLimiter(int produceRate, int consumeRate , Long blockQueueSize) {
		this.produceRate = produceRate;
		this.consumeRate = consumeRate;
		this.blockQueueSize = blockQueueSize;
		this.semaphore = new Semaphore(this.produceRate);
	}

	@Override
	public void requestGen() {
		//1s产生100个产生一个请求,直接加入队列，在consume中判断其能否被消费
		service.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				sequence++;
				try {
					if (requestQueue.size() < blockQueueSize) {
						requestQueue.offer(sequence);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 10L, 10L, TimeUnit.MILLISECONDS);
	}

	@Override
	public void produce() {
		service.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					semaphore.release(produceRate);
					System.out.println("complete release " + produceRate);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 1L, 1L, TimeUnit.SECONDS);
	}

	@Override
	public void consume() {
		service.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					if (requestQueue.size() > 0) {
						myConsume();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 1L, 1L, TimeUnit.MICROSECONDS);
	}

	@Override
	public void myConsume() {

	}


	public void testLimiter() {
		requestGen();
		produce();
		consume();
	}

	@Override
	public void printConsumeInfo(Long requestSequence) {
		System.out.println("request " + requestSequence + " enter system," +
				"rest " + requestQueue.size() + " request");
	}
}

package com.ysj.limitrate;

import java.util.concurrent.TimeUnit;

public class LeakyLimiter extends BaseLimiter{

	LeakyLimiter(int produceRate, int consumeRate, Long blockQueueSize) {
		super(produceRate, consumeRate, blockQueueSize);
	}

	@Override
	public void myConsume(){
		//漏斗桶里一直有有令牌，直接消耗
		Long curRequestSequence = requestQueue.poll();
		printConsumeInfo(curRequestSequence);
	}

	@Override
	public void consume(){
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
		}, 100L, 100L, TimeUnit.MILLISECONDS);
	}

	public static void main(String[] args) {
		Limiter limiter = new LeakyLimiter(
				10,10, 10L);
		limiter.testLimiter();

		limiter.addRequest(10L);
	}
}

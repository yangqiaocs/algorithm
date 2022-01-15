package com.ysj.limitrate;

import java.util.concurrent.TimeUnit;

public class TokenBucket extends BaseLimiter {


	TokenBucket(int produceRate, int consumeRate, Long blockQueueSize) {
		super(produceRate, consumeRate, blockQueueSize);
	}

	@Override
	public void myConsume(){
		//有令牌，才消耗
		if (semaphore.tryAcquire()) {
			Long curRequestSequence = requestQueue.poll();
			printConsumeInfo(curRequestSequence);
		}
	}

	public static void main(String[] args) {
		Limiter limiter = new TokenBucket(
				10,7, 10L);
		limiter.testLimiter();

		limiter.addRequest(20L);

	}
}

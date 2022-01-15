package com.ysj.limitrate;

public class LeakyBucket extends BaseLimiter {


	LeakyBucket(int produceRate, int consumeRate, Long blockQueueSize) {
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
		Limiter limiter = new LeakyBucket(
				3,10, 10L);
		limiter.testLimiter();
	}
}

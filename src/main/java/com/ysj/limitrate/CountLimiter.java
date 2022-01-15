package com.ysj.limitrate;

public class CountLimiter extends BaseLimiter {

	CountLimiter(int produceRate, int consumeRate, Long blockQueueSize) {
		super(produceRate, consumeRate, blockQueueSize);
	}

	@Override
	public void myConsume() {
		//不管有没有令牌，都消耗，没有令牌就相当于不处理
		Long curRequestSequence = requestQueue.poll();
		if (semaphore.tryAcquire()) {
			printConsumeInfo(curRequestSequence);
		}
	}


	public static void main(String[] args) {
		Limiter limiter = new CountLimiter(3,10, Long.MAX_VALUE);
		limiter.testLimiter();
	}
}

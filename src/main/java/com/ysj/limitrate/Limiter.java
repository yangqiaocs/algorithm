package com.ysj.limitrate;

public interface Limiter {
     void produce();
     void consume();
     void myConsume();
     void requestGen();
     void testLimiter();
     void printConsumeInfo(Long requestSequence);

}
 
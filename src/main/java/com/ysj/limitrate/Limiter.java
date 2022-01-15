package com.ysj.limitrate;

public interface Limiter {
     void produce();
     void consume();
     void testLimiter();
}
 
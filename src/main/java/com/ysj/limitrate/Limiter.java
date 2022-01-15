package com.ysj.limitrate;

public interface Limiter {
     boolean tryEnter();
     void release();
     void testLimiter();
}

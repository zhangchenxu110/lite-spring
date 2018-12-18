package com.litespring.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * 对拦截器的抽象（持有了PointCut，切入方法）  可以被Invocation链式调用
 *
 * @author 张晨旭
 * @DATE 2018/11/29
 */
public interface Advice extends MethodInterceptor {
    Pointcut getPointcut();
}


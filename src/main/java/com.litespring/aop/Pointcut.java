package com.litespring.aop;

/**
 * 描述一个切点的接口   持有一个表达式，通过MethodMatcher判断传入的方法是否满足某表达式
 * @author 张晨旭
 * @DATE 2018/11/28
 */
public interface Pointcut {
    MethodMatcher getMethodMatcher();
    String getExpression();
}

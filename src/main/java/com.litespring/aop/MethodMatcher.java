package com.litespring.aop;

import java.lang.reflect.Method;

/**
 * 传入一个方法，校验这个方法和表达式是否匹配
 * @author 张晨旭
 * @DATE 2018/11/28
 */
public interface MethodMatcher {

    boolean matches(Method method/*, Class<?> targetClass*/);

}

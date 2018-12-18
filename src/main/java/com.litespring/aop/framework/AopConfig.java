package com.litespring.aop.framework;

import com.litespring.aop.Advice;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 对一个需要进行aop增强的目标类的抽象（目标类信息 通知类advice列表）
 *
 * @author 张晨旭
 * @DATE 2018/11/29
 */
public interface AopConfig {


    Class<?> getTargetClass();

    Object getTargetObject();

    boolean isProxyTargetClass();


    Class<?>[] getProxiedInterfaces();


    boolean isInterfaceProxied(Class<?> intf);


    List<Advice> getAdvices();


    void addAdvice(Advice advice);

    List<Advice> getAdvices(Method method/*,Class<?> targetClass*/);

    void setTargetObject(Object obj);


}


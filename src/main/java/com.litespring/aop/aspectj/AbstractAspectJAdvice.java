package com.litespring.aop.aspectj;

import com.litespring.aop.Advice;
import com.litespring.aop.Pointcut;
import com.litespring.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

/**
 * @author 张晨旭
 * @DATE 2018/11/29
 */
public abstract class AbstractAspectJAdvice implements Advice {


    protected Method adviceMethod;
    protected AspectJExpressionPointcut pointcut;
    protected AspectInstanceFactory adviceObjectFactory;


    public AbstractAspectJAdvice(Method adviceMethod,
                                 AspectJExpressionPointcut pointcut,
                                 AspectInstanceFactory adviceObject) {

        this.adviceMethod = adviceMethod;
        this.pointcut = pointcut;
        this.adviceObjectFactory = adviceObject;
    }

    //切面方法执行  通知的方法. 通知类实例
    public void invokeAdviceMethod() throws  Throwable{
        adviceMethod.invoke(adviceObjectFactory.getAspectInstance());
    }

    public Pointcut getPointcut(){
        return this.pointcut;
    }
    public Method getAdviceMethod() {
        return adviceMethod;
    }
    public Object getAdviceInstance() throws Exception {
        return adviceObjectFactory.getAspectInstance();
    }
}

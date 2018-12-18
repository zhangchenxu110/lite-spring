package com.litespring.aop.aspectj;

import com.litespring.aop.config.AspectInstanceFactory;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author 张晨旭
 * @DATE 2018/11/29
 */
public class AspectJAfterReturningAdvice extends AbstractAspectJAdvice {

    //构造方法使用父类的构造方法
    public AspectJAfterReturningAdvice(Method adviceMethod,
                                       AspectJExpressionPointcut pointcut,
                                       AspectInstanceFactory adviceObject) {
        super(adviceMethod, pointcut, adviceObject);
    }

    public Object invoke(MethodInvocation mi) throws Throwable {
        //mi：Invocation开始调用下一个advice
        Object o = mi.proceed();
        //切入方法开始执行：  例如： 调用TransactionManager的start方法
        this.invokeAdviceMethod();

        return o;
    }


}


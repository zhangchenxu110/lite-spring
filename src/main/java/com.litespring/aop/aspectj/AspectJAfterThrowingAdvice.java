package com.litespring.aop.aspectj;

import com.litespring.aop.config.AspectInstanceFactory;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author 张晨旭
 * @DATE 2018/11/29
 */
public class AspectJAfterThrowingAdvice extends AbstractAspectJAdvice  {



    public AspectJAfterThrowingAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory adviceObject) {

        super(adviceMethod, pointcut, adviceObject);
    }


    public Object invoke(MethodInvocation mi) throws Throwable {
        try {
            return mi.proceed();
        }
        catch (Throwable t) {
            //切入方法开始执行：  例如： 调用TransactionManager的start方法
            this.invokeAdviceMethod();
            throw t;
        }
    }

}


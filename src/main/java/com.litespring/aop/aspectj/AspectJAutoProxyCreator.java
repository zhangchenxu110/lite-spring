package com.litespring.aop.aspectj;

import com.litespring.aop.Advice;
import com.litespring.aop.MethodMatcher;
import com.litespring.aop.Pointcut;
import com.litespring.aop.framework.AopConfigSupport;
import com.litespring.aop.framework.AopProxyFactory;
import com.litespring.aop.framework.CglibProxyFactory;
import com.litespring.bean.BeansException;
import com.litespring.bean.factory.config.BeanPostProcessor;
import com.litespring.bean.factory.config.ConfigurableBeanFactory;
import com.litespring.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 在Bean初始化完成后，用通知类增强的代理bean去代理以前的bean
 * 产生通知类List，构造AopConfigSupport，使用AopProxyFactory生成代理bean
 *
 * @author 张晨旭
 * @DATE 2018/12/17
 */
public class AspectJAutoProxyCreator implements BeanPostProcessor {
    ConfigurableBeanFactory beanFactory;

    public Object beforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object afterInitialization(Object bean, String beanName) throws BeansException {

        //如果这个Bean本身就是Advice及其子类，那就不要再生成动态代理了。
        if (isInfrastructureClass(bean.getClass())) {
            return bean;
        }

        List<Advice> advices = getCandidateAdvices(bean);
        if (advices.isEmpty()) {
            return bean;
        }

        return createProxy(advices, bean);
    }

    /**
     * 查找可以增强bean的Advice的列表(遍历Advices   只要Advice可以增强这个bean的某个方法  就将这个Advice加到List中返回)
     *
     * @param bean
     * @return
     */
    private List<Advice> getCandidateAdvices(Object bean) {

        //通过类别在BeanDefinition中获取bean
        List<Object> advices = this.beanFactory.getBeansByType(Advice.class);

        List<Advice> result = new ArrayList<Advice>();
        for (Object o : advices) {
            Pointcut pc = ((Advice) o).getPointcut();
            if (canApply(pc, bean.getClass())) {
                result.add((Advice) o);
            }

        }
        return result;
    }

    protected Object createProxy(List<Advice> advices, Object bean) {


        AopConfigSupport config = new AopConfigSupport();
        for (Advice advice : advices) {
            config.addAdvice(advice);
        }

        Set<Class> targetInterfaces = ClassUtils.getAllInterfacesForClassAsSet(bean.getClass());
        for (Class<?> targetInterface : targetInterfaces) {
            config.addInterface(targetInterface);
        }

        config.setTargetObject(bean);

        AopProxyFactory proxyFactory = null;
        if (config.getProxiedInterfaces().length == 0) {
            proxyFactory = new CglibProxyFactory(config);
        } else {
            //TODO 需要实现JDK 代理
            //proxyFactory = new JdkAopProxyFactory(config);
        }


        return proxyFactory.getProxy();


    }

    protected boolean isInfrastructureClass(Class<?> beanClass) {
        boolean retVal = Advice.class.isAssignableFrom(beanClass);

        return retVal;
    }

    public void setBeanFactory(ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;

    }

    public static boolean canApply(Pointcut pc, Class<?> targetClass) {


        MethodMatcher methodMatcher = pc.getMethodMatcher();

        Set<Class> classes = new LinkedHashSet<Class>(ClassUtils.getAllInterfacesForClassAsSet(targetClass));
        classes.add(targetClass);
        for (Class<?> clazz : classes) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (methodMatcher.matches(method/*, targetClass*/)) {
                    return true;
                }
            }
        }

        return false;
    }

}

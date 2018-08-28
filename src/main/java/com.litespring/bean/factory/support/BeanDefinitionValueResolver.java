package com.litespring.bean.factory.support;

import com.litespring.bean.factory.config.RuntimeBeanReference;
import com.litespring.bean.factory.config.TypedStringValue;

/**
 * @author 张晨旭
 * @DATE 2018/8/27
 */
public class BeanDefinitionValueResolver {
    //依赖BeanFactory
    private final DefaultBeanFactory beanFactory;

    public BeanDefinitionValueResolver(DefaultBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 当传入的对象是RuntimeBeanReference  就要通过BeanID转换成实例。
     *
     * @param value
     * @return
     */
    public Object resolveValueIfNecessary(Object value) {
        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference ref = (RuntimeBeanReference) value;
            String beanID = ref.getBeanId();
            Object bean = this.beanFactory.getBean(beanID);
            return bean;
        } else if (value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        } else {
            throw new RuntimeException("the value " + value + " has not implemented");
        }
    }
}

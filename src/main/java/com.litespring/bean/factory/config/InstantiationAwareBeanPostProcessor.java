package com.litespring.bean.factory.config;

import com.litespring.bean.BeansException;

/**
 * 实例生命周期钩子函数
 *
 * @author 张晨旭
 * @DATE 2018/10/8
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    boolean afterInstantiation(Object bean, String beanName) throws BeansException;

    //处理属性的钩子函数
    void postProcessPropertyValues(Object bean, String beanName) throws BeansException;

}

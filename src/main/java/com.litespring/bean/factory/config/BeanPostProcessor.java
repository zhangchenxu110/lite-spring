package com.litespring.bean.factory.config;

import com.litespring.bean.BeansException;

/**
 * bean生命周期 钩子函数
 *
 * @author 张晨旭
 * @DATE 2018/10/8
 */
public interface BeanPostProcessor {
    Object beforeInitialization(Object bean, String beanName) throws BeansException;

    Object afterInitialization(Object bean, String beanName) throws BeansException;
}

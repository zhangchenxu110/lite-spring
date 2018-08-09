package com.litespring.bean.factory;

import com.litespring.bean.BeanDefinition;

/**
 * 有向map中注册Bean的职责
 *
 * @author 张晨旭
 * @DATE 2018/8/8
 */
public interface BeanDefinitionRegistry {
    BeanDefinition getBeanDefinition(String beanID);
    void registerBeanDefinition(String beanID, BeanDefinition bd);
}

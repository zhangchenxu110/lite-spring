package com.litespring.bean.factory.support;

import com.litespring.bean.BeanDefinition;
import com.litespring.bean.factory.BeanCreationException;
import com.litespring.bean.factory.config.ConfigurableBeanFactory;

/**
 * 在DefaultBeanFactory和上层中再抽出一层  提供通过BeanDefinition获取类
 * @author 张晨旭
 * @DATE 2018/12/17
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {
    protected abstract Object createBean(BeanDefinition bd) throws BeanCreationException;
}


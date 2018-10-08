package com.litespring.bean.factory.config;

import com.litespring.bean.factory.BeanFactory;

import java.util.List;

/**
 * 拓展BeanFactory 增加set get classLoader的功能
 *
 * @author 张晨旭
 * @DATE 2018/8/8
 */
public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {
    void setBeanClassLoader(ClassLoader beanClassLoader);

    ClassLoader getBeanClassLoader();

    //添加bean生命周期中处理的钩子函数
    void addBeanPostProcessor(BeanPostProcessor postProcessor);

    //钩子函数列表
    List<BeanPostProcessor> getBeanPostProcessors();
}


package com.litespring.bean.factory.config;

import com.litespring.bean.BeanFactory;

/**
 * 拓展BeanFactory 增加set get classLoader的功能
 *
 * @author 张晨旭
 * @DATE 2018/8/8
 */
public interface ConfigurableBeanFactory extends BeanFactory {
    void setBeanClassLoader(ClassLoader beanClassLoader);

    ClassLoader getBeanClassLoader();
}


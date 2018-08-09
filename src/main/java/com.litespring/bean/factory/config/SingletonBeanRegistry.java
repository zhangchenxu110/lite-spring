package com.litespring.bean.factory.config;

/**
 * 单例接口 实现类中注册单例
 *
 * @author 张晨旭
 * @DATE 2018/8/9
 */
public interface SingletonBeanRegistry {
    void registerSingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);
}

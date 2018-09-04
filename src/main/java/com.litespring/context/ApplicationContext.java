package com.litespring.context;

import com.litespring.bean.factory.config.ConfigurableBeanFactory;

/**
 * applicationContext继承BeanFactory
 * 1、持有DefaultBeanFactory
 * 2、持有Resource实例 从不同的地方加载Resource
 * 只对外提供getBean方法
 *
 * @author 张晨旭
 * @DATE 2018/8/8
 */
public interface ApplicationContext extends ConfigurableBeanFactory {

}

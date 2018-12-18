package com.litespring.bean.factory;

import java.util.List;

/**
 * BeanFactory只拥有getBean的功能
 *
 * @author 张晨旭
 * @DATE 2018/7/9
 */
public interface BeanFactory {
    Object getBean(String beanId);

    Class<?> getType(String name) throws NoSuchBeanDefinitionException;

    //通过类型获取bean的所有实例
    List<Object> getBeansByType(Class<?> type);
}

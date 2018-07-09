package com.litespring.bean;

/**
 * @author 张晨旭
 * @DATE 2018/7/9
 */
public interface BeanFactory {
    BeanDefinition getBeanDefinition(String path);

    Object getBean(String beanId);
}

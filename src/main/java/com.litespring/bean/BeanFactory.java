package com.litespring.bean;

/**
 * BeanFactory只拥有getBean的功能
 *
 * @author 张晨旭
 * @DATE 2018/7/9
 */
public interface BeanFactory {
    Object getBean(String beanId);
}

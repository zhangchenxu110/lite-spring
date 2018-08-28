package com.litespring.bean.factory.config;

/**
 * ref型参数
 *
 * @author 张晨旭
 * @DATE 2018/8/27
 */
public class RuntimeBeanReference {
    private final String beanId;

    public RuntimeBeanReference(String beanId) {
        this.beanId = beanId;
    }

    public String getBeanId() {
        return this.beanId;
    }
}

package com.litespring.exception;

/**
 * 生成BeanDefinition时的错误
 * @author 张晨旭
 * @DATE 2018/7/9
 */
public class BeanDefinitionStoreException extends BeansException {

    public BeanDefinitionStoreException(String msg, Throwable cause) {
        super(msg, cause);

    }

}

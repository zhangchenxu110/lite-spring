package com.litespring.bean.factory;

import com.litespring.bean.BeansException;

/**
 * Bean create 时的异常
 * @author 张晨旭
 * @DATE 2018/7/9
 */
public class BeanCreationException extends BeansException {
    private String beanName;
    public BeanCreationException(String msg) {
        super(msg);

    }
    public BeanCreationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BeanCreationException(String beanName, String msg) {
        super("Error creating bean with name '" + beanName + "': " + msg);
        this.beanName = beanName;
    }

    public BeanCreationException(String beanName, String msg, Throwable cause) {
        this(beanName, msg);
        initCause(cause);
    }
    public String getBeanName(){
        return this.beanName;
    }



}

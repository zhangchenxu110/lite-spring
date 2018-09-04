package com.litespring.bean;

/**
 * 运行时异常  不用抛出
 * @author 张晨旭
 * @DATE 2018/7/9
 */
public class BeansException extends RuntimeException {
    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

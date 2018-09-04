package com.litespring.bean;

/**
 * @author 张晨旭
 * @DATE 2018/8/28
 */
public interface TypeConverter {


    <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;


}

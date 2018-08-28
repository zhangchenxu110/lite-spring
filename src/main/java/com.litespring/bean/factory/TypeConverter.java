package com.litespring.bean.factory;

import com.litespring.exception.TypeMismatchException;

/**
 * @author 张晨旭
 * @DATE 2018/8/28
 */
public interface TypeConverter {


    <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;


}

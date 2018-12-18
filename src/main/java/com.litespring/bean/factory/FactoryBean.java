package com.litespring.bean.factory;

/**
 *  接口  getObject()   获取工厂实例(获取Method)
 * @author 张晨旭
 * @DATE 2018/12/17
 */
public interface FactoryBean<T> {


    T getObject() throws Exception;

    Class<?> getObjectType();

}


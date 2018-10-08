package com.litespring.bean.factory.config;

import com.litespring.bean.factory.BeanFactory;

/**
 * 提供解析DependencyDescriptor的功能
 *
 * @author 张晨旭
 * @DATE 2018/10/8
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
    Object resolveDependency(DependencyDescriptor dependencyDescriptor);
}

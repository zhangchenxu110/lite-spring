package com.litespring.bean.factory.annotation;

import com.litespring.bean.core.type.AnnotationMetadata;

/**
 * 拓展了metadata的BeanDefinition
 *
 * @author 张晨旭
 * @DATE 2018/9/4
 */
public interface AnnotatedBeanDefinition {
    AnnotationMetadata getMetadata();
}

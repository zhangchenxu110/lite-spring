package com.litespring.bean.core.type;

import com.litespring.bean.core.annotation.AnnotationAttributes;

import java.util.Set;

/**
 * 注解metadata  继承持有了类的元数据信息
 *
 * @author 张晨旭
 * @DATE 2018/9/4
 */
public interface AnnotationMetadata extends ClassMetadata {

    Set<String> getAnnotationTypes();

    boolean hasAnnotation(String annotationType);

    //注解属性记录 AnnotationAttributes是一个包装了的HashMap   k：
    public AnnotationAttributes getAnnotationAttributes(String annotationType);
}
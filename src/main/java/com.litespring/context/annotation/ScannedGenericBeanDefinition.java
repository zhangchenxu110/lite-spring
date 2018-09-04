package com.litespring.context.annotation;

import com.litespring.core.type.AnnotationMetadata;
import com.litespring.bean.factory.annotation.AnnotatedBeanDefinition;
import com.litespring.bean.factory.support.GenericBeanDefinition;

/**
 * @author 张晨旭
 * @DATE 2018/9/4
 */
public class ScannedGenericBeanDefinition extends GenericBeanDefinition implements AnnotatedBeanDefinition {

    private final AnnotationMetadata metadata;


    public ScannedGenericBeanDefinition(AnnotationMetadata metadata) {
        super();

        this.metadata = metadata;

        setBeanClassName(this.metadata.getClassName());
    }


    public final AnnotationMetadata getMetadata() {
        return this.metadata;
    }


}

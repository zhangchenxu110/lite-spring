package com.litespring.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * @author 张晨旭
 * @DATE 2018/10/8
 */
public abstract class AnnotationUtils {

    public static <T extends Annotation> T getAnnotation(AnnotatedElement ae, Class<T> annotationType) {
        T ann = ae.getAnnotation(annotationType);
        if (ann == null) {
            for (Annotation metaAnn : ae.getAnnotations()) {
                ann = metaAnn.annotationType().getAnnotation(annotationType);
                if (ann != null) {
                    break;
                }
            }
        }
        return ann;
    }


}

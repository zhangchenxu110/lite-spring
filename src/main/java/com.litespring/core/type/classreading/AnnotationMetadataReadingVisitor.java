package com.litespring.core.type.classreading;

import com.litespring.core.annotation.AnnotationAttributes;
import com.litespring.core.type.AnnotationMetadata;
import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.Type;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * 也是一个Visitor  被classReader持有 当读取完信息后回调这个visitor，解析类的metadata以及注解的metadata
 * 继承ClassMetadataReadingVisitor
 *
 * @author 张晨旭
 * @DATE 2018/9/4
 */
public class AnnotationMetadataReadingVisitor extends ClassMetadataReadingVisitor implements AnnotationMetadata {

    private final Set<String> annotationSet = new LinkedHashSet<String>(4);
    private final Map<String, AnnotationAttributes> attributeMap = new LinkedHashMap<String, AnnotationAttributes>(4);

    public AnnotationMetadataReadingVisitor() {

    }

    /**
     * 在类级别的metadata做回调时，每个注解会回调这个方法。
     * 这个方法返回一个AnnotationVisitor，这个visitor会对当前这个注解的每个属性进行回调。
     *
     * @param desc    获取当前注解的类名
     * @param visible
     * @return
     */
    @Override
    public AnnotationVisitor visitAnnotation(final String desc, boolean visible) {
        String className = Type.getType(desc).getClassName();
        this.annotationSet.add(className);
        return new AnnotationAttributesReadingVisitor(className, this.attributeMap);
    }

    public Set<String> getAnnotationTypes() {
        return this.annotationSet;
    }

    public boolean hasAnnotation(String annotationType) {
        return this.annotationSet.contains(annotationType);
    }

    public AnnotationAttributes getAnnotationAttributes(String annotationType) {
        return this.attributeMap.get(annotationType);
    }


}

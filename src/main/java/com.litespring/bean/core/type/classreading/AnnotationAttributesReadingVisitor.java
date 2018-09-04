package com.litespring.bean.core.type.classreading;

import com.litespring.bean.core.annotation.AnnotationAttributes;
import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

import java.util.Map;

/**
 * 在ClassReader回调类级别的metadata时，当回调到每一个visitAnnotation时。
 * 如果返回一个AnnotationVisitor，那么就会访问这个注解的每个属性 回调属性Visitor方法。
 *
 * @author 张晨旭
 * @DATE 2018/9/4
 */
public class AnnotationAttributesReadingVisitor extends AnnotationVisitor {
    private final String annotationType;

    //当一个注解的所有属性都回调完visitor后，最后会回调visitEnd方法，将这个注解的属性Map存到attributesMap中  K是注解类V是属性Map AnnotationAttributes。
    private final Map<String, AnnotationAttributes> attributesMap;

    //保存一个注解的所有属性  是一个Map结构  k是属性名，v是属性值。
    private AnnotationAttributes attributes = new AnnotationAttributes();


    public AnnotationAttributesReadingVisitor(String annotationType, Map<String, AnnotationAttributes> attributesMap) {
        super(Opcodes.ASM5);
        this.annotationType = annotationType;
        this.attributesMap = attributesMap;
    }

    @Override
    public final void visitEnd() {
        this.attributesMap.put(this.annotationType, this.attributes);
    }

    public void visit(String attributeName, Object attributeValue) {
        this.attributes.put(attributeName, attributeValue);
    }
}

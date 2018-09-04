package com.litespring.context.annotation;

import com.litespring.core.annotation.AnnotationAttributes;
import com.litespring.core.type.AnnotationMetadata;
import com.litespring.bean.BeanDefinition;
import com.litespring.bean.BeanDefinitionRegistry;
import com.litespring.bean.factory.annotation.AnnotatedBeanDefinition;
import com.litespring.bean.factory.support.BeanNameGenerator;
import com.litespring.util.ClassUtils;
import com.litespring.util.StringUtils;

import java.beans.Introspector;
import java.util.Set;

/**
 * @author 张晨旭
 * @DATE 2018/9/4
 */
public class AnnotationBeanNameGenerator implements BeanNameGenerator {


    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        if (definition instanceof AnnotatedBeanDefinition) {
            String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition) definition);
            if (StringUtils.hasText(beanName)) {
                // Explicit bean name found.
                return beanName;
            }
        }
        //当没有在注解属性中指定value  就通过类名生成一个默认的beanId
        return buildDefaultBeanName(definition, registry);
    }

    /**
     * 通过类注解上的value来获取这个bean的ID
     * Derive a bean name from one of the annotations on the class.
     *
     * @param annotatedDef the annotation-aware bean definition
     * @return the bean name, or {@code null} if none is found
     */
    protected String determineBeanNameFromAnnotation(AnnotatedBeanDefinition annotatedDef) {
        AnnotationMetadata amd = annotatedDef.getMetadata();
        Set<String> types = amd.getAnnotationTypes();
        String beanName = null;
        for (String type : types) {
            AnnotationAttributes attributes = amd.getAnnotationAttributes(type);
            if (attributes.get("value") != null) {
                Object value = attributes.get("value");
                if (value instanceof String) {
                    String strVal = (String) value;
                    if (StringUtils.hasLength(strVal)) {
                        beanName = strVal;
                    }
                }
            }
        }
        return beanName;
    }


    /**
     * Derive a default bean name from the given bean definition.
     * <p>The default implementation delegates to {@link #buildDefaultBeanName(BeanDefinition)}.
     *
     * @param definition the bean definition to build a bean name for
     * @param registry   the registry that the given bean definition is being registered with
     * @return the default bean name (never {@code null})
     */
    protected String buildDefaultBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        return buildDefaultBeanName(definition);
    }

    /**
     * Derive a default bean name from the given bean definition.
     * <p>The default implementation simply builds a decapitalized version
     * of the short class name: e.g. "mypackage.MyJdbcDao" -> "myJdbcDao".
     * <p>Note that inner classes will thus have names of the form
     * "outerClassName.InnerClassName", which because of the period in the
     * name may be an issue if you are autowiring by name.
     *
     * @param definition the bean definition to build a bean name for
     * @return the default bean name (never {@code null})
     */
    protected String buildDefaultBeanName(BeanDefinition definition) {
        String shortClassName = ClassUtils.getShortName(definition.getBeanClassName());
        return Introspector.decapitalize(shortClassName);
    }

}


package com.litespring.bean.factory.annotation;

import com.litespring.bean.factory.BeanCreationException;
import com.litespring.bean.factory.config.AutowireCapableBeanFactory;
import com.litespring.bean.factory.config.DependencyDescriptor;
import com.litespring.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * 字段依赖注入处理
 *
 * @author 张晨旭
 * @DATE 2018/10/8
 */
public class AutowiredFieldElement extends InjectionElement {
    boolean required;

    public AutowiredFieldElement(Field f, boolean required, AutowireCapableBeanFactory factory) {
        super(f, factory);
        this.required = required;
    }

    public Field getField() {
        return (Field) this.member;
    }

    @Override
    public void inject(Object target) {

        Field field = this.getField();
        try {

            DependencyDescriptor desc = new DependencyDescriptor(field, this.required);

            Object value = factory.resolveDependency(desc);

            if (value != null) {

                ReflectionUtils.makeAccessible(field);
                field.set(target, value);
            }
        } catch (Throwable ex) {
            throw new BeanCreationException("Could not autowire field: " + field, ex);
        }
    }

}


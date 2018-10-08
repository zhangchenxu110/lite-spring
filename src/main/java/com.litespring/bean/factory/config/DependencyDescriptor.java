package com.litespring.bean.factory.config;

import com.litespring.util.Assert;

import java.lang.reflect.Field;

/**
 * 依赖描述符
 * lite-spring中仅实现依赖符为字段的，依赖符为方法时暂忽略。
 *
 * @author 张晨旭
 * @DATE 2018/10/8
 */
public class DependencyDescriptor {
    private Field field;
    private boolean required;

    public DependencyDescriptor(Field field, boolean required) {
        Assert.notNull(field, "Field must not be null");
        this.field = field;
        this.required = required;

    }

    public Class<?> getDependencyType() {
        if (this.field != null) {
            return field.getType();
        }
        throw new RuntimeException("only support field dependency");
    }

    public boolean isRequired() {
        return this.required;
    }
}

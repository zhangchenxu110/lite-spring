package com.litespring.bean.factory.support;

import com.litespring.bean.BeanDefinition;
import com.litespring.bean.ConstructorArgument;
import com.litespring.bean.PropertyValue;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义BeanDefinition类  每个类的定义中属性有他的id 名字 范围 还有各个构造方法。
 *
 * @author 张晨旭
 * @DATE 2018/7/9
 */
public class GenericBeanDefinition implements BeanDefinition {
    private String id;
    private String beanClassName;
    private Class<?> beanClass;
    private boolean singleton = true;
    private boolean prototype = false;
    private String scope = SCOPE_DEFAULT;

    private List<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
    private ConstructorArgument constructorArgument = new ConstructorArgument();

    //表明这个Bean定义是不是我们litespring自己合成的。
    private boolean isSynthetic = false;

    public GenericBeanDefinition() {
    }

    public GenericBeanDefinition(Class<?> clz) {
        this.beanClass = clz;
        this.beanClassName = clz.getName();
    }

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    public String getBeanClassName() {

        return this.beanClassName;
    }

    public List<PropertyValue> getPropertyValues() {
        return this.propertyValues;
    }

    public boolean isSingleton() {
        return this.singleton;
    }

    public boolean isPrototype() {
        return this.prototype;
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);

    }

    protected void setBeanClassName(String className) {
        this.beanClassName = className;
    }

    public ConstructorArgument getConstructorArgument() {
        return this.constructorArgument;
    }

    public String getID() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean hasConstructorArgumentValues() {
        return !this.constructorArgument.isEmpty();
    }

    @Override
    public Class<?> getBeanClass() {
        return beanClass;
    }

    @Override
    public boolean hasBeanClass() {
        return this.beanClass != null;
    }

    @Override
    public Class<?> resolveBeanClass(ClassLoader beanClassLoader) throws ClassNotFoundException {
        String className = getBeanClassName();
        if (className == null) {
            return null;
        }
        Class<?> resolvedClass = beanClassLoader.loadClass(className);
        this.beanClass = resolvedClass;
        return resolvedClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public boolean isSynthetic() {
        return isSynthetic;
    }
    public void setSynthetic(boolean isSynthetic) {
        this.isSynthetic = isSynthetic;
    }
}

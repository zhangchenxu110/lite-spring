package com.litespring.bean;

import java.util.List;

/**
 * @author 张晨旭
 * @DATE 2018/7/9
 */
public interface BeanDefinition {
    public static final String SCOPE_SINGLETON = "singleton";
    public static final String SCOPE_PROTOTYPE = "prototype";
    public static final String SCOPE_DEFAULT = "";

    public boolean isSingleton();

    public boolean isPrototype();

    String getScope();

    void setScope(String scope);

    public String getBeanClassName();

    List<PropertyValue> getPropertyValues(); //属性列表

}

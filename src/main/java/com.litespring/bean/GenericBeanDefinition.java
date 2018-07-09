package com.litespring.bean;

/**
 * 定义BeanDefinition类  每个类的定义中属性有他的id 名字 范围 还有各个构造方法。
 * @author 张晨旭
 * @DATE 2018/7/9
 */
public class GenericBeanDefinition implements BeanDefinition {
    private String id;
    private String beanClassName;
    private boolean singleton = true;
    private boolean prototype = false;
    private String scope = SCOPE_DEFAULT;
    public GenericBeanDefinition(String id, String beanClassName) {

        this.id = id;
        this.beanClassName = beanClassName;
    }
    public String getBeanClassName() {

        return this.beanClassName;
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

}

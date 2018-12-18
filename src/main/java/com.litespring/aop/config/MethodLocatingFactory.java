package com.litespring.aop.config;

import com.litespring.bean.BeanUtils;
import com.litespring.bean.factory.BeanFactory;
import com.litespring.bean.factory.BeanFactoryAware;
import com.litespring.bean.factory.FactoryBean;
import com.litespring.util.StringUtils;

import java.lang.reflect.Method;

/**
 * 将需要织入的方法拿出来(这个类也就是通知的方法的BeanDefinition的第一个参数：通知方法  targetBeanName和methodName都是读取xml时)
 *
 * @author 张晨旭
 * @DATE 2018/11/28
 */
public class MethodLocatingFactory implements FactoryBean<Method>, BeanFactoryAware {

    //第1 2个参数是在构造BeanDefinition时，放在GenericBeanDefinition中的，实例化Bean时按照字符串属性注入的
    private String targetBeanName;

    private String methodName;

    //这个属性是在初始化bean时，设置BeanFactory是，生成的
    private Method method;

    public void setTargetBeanName(String targetBeanName) {
        this.targetBeanName = targetBeanName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        if (!StringUtils.hasText(this.targetBeanName)) {
            throw new IllegalArgumentException("Property 'targetBeanName' is required");
        }
        if (!StringUtils.hasText(this.methodName)) {
            throw new IllegalArgumentException("Property 'methodName' is required");
        }

        Class<?> beanClass = beanFactory.getType(this.targetBeanName);
        if (beanClass == null) {
            throw new IllegalArgumentException("Can't determine type of bean with name '" + this.targetBeanName + "'");
        }


        this.method = BeanUtils.resolveSignature(this.methodName, beanClass);

        if (this.method == null) {
            throw new IllegalArgumentException("Unable to locate method [" + this.methodName +
                    "] on bean [" + this.targetBeanName + "]");
        }
    }


    public Method getObject() throws Exception {
        return this.method;
    }

    public Class<?> getObjectType() {
        return Method.class;
    }
}

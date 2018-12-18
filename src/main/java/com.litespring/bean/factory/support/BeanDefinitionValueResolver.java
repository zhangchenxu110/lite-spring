package com.litespring.bean.factory.support;

import com.litespring.bean.BeanDefinition;
import com.litespring.bean.BeansException;
import com.litespring.bean.factory.BeanCreationException;
import com.litespring.bean.factory.FactoryBean;
import com.litespring.bean.factory.config.RuntimeBeanReference;
import com.litespring.bean.factory.config.TypedStringValue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 张晨旭
 * @DATE 2018/8/27
 */
public class BeanDefinitionValueResolver {
    protected final Log logger = LogFactory.getLog(getClass());

    //依赖BeanFactory
    private final AbstractBeanFactory beanFactory;

    public BeanDefinitionValueResolver(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 当传入的对象是RuntimeBeanReference  就要通过BeanID转换成实例。
     *
     * @param value
     * @return
     */
    public Object resolveValueIfNecessary(Object value) {
        //第2个参数
        // <aop:pointcut id="placeOrder" expression="execution(* org.litespring.service.v5.*.placeOrder(..))" />
        // placeOrder是由上面注入到BeanDefinition中   这里通过beanID获取AspectJExpressionPoint实例的。
        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference ref = (RuntimeBeanReference) value;
            String beanID = ref.getBeanId();
            Object bean = this.beanFactory.getBean(beanID);
            return bean;
        } else if (value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        } else if (value instanceof BeanDefinition) {
            // 如果这个要注入的属性是BeanDefinition类型
            // (也就是AOP时 将通知类的方法(MethodLocatingFactory --getObject-- Advice)  切面(AspectJExpressionPoint)  通知类(通知类 普通BD)
            // 这三个抽象成了BeanDefinition(准确说是1 3)，需要获取这些BeanDefinition这种属性实例
            BeanDefinition bd = (BeanDefinition) value;

            String innerBeanName = "(inner bean)" + bd.getBeanClassName() + "#" +
                    Integer.toHexString(System.identityHashCode(bd));

            return resolveInnerBean(innerBeanName, bd);

        } else {
            throw new RuntimeException("the value " + value + " has not implemented");
        }
    }


    private Object resolveInnerBean(String innerBeanName, BeanDefinition innerBd) {
        try {
            //获取通知类方法和通知类  这两种BeanDefinition的实例(获取一个bean的实例 会先调用构造方法设置属性 再populateBean设置属性)
            Object innerBean = this.beanFactory.createBean(innerBd);

            //对于通知方法(第一个参数)  也就是MethodLocatingFactory(实现了BeanFactoryAware和FactoryBean(获取方法))
            // 可以Method
            if (innerBean instanceof FactoryBean) {
                try {
                    return ((FactoryBean<?>) innerBean).getObject();
                } catch (Exception e) {
                    throw new BeanCreationException(innerBeanName, "FactoryBean threw exception on object creation", e);
                }
            } else {//对于通知类  也就是AspectInstanceFactory (实现了BeanFactoryAware（设置BeanFactory）)
                return innerBean;
            }
        } catch (BeansException ex) {
            throw new BeanCreationException(
                    innerBeanName,
                    "Cannot create inner bean '" + innerBeanName + "' " +
                            (innerBd != null && innerBd.getBeanClassName() != null ? "of type [" + innerBd.getBeanClassName() + "] " : "")
                    , ex);
        }
    }
}

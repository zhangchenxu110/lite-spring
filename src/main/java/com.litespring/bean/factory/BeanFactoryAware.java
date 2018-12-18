package com.litespring.bean.factory;

import com.litespring.bean.BeansException;

/**
 * @author 张晨旭
 * @DATE 2018/12/17
 */
public interface BeanFactoryAware {

    /**
     * Callback that supplies the owning factory to a bean instance.
     * <p>Invoked after the population of normal bean properties
     * but before an initialization callback such as
     * {@link InitializingBean#afterPropertiesSet()} or a custom init-method.
     * @param beanFactory owning BeanFactory (never {@code null}).
     * The bean can immediately call methods on the factory.
     * @throws BeansException in case of initialization errors
     * @see BeanInitializationException
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
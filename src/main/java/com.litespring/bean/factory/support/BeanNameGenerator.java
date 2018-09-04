package com.litespring.bean.factory.support;

import com.litespring.bean.BeanDefinition;
import com.litespring.bean.BeanDefinitionRegistry;

/**
 * @author 张晨旭
 * @DATE 2018/9/4
 */
public interface BeanNameGenerator {

    /**
     * Generate a bean name for the given bean definition.
     * @param definition the bean definition to generate a name for
     * @param registry the bean definition registry that the given definition
     * is supposed to be registered with
     * @return the generated bean name
     */
    String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry);

}
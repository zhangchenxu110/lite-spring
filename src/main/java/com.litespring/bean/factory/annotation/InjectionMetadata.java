package com.litespring.bean.factory.annotation;

import java.util.List;

/**
 * bean的注入元数据 持有bean的targetClass，以及所有injectElement列表
 *
 * @author 张晨旭
 * @DATE 2018/10/8
 */
public class InjectionMetadata {
    private final Class<?> targetClass;
    private List<InjectionElement> injectionElements;

    public InjectionMetadata(Class<?> targetClass, List<InjectionElement> injectionElements) {
        this.targetClass = targetClass;
        this.injectionElements = injectionElements;
    }
    public List<InjectionElement> getInjectionElements() {
        return injectionElements;
    }

    public void inject(Object target) {
        if (injectionElements == null || injectionElements.isEmpty()) {
            return;
        }
        for (InjectionElement ele : injectionElements) {

            ele.inject(target);
        }
    }

}

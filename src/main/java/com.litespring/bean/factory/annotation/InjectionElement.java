package com.litespring.bean.factory.annotation;

import com.litespring.bean.factory.config.AutowireCapableBeanFactory;

import java.lang.reflect.Member;

/**
 * @author 张晨旭
 * @DATE 2018/10/8
 */
public abstract class InjectionElement {
    protected Member member;
    protected AutowireCapableBeanFactory factory;

    InjectionElement(Member member, AutowireCapableBeanFactory factory) {
        this.member = member;
        this.factory = factory;
    }

    public abstract void inject(Object target);
}

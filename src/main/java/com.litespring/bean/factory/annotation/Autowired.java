package com.litespring.bean.factory.annotation;

import java.lang.annotation.*;

/**
 *
 * @author 张晨旭
 * @DATE 2018/9/3
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {

    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     *
     * @return the suggested component name, if any
     */
    String value() default "";

}
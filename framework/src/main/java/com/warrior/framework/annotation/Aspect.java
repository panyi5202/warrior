package com.warrior.framework.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 *
 * @author panyi on 18-7-20.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     * 注解
     */
    Class<? extends Annotation> value();
}

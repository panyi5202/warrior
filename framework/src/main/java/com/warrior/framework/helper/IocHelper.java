package com.warrior.framework.helper;

import com.warrior.framework.annotation.Inject;
import com.warrior.framework.util.ArrayUtil;
import com.warrior.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入帮助类
 *
 * @author panyi on 18-7-18.
 */
public final class IocHelper {
    static {
        // 获取所有Bean类和Bean实例的映射
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (beanMap != null) {
            beanMap.forEach((beanClass, beanInstance) -> {
                // 获取bean类定义的所有成员变量
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(beanFields)) {
                    for (Field beanField : beanFields) {
                        // 判断当前field是否带有Inject标注
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            // 在Bean容器中获取对应的实例
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                // 通过反射初始化BeanField的值
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            });
        }
    }
}


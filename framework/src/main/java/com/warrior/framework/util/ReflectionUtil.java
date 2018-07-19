package com.warrior.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 *
 * @author panyi on 18-7-18.
 */
public final class ReflectionUtil {
    private static final Logger log = LoggerFactory.getLogger(ReflectionUtil.class);

    public static Object newInstance(Class<?> cls) {
        Object object = null;
        try {
            object = cls.newInstance();
        } catch (Exception e) {
            log.error("实例化{}失败,原因：{}", cls, e.getMessage());
        }
        return object;
    }

    public static Object invokeMethod(Object obj, Method method, Object... args) {
        Object result = null;
        try {
            method.setAccessible(true);
            result = method.invoke(obj, args);
        } catch (Exception e) {
            log.error("调用方法{}失败，原因：{}", method, e.getMessage());
        }
        return result;
    }

    public static void setField(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            log.error("设置属性{}的值出错，原因：{}", field, e.getMessage());
        }
    }
}

package com.warrior.framework.helper;

import com.warrior.framework.util.ReflectionUtil;

import javax.imageio.plugins.bmp.BMPImageWriteParam;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean帮助类
 *
 * @author panyi on 18-7-18.
 */
public class BeanHelper {
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> cls : beanClassSet) {
            // 实例化Bean
            Object obj = ReflectionUtil.newInstance(cls);
            BEAN_MAP.put(cls, obj);
            System.out.println("类：" + cls.getName() + "=" + obj);
        }
    }

    /**
     * 获取Bean映射
     *
     * @return Map
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("找不到类的实例bean->" + cls);
        }
        return (T) BEAN_MAP.get(cls);
    }

}

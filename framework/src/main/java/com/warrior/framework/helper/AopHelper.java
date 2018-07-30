package com.warrior.framework.helper;

import com.warrior.framework.annotation.Aspect;
import com.warrior.framework.proxy.AspectProxy;
import com.warrior.framework.proxy.Proxy;
import com.warrior.framework.proxy.ProxyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * AOP帮助类
 *
 * @author panyi on 18-7-20.
 */
public final class AopHelper {
    private static final Logger log = LoggerFactory.getLogger(AopHelper.class);

    static {
        try {
            // 创建 Map<切面类、Set<目标类>>
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            // 建 Map<目标类，List<切面代理对象>>
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            targetMap.forEach((targetClass, proxyList) -> {
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);
            });
        } catch (Exception e) {
            log.error("初始化AOP错误：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 获取切面类Aspect标注中配置的annotation对应的所有类
     * 比如：@Aspect(Controller)，获取所有被@Controller标注的类
     *
     * @param aspect 切面注解
     * @return Set
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) {
        Set<Class<?>> targetClassSet = new HashSet<>();
        Class<? extends Annotation> annotation = aspect.value();
        if (!Aspect.class.equals(annotation)) {
            // 获取所有被Aspect标注的类
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    /**
     * 创建 Map<切面类、Set<目标类>>
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        // 获取 AspectProxy 的所有子类
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        proxyClassSet.forEach(proxyClass -> {
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        });
        return proxyMap;
    }

    /**
     * 创建 Map<目标类，List<切面代理对象>>
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        proxyMap.forEach((proxyClass, targetClassSet) -> {
            targetClassSet.forEach(targetClass -> {
                try {
                    Proxy proxy = (Proxy) proxyClass.newInstance();
                    if (targetMap.containsKey(targetClass)) {
                        targetMap.get(targetClass).add(proxy);
                    } else {
                        List<Proxy> proxyList = new ArrayList<>();
                        proxyList.add(proxy);
                        targetMap.put(targetClass, proxyList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
        return targetMap;
    }

    //编写一个泛型方法对异常进行包装
    static <E extends Exception> void doThrow(Exception e) throws E {
        throw (E) e;
    }
}

package com.warrior.framework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.util.List;

/**
 * 代理管理器
 *
 * @author panyi on 18-7-20.
 */
public class ProxyManager {
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {
        return (T) Enhancer.create(targetClass, (MethodInterceptor) (targetObject, targetMethod, methodParams, methodProxy) ->
                new ProxyChain(targetClass, targetObject, targetMethod, methodProxy, methodParams, proxyList)
                .doProxyChain());
    }
}

package com.warrior.framework.proxy;

/**
 * 代理接口
 *
 * @author panyi on 18-7-20.
 */
public interface Proxy {
    /**
     * 执行链式代理
     *
     * @param proxyChain 代理链
     * @return Object
     * @throws Throwable
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}

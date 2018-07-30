package com.warrior.demo.aspect;

import com.warrior.framework.annotation.Aspect;
import com.warrior.framework.annotation.Controller;
import com.warrior.framework.proxy.AspectProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 计时切面，切所有的Controller类
 *
 * @author panyi on 18-7-21.
 */
@Aspect(Controller.class)
public class TimeAspect extends AspectProxy {
    private final Logger log = LoggerFactory.getLogger(TimeAspect.class);
    private static ThreadLocal<Long> BEGIN_TIME = new ThreadLocal<>();

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        long beginTime = System.currentTimeMillis();
        BEGIN_TIME.set(beginTime);
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
        long endTime = System.currentTimeMillis();
        Long beginTime = BEGIN_TIME.get();
        long duration = endTime - beginTime;
        log.info("类{}的方法{}耗时{}毫秒,开始{}结束{}.", cls.getName(), method.getName(), duration, beginTime, endTime);
    }
}

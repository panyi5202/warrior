package com.warrior.demo.aspect;

import com.warrior.framework.annotation.Aspect;
import com.warrior.framework.annotation.Controller;
import com.warrior.framework.proxy.AspectProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 日志切面，切所有的controller类
 *
 * @author panyi on 18-7-21.
 */
@Aspect(Controller.class)
public class LogAspect extends AspectProxy {
    private final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        log.info("开始调用{}的方法{}", cls.getName(), method.getName());
    }
}

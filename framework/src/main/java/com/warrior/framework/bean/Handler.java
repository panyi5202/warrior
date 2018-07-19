package com.warrior.framework.bean;

import java.lang.reflect.Method;

/**
 * 封装Action信息
 *
 * @author panyi on 18-7-18.
 */
public class Handler {
    /**
     * Controller 类
     */
    private Class<?> controllerClass;
    /**
     * Action 方法
     */
    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}

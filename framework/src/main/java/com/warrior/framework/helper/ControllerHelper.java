package com.warrior.framework.helper;

import com.warrior.framework.annotation.Action;
import com.warrior.framework.bean.Handler;
import com.warrior.framework.bean.Request;
import com.warrior.framework.util.ArrayUtil;
import com.warrior.framework.util.Constant;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器帮助类
 *
 * @author panyi on 18-7-18.
 */
public final class ControllerHelper {
    /**
     * 保存请求和处理器的映射关系
     */
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    // 初始化请求和处理器的映射关系
    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtils.isNotEmpty(controllerClassSet)) {
            controllerClassSet.forEach(controllerClass -> {
                // 获取Controller类中的方法
                Method[] controllerMethods = controllerClass.getDeclaredMethods();
                if (ArrayUtil.isNotEmpty(controllerMethods)) {
                    for (Method controllerMethod : controllerMethods) {
                        // 判断方法是否有Action标注
                        if (controllerMethod.isAnnotationPresent(Action.class)) {
                            Action actionAnnotation = controllerMethod.getAnnotation(Action.class);
                            String mapping = actionAnnotation.value();
                            if (mapping.contains(Constant.REQ_PATH_REG)) {
                                String[] reqArr = mapping.split(Constant.REQ_PATH_SEPARATOR);
                                if (ArrayUtil.isNotEmpty(reqArr) && reqArr.length == 2) {
                                    String requestMethod = reqArr[0];
                                    String requestPath = reqArr[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(controllerClass, controllerMethod);
                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}

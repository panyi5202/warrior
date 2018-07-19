package com.warrior.framework;

import com.warrior.framework.helper.BeanHelper;
import com.warrior.framework.helper.ClassHelper;
import com.warrior.framework.helper.ControllerHelper;
import com.warrior.framework.helper.IocHelper;
import com.warrior.framework.util.ClassUtil;

/**
 * 加载相应的helper
 *
 * @author panyi on 18-7-18.
 */
public final class HelperLoader {
    public static void init() {
        Class<?>[] classArr = {ClassHelper.class, BeanHelper.class, IocHelper.class, ControllerHelper.class};
        for (Class<?> cls : classArr) {
            ClassUtil.loadClass(cls.getName(), true);
        }
    }
}

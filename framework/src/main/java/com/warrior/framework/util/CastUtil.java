package com.warrior.framework.util;

/**
 * 类型转换工具类
 *
 * @author panyi on 18-7-18.
 */
public class CastUtil {
    public static long castLong(Object obj) {
        if (obj == null) {
            return 0L;
        }
        return (Long) obj;
    }
}

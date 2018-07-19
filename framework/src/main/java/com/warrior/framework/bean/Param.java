package com.warrior.framework.bean;

import com.warrior.framework.util.CastUtil;

import java.util.Map;

/**
 * 请求参数对象
 *
 * @author panyi on 18-7-18.
 */
public class Param {
    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public long getLong(String name) {
        return CastUtil.castLong(paramMap.get(name));
    }

    public String getString(String name) {
        return "" + paramMap.get(name);
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}

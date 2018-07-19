package com.warrior.framework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 视图对象
 *
 * @author panyi on 18-7-18.
 */
public class View {
    /**
     * 视图的路径
     */
    private String path;
    /**
     * 模型数据
     */
    private Map<String, Object> model;

    public View(String path) {
        this.path = path;
        this.model = new HashMap<>();
    }

    public View addModel(String key, Object value) {
        model.put(key, value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}

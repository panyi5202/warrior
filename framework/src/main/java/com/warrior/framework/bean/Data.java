package com.warrior.framework.bean;

/**
 * 返回的数据对象
 * @author panyi on 18-7-18.
 */
public class Data {
    /**
     * 模型数据
     */
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}

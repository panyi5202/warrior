package com.warrior.framework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Json工具类
 *
 * @author panyi on 18-7-19.
 */
public final class JsonUtil {
    private final static Logger log = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 把对象转成Json串
     *
     * @return String
     */
    public static String toJson(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("对象转Json出错，{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 把json串转换成对象
     *
     * @param json json串
     * @param type 目标对象类型
     * @param <T>  返回类型
     * @return T
     */
    public static <T> T parseJson(String json, Class<T> type) {
        try {
            return OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            log.error("Json转对象出错，{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

package com.warrior.framework.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 处理流的工具类
 *
 * @author panyi on 18-7-19.
 */
public final class StreamUtil {
    public static String getString(InputStream inputStream, String charsetname) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString(charsetname);
    }
}

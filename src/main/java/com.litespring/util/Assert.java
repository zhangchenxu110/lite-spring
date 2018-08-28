package com.litespring.util;

/**
 * @author 张晨旭
 * @DATE 2018/8/27
 */
public class Assert {
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }
}

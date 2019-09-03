package com.car.orbit.orbitservice.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地线程保存区,用于保存当前操作线程的token、其他当前线程级别公共信息
 */
public class LocalHolder {
    public final static String TOKEN_KEY = "token";
    private final static ThreadLocal<Map<String,Object>> LOCAL_HOLDER = new ThreadLocal<>();
    public static void put(String key,Object object) {
        Map<String,Object> local = get();
        if (local == null){
            local = new HashMap<>();
        }
        local.put(key,object);
        LOCAL_HOLDER.set(local);
    }

    public static String getCurrentToken() {
        Map<String,Object> map = get();
        String token = (String) map.get(TOKEN_KEY);
        return token;
    }

    public static Map<String,Object> get() {
        return LOCAL_HOLDER.get();
    }

    public static void remove() {
        LOCAL_HOLDER.remove();
    }
}

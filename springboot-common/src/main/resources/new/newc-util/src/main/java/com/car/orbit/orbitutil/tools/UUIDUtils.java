package com.car.orbit.orbitutil.tools;

import java.util.UUID;

/**
 * @Title: UUIDUtils
 * @Package: com.car.orbit.orbitutil.tools
 * @Description: UUID生成工具
 * @Author: monkjavaer
 * @Data: 2019/3/7 19:39
 * @Version: V1.0
 */
public class UUIDUtils {

    /**
     * 生成32位UUID
     *
     * @return String 32位UUID
     */
    public static String generate() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    public static void main(String[] args) {
        System.out.println(generate());
    }

}

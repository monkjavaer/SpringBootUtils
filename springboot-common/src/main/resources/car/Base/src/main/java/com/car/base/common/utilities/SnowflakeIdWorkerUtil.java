package com.base.springboot.car.Base.src.main.java.com.car.base.common.utilities;

import java.math.BigInteger;

/**
 * <p>Description:SnowflakeId生成工具类 </p>
 *
 * @author monkjavaer
 * @version 1.0 创建时间:2017-11-29 18:12
 */
public class SnowflakeIdWorkerUtil {

    private static final SnowflakeIdWorker WORKER = new SnowflakeIdWorker(0, 0);

    public static BigInteger generateId(){
        return WORKER.nextId();
    }
}

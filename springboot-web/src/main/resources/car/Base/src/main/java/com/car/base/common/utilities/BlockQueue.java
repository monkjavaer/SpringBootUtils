package com.car.base.common.utilities;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Eric on 2018/6/15.
 */
public class BlockQueue {

    public static int corePookSize = 5;

    public static int maximumPoolSize = 16;

    public static long keepActiveTime = 200;

    public static TimeUnit timeUnit = TimeUnit.SECONDS;

    public static BlockingQueue<Runnable> taskServiceQueue = new ArrayBlockingQueue<Runnable>(5);
}

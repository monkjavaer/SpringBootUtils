package com.car.base.common.utilities;

import java.math.BigInteger;

/**
 * <p>Description:Twitter的分布式自增ID算法Snowflake实现 </p>
 *
 * SnowFlake的结构如下(每部分用-分开):<br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 <br>
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 * 41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)得到的值，
 * 这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。
 * 41位的时间截，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
 * 10位的数据机器位，可以部署在1024个节点，包括5位dataCenterId和5位workerId<br>
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号<br>
 * 加起来刚好64位，为一个Long型。<br>
 * <br>
 *     修改：10位机器位缩减成4位，12位序列缩减成10位，再加上1位标识，满足JS最长16位数字的要求，16位之后会丢失精度
 *     workerIdBits = 5L;改为workerIdBits = 2L;
 *     dataCenterIdBits = 5L;改为dataCenterIdBits = 2L;
 *     sequenceBits = 12L;改为sequenceBits = 10L;
 * <br/>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 *
 * @author monkjavaer
 * @version 1.0 创建时间:2017-11-27 16:38
 */
public class SnowflakeIdWorker {

    /** 开始时间戳（2017-11-27 0:0:0） */
    private final long startTimeStamp = 1517414400000L;

    /** 机器ID所占的位数 */
    private final long workerIdBits = 2L;

    /** 数据标识ID所占的位数 */
    private final long dataCenterIdBits = 2L;

    /** 支持的最大机器ID，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /** 支持的最大数据标识ID，结果是31 */
    private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);

    /** 序列在ID中的位数 */
    private final long sequenceBits = 10L;

    /** 机器ID向左移12位 */
    private final long workerIdShift = sequenceBits;

    /** 数据标识ID向左移17位（12+5） */
    private final long dataCenterIdShift = sequenceBits + workerIdBits;

    /** 时间戳向左移22位（5+5+12） */
    private final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

    /** 生成序列的掩码，这里为4095 */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** 工作机器ID（0-31） */
    private long workerId;

    /** 数据中心ID（0-31） */
    private long dataCenterId;

    /** 毫秒内序列（0-4095） */
    private long sequence = 0L;

    /** 上次生成ID的时间戳 */
    private long lastTimestamp = -1L;

    /**
     *  构造函数
     */
    public SnowflakeIdWorker(long workerId, long dataCenterId){
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0",
                    maxWorkerId));
        }
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("dataCenter Id can't be greater than %d or less than 0",
                    maxDataCenterId));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    /**
     *  获得下一个ID，线程安全
     * @return 下一个ID
     */
    public synchronized BigInteger nextId(){
        long timestamp = timeGen();
        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退，这个时候抛异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }
        // 如果是同一时间生成的，则进行毫秒内序列
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
            // 毫秒内序列溢出
            if (sequence == 0) {
                // 阻塞到下一毫秒，获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        // 时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }
        // 上次生成ID的时间戳
        lastTimestamp = timestamp;
        // 移位并通过运算生成拼到一起组成64位的ID
        return BigInteger.valueOf(((timestamp - startTimeStamp) << timestampLeftShift)
                | (dataCenterId << dataCenterIdShift)
                | (workerId << workerIdShift)
                | sequence);
    }

    /**
     *  阻塞到下一毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成Id的时间戳
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp){
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     *  返回以毫秒为单位的当前时间戳
     * @return 当前时间戳（毫秒）
     */
    private long timeGen(){
        return System.currentTimeMillis();
    }
}

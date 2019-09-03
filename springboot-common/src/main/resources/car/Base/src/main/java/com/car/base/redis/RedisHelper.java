package com.base.springboot.car.Base.src.main.java.com.car.base.redis;

import com.alibaba.fastjson.JSON;
import com.base.springboot.car.Base.src.main.java.com.car.base.common.utilities.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class RedisHelper {

    private static JedisSentinelPool jedisSentinelPool = null;

    private static JedisPool jedisPool = null;

    private static String mode = "single";

    private final static String SENTINEL_MODE = "sentinel";

    private final static String SINGLE_MODE = "single";
    private static String host;
    private static int port;
    private static String sentinelIps;
    private static String sentinelPorts;
    private static int maxIdle = 300;
    private static int maxActive = 600;

    private static Logger logger = LoggerFactory.getLogger(RedisHelper.class);

    static {
        try {
            PropertiesUtil propertiesUtil = new PropertiesUtil("/redis.properties");
            mode = propertiesUtil.getPropertieValue("redis.mode");
            if (SENTINEL_MODE.equals(mode)){
                sentinelIps = propertiesUtil.getPropertieValue("redis-sentinel.ip");
                sentinelPorts = propertiesUtil.getPropertieValue("redis-sentinel.port");
            }else {
                host = propertiesUtil.getPropertieValue("redis.host");
                port = Integer.valueOf(propertiesUtil.getPropertieValue("redis.port"));
            }
            maxIdle = Integer.valueOf(propertiesUtil.getPropertieValue("redis.maxIdle"));
            maxActive = Integer.valueOf(propertiesUtil.getPropertieValue("redis.maxActive"));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("RedisHelper init error!!!!!,Message is " + e.getMessage());
        }
    }

    private synchronized static void initRedisConnection() {
        if (jedisSentinelPool == null && jedisPool == null) {
            try {

                if (SENTINEL_MODE.equals(mode)) {
                    Set<String> sentinels = new HashSet<String>();
                    String[] ipArray = sentinelIps.split(",");
                    String[] portArray = sentinelPorts.split(",");

                    for (int i = 0; i < ipArray.length; i++) {
                        HostAndPort hostAndPort = new HostAndPort(ipArray[i], Integer.parseInt(portArray[i]));
                        sentinels.add(hostAndPort.toString());

                    }

                    //TODO 可以创建GenericObjectPoolConfig
                    // 创建JedisSentinelPool对象
                    jedisSentinelPool = new JedisSentinelPool("mymaster", sentinels);


                } else {
                    //                     创建jedis池配置实例
                    JedisPoolConfig config = new JedisPoolConfig();

                    //连接耗尽时是否阻塞, false报异常,true阻塞直到超时, 默认true
                    config.setBlockWhenExhausted(true);

                    //设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
                    config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");

                    //是否启用pool的jmx管理功能, 默认true
                    config.setJmxEnabled(true);

                    //MBean ObjectName = new ObjectName("org.apache.commons.pool2:type=GenericObjectPool,name=" + "pool" + i); 默 认为"pool", JMX不熟,具体不知道是干啥的...默认就好.
                    config.setJmxNamePrefix("pool");

                    //是否启用后进先出, 默认true
                    config.setLifo(true);

                    //最大空闲连接数, 默认8个
                    config.setMaxIdle(maxIdle);

                    //最大连接数, 默认8个
                    config.setMaxTotal(maxActive);

                    //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
                    config.setMaxWaitMillis(30 * 1000);

                    //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
                    config.setMinEvictableIdleTimeMillis(1800000);

                    //最小空闲连接数, 默认0
                    config.setMinIdle(0);

                    //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
                    config.setNumTestsPerEvictionRun(3);

                    //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
                    config.setSoftMinEvictableIdleTimeMillis(3 * 60 * 1000);

                    //在获取连接的时候检查有效性, 默认false
                    config.setTestOnBorrow(false);

                    //在空闲时检查有效性, 默认false
                    config.setTestWhileIdle(false);

                    //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
                    config.setTimeBetweenEvictionRunsMillis(-1);

                    //根据配置实例化jedis池
                    jedisPool = new JedisPool(config, host, port, 100000);
                }


            } catch (Exception e) {
                logger.warn("redis无法连接,", e);
            }
        }
    }


    /**
     * 获取Jedis实例
     *
     * @return
     */
    public static Jedis getJedis() {
        Jedis jedis = null;
        try {
            initRedisConnection();

            if (SINGLE_MODE.equals(mode)) {
                jedis = jedisPool.getResource();
            } else {
                jedis = jedisSentinelPool.getResource();
            }
        } catch (Exception e) {
            e.printStackTrace();
//            try {
//                Thread.sleep(1000);//异常后睡眠一会儿
//            } catch (InterruptedException e1) {
//                e1.printStackTrace();
//            }
//            initRedisConnection();
//
//            if (pool != null) {
//                jedis = pool.getResource();
//            }
        }
        return jedis;
    }


    /**
     * 允许抛出异常的get
     * 查询数据
     */
    @SuppressWarnings("deprecation")
    public static String getWithException(String key) throws Exception {
        Jedis jedis = null;
        String str = null;
        try {
            jedis = getJedis();
            if (jedis == null) {
                throw new Exception("=====getJedisFailed======");
            }
            str = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (jedis != null) {
                close(jedis);
            }
        }
        return str;

    }

    /**
     * 查询数据
     */
    @SuppressWarnings("deprecation")
    public static String get(String key) {
        Jedis jedis = getJedis();
        String str = null;
        try {
            str = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        close(jedis);
        return str;

    }

    /**
     * 根据正则查询KEYS的数据
     */
    @SuppressWarnings("deprecation")
    public static Set<String> getKeys(String pattern) {
        Jedis jedis = getJedis();
        Set<String> result = null;
        try {
            result = jedis.keys(pattern);
        } catch (Exception e) {
            e.printStackTrace();
        }

        close(jedis);
        return result;

    }


    /**
     * 删除缓存中得对象，根据key
     *
     * @param key
     * @return
     */
    @SuppressWarnings("deprecation")
    public static boolean del(String key) {
        Jedis jedis = getJedis();
        try {
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
//            e.printStackTrace();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e1) {
//                e1.printStackTrace();
//            }
//            jedis = getJedis();
//            jedis.del(key);
        }
        close(jedis);
        return true;
    }


    /**
     * 存储对象
     *
     * @param key
     * @param str
     */
    public static boolean save(String key, String str) {
        Jedis jedis = getJedis();
        try {
            jedis.set(key, str);
        } catch (Exception e) {
            e.printStackTrace();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e1) {
//                e1.printStackTrace();
//            }
//            jedis = getJedis();
//            jedis.set(key, str);

        }
        close(jedis);
        return true;
    }

    /**
     * get a redis cache
     *
     * @param key   redis key
     * @param field redis field
     * @return redis value in json format
     */
    public static String get(String key, String field) {
        Jedis jedis = getJedis();
        String value = jedis.hget(key, field);
        close(jedis);
        return value;
    }


    /**
     * add single redis cache
     *
     * @param key       redis key
     * @param field     redis field
     * @param valueJson redis value in json format
     * @return if add success
     */
    public static boolean save(String key, String field, String valueJson) {
        Jedis jedis = getJedis();
        long reply = jedis.hset(key, field, valueJson);
        jedis.close();
        return reply == 1;
    }

    /**
     * 存储对象
     *
     * @param key
     * @param objects
     */
    public static boolean saveObject(String key, Object objects) {
        Jedis jedis = getJedis();
        try {
            String jsonStr = JSON.toJSONString(objects);
            jedis.set(key, jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e1) {
//                e1.printStackTrace();
//            }
//            jedis = getJedis();
//            jedis.set(key, str);

        }
        close(jedis);
        return true;
    }

    /**
     * 存储对象并设定超时时间
     *
     * @param key
     * @param objects
     * @param seconds
     *
     */
    public static boolean saveObjectEx(String key, Object objects, int seconds) {
        Jedis jedis = getJedis();
        try {
            String jsonStr = JSON.toJSONString(objects);
            jedis.setex(key, seconds, jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e1) {
//                e1.printStackTrace();
//            }
//            jedis = getJedis();
//            jedis.setex(key, seconds, str);
        }
        close(jedis);
        return true;
    }

    /**
     * 存储对象,有异常则抛出
     *
     * @param key
     * @param str
     */
    public static boolean saveThrowException(String key, String str) throws Exception {
        Jedis jedis = getJedis();
        try {
            jedis.set(key, str);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        close(jedis);
        return true;
    }

    /**
     * 存储对象并设定超时时间
     *
     * @param key
     * @param str
     */
    public static boolean saveEx(String key, int seconds, String str) {
        Jedis jedis = getJedis();
        try {
            jedis.setex(key, seconds, str);
        } catch (Exception e) {
            e.printStackTrace();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e1) {
//                e1.printStackTrace();
//            }
//            jedis = getJedis();
//            jedis.setex(key, seconds, str);
        }
        close(jedis);
        return true;
    }


    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    private static void close(final Jedis jedis) {

        if (jedis != null) {
            jedis.close();
        }
    }

    /*************************************************************************************************************************************
     ** @Description 刪除RedisLockKey
     ** @Author Ding
     ** @Date 2018/2/9 15:36
     **
     ************************************************************************************************************************************/
    public static void delRedisLock(String key) {
        key = "RedisLock:" + key;
        Jedis jedis = getJedis();
        jedis.del(key);
        close(jedis);

    }

    /**
     * @Description 检测是否存在相同键，若没有则加上  seconds=0代表无限
     * @Author Ding
     * @Date 2018/1/23 17:16
     */
    public static boolean checkEx(String key, int seconds) {
        key = "RedisLock:" + key;
        Jedis jedis = getJedis();
        try {
            if ("1".equals(jedis.get(key))) {
                close(jedis);
                return false;
            } else {
                if (0 != seconds) {
                    jedis.setex(key, seconds, "1");
                    close(jedis);
                    return true;
                } else {
                    jedis.set(key, "1");
                    close(jedis);
                    return true;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            close(jedis);
            return false;
        }

    }


    /**
     * @Description 检测是否存在相同键
     * @Author Ding
     * @Date 2018/1/23 17:16
     */
    public static boolean checkEx(String key) {
        key = "RedisLock:" + key;
        Jedis jedis = getJedis();
        try {
            if ("1".equals(jedis.get(key))) {
                close(jedis);
                return false;
            } else {
                close(jedis);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            close(jedis);
            return false;
        }

    }


    public static void setEx(String key, int seconds) {
        Jedis jedis = RedisHelper.getJedis();
        try {
            key = "RedisLock:" + key;
            jedis.setex(key, seconds, "1");
        } catch (Exception e) {
            e.printStackTrace();
        }

        close(jedis);
    }


    public static Date getNowTime() {
        Jedis jedis = RedisHelper.getJedis();
        try {
            Date date = new Date(Long.parseLong(jedis.time().get(0)) * 1000);
            close(jedis);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            close(jedis);
            return null;
        }

    }


    public static long getNowTimeLong() {
        Jedis jedis = RedisHelper.getJedis();
        try {
            long nowTime = Long.parseLong(jedis.time().get(0)) * 1000;
            close(jedis);
            return nowTime;
        } catch (Exception e) {
            e.printStackTrace();
            close(jedis);
            return 0;
        }

    }
}

package com.car.orbit.orbitutil.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Tuple;

import java.util.*;

/**
 * CreateDate：2018/5/22 <br/>
 * Author：monkjavaer <br/>
 * Description: utils for redis operate(all content will be stored in json format)
 **/
public class RedisClient {

    /**
     * redis connect pool
     */
    private JedisPool pool;

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";//毫秒
    private static final String SET_WITH_EXPIRE_EX_TIME = "EX";//秒
    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * init redis connect pool
     *
     * @param config redis config
     * @param host redis server host
     * @param port redis server port
     * @param timeout redis server connection timeout
     */
    public RedisClient(JedisPoolConfig config, String host, int port, int timeout, String password, int database) {
//        pool = new JedisPool(config, host, port, timeout);
        pool = new JedisPool(config, host, port, timeout, password, database);
    }

    /**
     * 获取所有的key
     *
     * @return 所有的key的set集合
     */
    public Set<String> getAllKeys() {
        Jedis jedis = getJedis();
        Set<String> keys = jedis.keys("*");
        close(jedis);
        return keys;
    }

    /**
     * 是否已经存在某个key
     *
     * @param key key
     * @return true-存在，false-不存在
     */
    public boolean existsKey(String key) {
        Jedis jedis = getJedis();
        boolean exists = jedis.exists(key);
        close(jedis);
        return exists;
    }

    /**
     * 存储string
     *
     * @param key
     * @param value
     */
    public boolean setString(String key, String value) {
        Jedis jedis = getJedis();
        String reply = jedis.set(key, value);
        close(jedis);
        return "OK".equalsIgnoreCase(reply);
    }

    /**
     * 存储对象
     *
     * @param key
     * @param objects
     */
    public boolean setObject(String key, Object objects) {
        Jedis jedis = getJedis();
        String jsonStr = JSON.toJSONString(objects);
        String reply = jedis.set(key, jsonStr);
        close(jedis);
        return "OK".equalsIgnoreCase(reply);
    }

    /**
     * 存储对象并设定超时时间
     *
     * @param key
     * @param objects
     * @param seconds
     */
    public boolean setObjectEx(String key, Object objects, int seconds) {
        Jedis jedis = getJedis();
        String jsonStr = JSON.toJSONString(objects);
        String reply = jedis.setex(key, seconds, jsonStr);
        close(jedis);
        return "OK".equalsIgnoreCase(reply);
    }

    /**
     * 查询数据
     */
    public String getJsonString(String key) {
        Jedis jedis = getJedis();
        String str = jedis.get(key);
        close(jedis);
        return str;
    }

    /**
     * 查询对象数据
     */
    public Object getObject(String key, Object object) {
        if (object instanceof java.lang.String) {
            return getJsonString(key);
        } else {
            String str = getJsonString(key);
            if (StringUtils.isNotEmpty(str)) {
                return JSON.parseObject(str, object.getClass());
            } else {
                return null;
            }
        }
    }

    /**
     * add single redis cache
     *
     * @param key redis key
     * @param field redis field
     * @param value redis value
     * @param expire expire time
     * @return if add success
     */
    public boolean saveSingleByExpire(String key, String field, Object value, int expire) {
        Jedis jedis = getJedis();
        long reply = jedis.hset(key, field, JSON.toJSONString(value, SerializerFeature.DisableCircularReferenceDetect));
        jedis.expire(key, expire);
        jedis.close();
        return reply == 1;
    }

    /**
     * add single redis cache
     *
     * @param key redis key
     * @param field redis field
     * @param value redis value
     * @return if add success
     */
    public boolean save(String key, String field, Object value) {
        Jedis jedis = getJedis();
        long reply = jedis.hset(key, field, JSONObject.toJSONString(value));
        jedis.close();
        return reply == 1;
    }

    /**
     * 保存key-value
     *
     * @param key key
     * @param value value
     * @return 是否成功
     */
    public boolean save(String key, String value) {
        Jedis jedis = getJedis();
        String result = jedis.set(key, value);
        close(jedis);
        return "OK".equals(result);
    }

    /**
     * add single redis cache
     *
     * @param key redis key
     * @param field redis field
     * @param valueJson redis value in json format
     * @return if add success
     */
    public boolean save(String key, String field, String valueJson) {
        Jedis jedis = getJedis();
        long reply = jedis.hset(key, field, valueJson);
        jedis.close();
        return reply == 1;
    }

    /**
     * 设置超时时间
     *
     * @param key key
     * @param expire 超时时间，单位 秒
     * @return true-成功，false-失败
     */
    public boolean setExpireTime(String key, int expire) {
        Jedis jedis = getJedis();
        Long result = jedis.expire(key, expire);
        close(jedis);
        return 1 == result;
    }

    /**
     * batch save multi data to redis
     *
     * @param key redis key
     * @param data data to store, key is redis field
     * @return if batch save success
     */
    public boolean batchSave(String key, Map<String, Object> data) {
        Jedis jedis = getJedis();
        Map<String, String> jsonData = new HashMap<>(); //transfer data value to json format
        for (String field : data.keySet()) {
            jsonData.put(field, JSONObject.toJSONString(data.get(field)));
        }
        String reply = jedis.hmset(key, jsonData);
        close(jedis);
        return "OK".equalsIgnoreCase(reply);
    }

    /**
     * batch save multi data to redis
     *
     * @param key redis key
     * @param data data to store, key is redis field, value is json
     * @return if batch save success
     */
    public boolean batchSaveJson(String key, Map<String, String> data) {
        Jedis jedis = getJedis();
        String reply = jedis.hmset(key, data);
        close(jedis);
        return "OK".equalsIgnoreCase(reply);
    }

    /**
     * Check key exist or not
     *
     * @param key
     * @return
     */
    public boolean checkKeyExist(String key) {
        Jedis jedis = getJedis();
        boolean exist = jedis.exists(key);
        close(jedis);
        return exist;
    }

    /**
     * get all cached data in given redis key
     *
     * @param key redis key
     * @return all cached data,key is redis field
     */
    public Map<String, String> get(String key) {
        Jedis jedis = getJedis();
        Map<String, String> data = jedis.hgetAll(key);
        close(jedis);
        return data;
    }

    /**
     * get all cached data in given redis key
     *
     * @param key redis key
     * @param clazz cached data type
     * @return all cached data,key is redis field
     */
    public <T> Map<String, T> get(String key, Class<T> clazz) {
        Map<String, T> resultMap = new HashMap<>();
        Jedis jedis = getJedis();
        Map<String, String> data = jedis.hgetAll(key);
        for (String field : data.keySet()) {
            JSONObject jsonCacheObj = JSONObject.parseObject(data.get(field));
            T cacheData = JSONObject.toJavaObject(jsonCacheObj, clazz);
            resultMap.put(field, cacheData);
        }
        close(jedis);
        return resultMap;
    }

    /**
     * 获取key-value的直接value，针对直接存储key-value的数据
     *
     * @param key key
     * @param clazz value的类
     * @param <T> value的类
     * @return 转化成Java对象的value值
     */
    public <T> T getValue(String key, Class<T> clazz) {
        Jedis jedis = getJedis();
        JSONObject json = JSONObject.parseObject(jedis.get(key));
        T cacheData = JSONObject.toJavaObject(json, clazz);
        close(jedis);
        return cacheData;
    }

    /**
     * get a redis cache
     *
     * @param key redis key
     * @param field redis field
     * @return redis value in json format
     */
    public String get(String key, String field) {
        Jedis jedis = getJedis();
        String value = jedis.hget(key, field);
        close(jedis);
        return value;
    }

    /**
     * get a redis cache
     *
     * @param key redis key
     * @param field redis field
     * @param clazz redis value type
     * @return redis cache value
     */
    public <T> T get(String key, String field, Class<T> clazz) {
        Jedis jedis = getJedis();
        String jsonData = jedis.hget(key, field);
        JSONObject jsonCacheObj = JSONObject.parseObject(jsonData);
        T retureObj = JSONObject.toJavaObject(jsonCacheObj, clazz);
        close(jedis);
        return retureObj;
    }

    /**
     * delete all redis cache in given key
     *
     * @param key redis key
     * @return if delete success
     */
    public boolean delete(String key) {
        Jedis jedis = getJedis();
        long reply = jedis.del(key);
        close(jedis);
        return reply == 1;
    }

    /**
     * delete redis cache in given key and fields
     *
     * @param key redis key
     * @param fields redis fields
     * @return if delete success
     */
    public boolean delete(String key, String... fields) {
        Jedis jedis = getJedis();
        long reply = jedis.hdel(key, fields);
        close(jedis);
        return reply == 1;
    }

    /**
     * get all cache fields in given key
     *
     * @param key redis key
     * @return cache fields
     */
    public Set<String> getFields(String key) {
        Jedis jedis = getJedis();
        Set<String> fields = jedis.hkeys(key);
        close(jedis);
        return fields;
    }

    /**
     * get all redis cache values in given key(only used in level two cache)
     *
     * @param key redis key
     * @return cache values
     */
    public List<String> getValues(String key) {
        Jedis jedis = getJedis();
        List<String> values = jedis.hvals(key);
        close(jedis);
        return values;
    }

    /**
     * get all redis cache values in given key(only used in level two cache)
     *
     * @param key redis key
     * @param clazz redis cache value type
     * @return cache values
     */
    public <T> List<T> getValues(String key, Class<T> clazz) {
        Jedis jedis = getJedis();
        List<T> returnObjs = new ArrayList<>();
        List<String> values = jedis.hvals(key);
        for (String value : values) {
            JSONObject jsonCacheObj = JSONObject.parseObject(value);
            returnObjs.add(JSONObject.toJavaObject(jsonCacheObj, clazz));
        }
        close(jedis);
        return returnObjs;
    }

    /**
     * 按照分页方式存储list数据
     *
     * @param key redis key
     * @param data 待存储的list类型数据
     * @return
     */
    public void saveListWithPage(String key, List<? extends Object> data) {
        Jedis jedis = getJedis();
        Map<String, Double> sourceMember = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            double score = i;
            sourceMember.put(i + "--!!" + JsonUtils.toJSONString(data.get(i)), score);//加上前缀避免转json后完全相同的情况
        }
        if (data.size() > 0) {
            jedis.zadd(key, sourceMember);
        }
        close(jedis);
    }

    /**
     * 按照分页方式存储list数据
     *
     * @param key redis key
     * @param data 待存储的list类型数据
     * @param seconds 有效时间，单位为秒
     * @return
     */
    public void saveListWithPageEx(String key, List<? extends Object> data, final int seconds) {
        Jedis jedis = getJedis();
        Map<String, Double> sourceMember = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            double score = i;
            sourceMember.put(i + "--!!" + JsonUtils.toJSONString(data.get(i)), score);//加上前缀避免转json后完全相同的情况
        }
        if (data.size() > 0) {
            jedis.zadd(key, sourceMember);
            jedis.expire(key, seconds);
        }
        close(jedis);
    }

    /**
     * 统计有序集合的数量
     *
     * @param key
     * @return
     */
    public Integer getTotalPageSize(String key) {
        Jedis jedis = getJedis();
        Long count = jedis.zcard(key);
        close(jedis);
        return count.intValue();
    }

    /**
     * 分页查询缓存数据
     *
     * @param key redis key
     * @param start 起始位置
     * @param size 大小
     * @return
     */
    public <T> List<T> getListWithPage(String key, int start, int size, Class<T> clz) {
        Jedis jedis = getJedis();
        List<T> result = new ArrayList<>();
        if (size <= 0) {
            return result;
        }
        Set<Tuple> tuples = jedis.zrangeWithScores(key, start, start + size - 1);
        for (Tuple tuple : tuples) {
            result.add(JsonUtils.toBean(tuple.getElement().split("--!!")[1], clz));
        }
        close(jedis);
        return result;
    }

    /**
     * 设置key的过期时间
     *
     * @param key redis key
     * @param seconds 有效时间，单位为秒
     * @return Integer reply, specifically: 1: the timeout was set. 0: the timeout was not set since
     * *         the key already has an associated timeout (this may happen only in Redis versions &lt;
     * *         2.1.3, Redis &gt;= 2.1.3 will happily update the timeout), or the key does not exist.
     */
    public Long expire(final String key, final int seconds) {
        Jedis jedis = getJedis();
        long t = jedis.expire(key, seconds);
        close(jedis);
        return t;
    }


    /**
     * 尝试获取分布式锁
     *
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间 这里用PX，为毫秒
     * @return 是否获取成功
     */
    public boolean tryGetLock(String lockKey, String requestId, long expireTime) {
        lockKey = "RedisLock:" + lockKey;
        Jedis jedis = getJedis();
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        close(jedis);
        return LOCK_SUCCESS.equals(result);
    }

    /**
     * 尝试获取分布式锁
     *
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间 EX，为秒
     * @return 是否获取成功
     */
    public boolean tryGetLockEx(String lockKey, String requestId, int expireTime) {
        lockKey = "RedisLock:" + lockKey;
        Jedis jedis = getJedis();
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_EX_TIME, expireTime);
        close(jedis);
        return LOCK_SUCCESS.equals(result);
    }

    /**
     * 释放分布式锁
     *
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean releaseLock(String lockKey, String requestId) {
        lockKey = "RedisLock:" + lockKey;
        Jedis jedis = getJedis();
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        close(jedis);
        return RELEASE_SUCCESS.equals(result);
    }

    /**
     * get a jedis instance from redis pool
     *
     * @return jedis instance
     */
    private synchronized Jedis getJedis() {
        return pool.getResource();
    }

    /**
     * close jedis instance and release connection back to redis pool
     *
     * @param jedis jedis instance to be closed
     */
    private void close(Jedis jedis) {
        if (!Objects.isNull(jedis)) {
            jedis.close();
        }
    }
}

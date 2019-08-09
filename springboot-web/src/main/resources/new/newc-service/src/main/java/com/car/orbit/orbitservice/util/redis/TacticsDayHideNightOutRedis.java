package com.car.orbit.orbitservice.util.redis;


import com.car.orbit.orbitservice.constant.EsConstant;
import com.car.orbit.orbitservice.vo.DayHideNightOutVO;
import com.car.orbit.orbitutil.tools.PropertyReaderUtils;

import java.util.List;

/**
 * 战法==昼伏夜出缓存的数据
 *
 * @author zks
 */
public class TacticsDayHideNightOutRedis extends BaseBusinessRedis {
    private static String dayNightKey = "DayHideNightOut:";
    private static String nightKey = "NightOut:";
    private static String countList = "CountList";
    private static int expireSeconds = Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE));

    /**
     * 获取昼伏夜出统计列表KEY
     */
    private static String getDayNightCountKey(String key) {
        return dayNightKey + key + SPLIT + countList;
    }

    /**
     * 获取夜间频出统计列表KEY
     */
    private static String getNightCountKey(String key) {
        return nightKey + key + SPLIT + countList;
    }

    /**
     * check查询的KEY值是否在redis中有缓存
     */
    public static boolean checkKeyExist(String searchKey, boolean isDayNight) {
        if (isDayNight) {
            return redisClient.checkKeyExist(getDayNightCountKey(searchKey));
        } else {
            return redisClient.checkKeyExist(getNightCountKey(searchKey));
        }
    }

    /**
     * 保存到Redis
     */
    public static void saveWithPage(String searchKey, List<DayHideNightOutVO> list, boolean isDayNight) {
        if (isDayNight) {
            redisClient.saveListWithPageEx(getDayNightCountKey(searchKey), list, expireSeconds);
        } else {
            redisClient.saveListWithPageEx(getNightCountKey(searchKey), list, expireSeconds);
        }
    }

    public static List<DayHideNightOutVO> getWithPage(String key, int start, int size, boolean isDayNight) {
        if (isDayNight) {
            return redisClient.getListWithPage(getDayNightCountKey(key), start, size, DayHideNightOutVO.class);
        } else {
            return redisClient.getListWithPage(getNightCountKey(key), start, size, DayHideNightOutVO.class);
        }
    }

    public static int getTotalCount(String key, boolean isDayNight) {
        if (isDayNight) {
            return redisClient.getTotalPageSize(getDayNightCountKey(key));
        } else {
            return redisClient.getTotalPageSize(getNightCountKey(key));
        }
    }

    public static void expire(String key, boolean dayNightFlag) {
        if (dayNightFlag) {
            redisClient.expire(getDayNightCountKey(key), expireSeconds);
        } else {
            redisClient.expire(getNightCountKey(key), expireSeconds);
        }
    }
}

package com.car.orbit.orbitservice.util.redis;

import com.car.orbit.orbitservice.entity.OrbitControlWhitelist;
import com.car.orbit.orbitutil.tools.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 白名单缓存 todo
 * @author zks
 */
public class WhitelistRedis extends BaseBusinessRedis {

    /**
     * 获取所有白名单信息
     * @return
     */
    public static List<OrbitControlWhitelist> getAll(){
        return redisClient.getValues(WHITELIST_KEY, OrbitControlWhitelist.class);
    }

    /**
     * 获取所有白名单VID信息
     * @return
     */
    public static List<String> getAllVid(){
        List<String> whiteList = new ArrayList<>();
        List<OrbitControlWhitelist> tmp = redisClient.getValues(WHITELIST_KEY, OrbitControlWhitelist.class);
        for (OrbitControlWhitelist orbitControlBlacklist : tmp){
            whiteList.add(orbitControlBlacklist.getVid());
        }
        return whiteList;
    }

    /**
     * 根据VID获取白名单信息
     * @param vid
     * @return
     */
    public static OrbitControlWhitelist getByVid(String vid) {
        String value = redisClient.get(WHITELIST_KEY).get(vid);
        if (!StringUtils.isEmpty(value)) {
            OrbitControlWhitelist vo = JsonUtils.toBean(value, OrbitControlWhitelist.class);
            return vo;
        }else{
            return null;
        }
    }

    /**
     * 根据VID判断是否在黑名单中
     * @param vid
     * @return
     */
    public static boolean isInWhitelist(String vid) {
        OrbitControlWhitelist whitelist = getByVid(vid);
        return (whitelist != null) ? true : false;
    }

    /**
     * 根据vid移除黑名单中
     * @param vid
     * @return
     */
    public static void remove(String vid) {
        redisClient.delete(WHITELIST_KEY, vid);
    }

    /**
     * 保存对象
     * @param vo
     */
    public static void saveVo(OrbitControlWhitelist vo) {
        redisClient.save(WHITELIST_KEY, vo.getVid(), JsonUtils.toJSONString(vo));
    }

    /**
     * 保存列表
     * @param list
     */
    public static void saveVoList(List<OrbitControlWhitelist> list) {
        if(list == null ){
            return;
        }
        for(OrbitControlWhitelist vo : list){
            saveVo(vo);
        }
    }
}

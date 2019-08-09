package com.car.orbit.orbitservice.util.redis;

import com.car.orbit.orbitservice.entity.OrbitControlBlacklist;
import com.car.orbit.orbitservice.enums.StatusEnum;
import com.car.orbit.orbitutil.tools.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 黑名单缓存【不包含delete了的】
 * @author zks
 */
public class BlacklistRedis extends BaseBusinessRedis {

    /**
     * 获取所有黑名单信息
     * @return
     */
    public static List<OrbitControlBlacklist> getAll(){
        return redisClient.getValues(BLACKLIST_KEY, OrbitControlBlacklist.class);
    }

    /**
     * 根据VID获取黑名单信息
     * @param vid
     * @return
     */
    public static OrbitControlBlacklist getByVid(String vid) {
        String value = redisClient.get(BLACKLIST_KEY).get(vid);
        if (!StringUtils.isEmpty(value)) {
            OrbitControlBlacklist vo = JsonUtils.toBean(value, OrbitControlBlacklist.class);
            return vo;
        }else{
            return null;
        }
    }

    /**
     * 根据VID判断是否在黑名单中,并且处于开启状态
     * @param vid
     * @return
     */
    public static boolean isInBlacklist(String vid) {
        OrbitControlBlacklist blacklist = getByVid(vid);
        return (blacklist != null) ? true : false;
    }

    /**
     * 根据vid移除黑名单中
     * @param vid
     * @return
     */
    public static void remove(String vid) {
        redisClient.delete(BLACKLIST_KEY, vid);
    }

    /**
     * 保存对象
     * @param vo
     */
    public static void saveVo(OrbitControlBlacklist vo) {
        redisClient.save(BLACKLIST_KEY, vo.getVid(), JsonUtils.toJSONString(vo));
    }

    /**
     * 保存列表
     * @param list
     */
    public static void saveVoList(List<OrbitControlBlacklist> list) {
        if(list == null ){
            return;
        }
        for(OrbitControlBlacklist vo : list){
            saveVo(vo);
        }
    }
}

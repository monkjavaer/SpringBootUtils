package com.car.orbit.orbitservice.util.redis;

import com.car.orbit.orbitservice.entity.OrbitResRoadcrossPoint;
import com.car.orbit.orbitutil.tools.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 路口卡口信息缓存
 * @author zks
 */
public class RoadcrossPointRedis extends BaseBusinessRedis {

    /**
     * 获取所有
     * @return
     */
    public static List<OrbitResRoadcrossPoint> getAll(){
        return redisClient.getValues(DIC_ROAD_KEY,OrbitResRoadcrossPoint.class);
    }

    /**
     * 根据code获取对象
     * @param code
     * @return
     */
    public static OrbitResRoadcrossPoint getRoadcrossPointByCode(String code) {
        String value = redisClient.get(DIC_ROAD_KEY).get(code);
        if (!StringUtils.isEmpty(value)) {
            OrbitResRoadcrossPoint vo = JsonUtils.toBean(value, OrbitResRoadcrossPoint.class);
            return vo;
        }else{
            return null;
        }
    }

    /**
     * 根据code获取名称
     * @param code
     * @return
     */
    public static String getNameByCode(String code) {
        OrbitResRoadcrossPoint point = getRoadcrossPointByCode(code);
        if(point==null){
            return null;
        }
        return point.getName();
    }

    /**
     * 保存对象
     * @param vo
     */
    public static void saveVo(OrbitResRoadcrossPoint vo) {
        redisClient.save(DIC_ROAD_KEY, vo.getId(), JsonUtils.toJSONString(vo));
    }

    /**
     * 保存列表
     * @param list
     */
    public static void saveVoList(List<OrbitResRoadcrossPoint> list) {
        if(list == null ){
            return;
        }
        for(OrbitResRoadcrossPoint vo : list){
            saveVo(vo);
        }
    }
}

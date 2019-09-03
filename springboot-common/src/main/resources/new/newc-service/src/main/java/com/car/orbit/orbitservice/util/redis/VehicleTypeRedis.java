package com.car.orbit.orbitservice.util.redis;

import com.car.orbit.orbitservice.entity.OrbitVehicleType;
import com.car.orbit.orbitutil.tools.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * city信息缓存
 * @author zks
 */
public class VehicleTypeRedis extends BaseBusinessRedis {

    /**
     * 获取所有城市信息
     * @return
     */
    public static List<OrbitVehicleType> getAllVehicleType(){
        return redisClient.getValues(DIC_VEHICLETYPE_KEY,OrbitVehicleType.class);
    }

    /**
     * 根据code获取城市信息
     * @param code
     * @return
     */
    public static OrbitVehicleType getCityByCode(String code) {
        String value = redisClient.get(DIC_VEHICLETYPE_KEY).get(code);
        if (!StringUtils.isEmpty(value)) {
            OrbitVehicleType vo = JsonUtils.toBean(value, OrbitVehicleType.class);
            return vo;
        }else{
            return null;
        }
    }

    /**
     * 根据code名称信息
     * @param code
     * @return
     */
    public static String getNameByCode(String code) {
        String value = redisClient.get(DIC_VEHICLETYPE_KEY).get(code);
        return getInternationalName(value);
    }

    /**
     * 保存对象
     * @param vo
     */
    public static void saveVo(OrbitVehicleType vo) {
        redisClient.save(DIC_VEHICLETYPE_KEY, vo.getCode(), JsonUtils.toJSONString(vo));
    }

    /**
     * 保存列表
     * @param list
     */
    public static void saveVoList(List<OrbitVehicleType> list) {
        if(list == null ){
            return;
        }
        for(OrbitVehicleType vo : list){
            saveVo(vo);
        }
    }
}

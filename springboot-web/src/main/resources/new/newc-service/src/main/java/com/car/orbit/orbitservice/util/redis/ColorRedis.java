package com.car.orbit.orbitservice.util.redis;

import com.car.orbit.orbitservice.entity.OrbitVehicleColor;
import com.car.orbit.orbitutil.tools.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * color信息缓存
 * @author zks
 */
public class ColorRedis extends BaseBusinessRedis {

    /**
     * 获取所有城市信息
     * @return
     */
    public static List<OrbitVehicleColor> getAllCitys(){
        return redisClient.getValues(DIC_COLOR_KEY,OrbitVehicleColor.class);
    }

    /**
     * 根据code获取实体信息
     * @param code
     * @return
     */
    public static OrbitVehicleColor getColorByCode(String code) {
        String value = redisClient.get(DIC_COLOR_KEY).get(code);
        if (!StringUtils.isEmpty(value)) {
            OrbitVehicleColor vo = JsonUtils.toBean(value, OrbitVehicleColor.class);
            return vo;
        }else{
            return null;
        }
    }

    /**
     * 根据code信息,结合当前登录者信息获取对应的国际名称
     * @param code
     * @return
     */
    public static String getNameByCode(String code) {
        String value = redisClient.get(DIC_COLOR_KEY).get(code);
        return getInternationalName(value);
    }

    /**
     * 保存对象
     * @param vo
     */
    public static void saveVo(OrbitVehicleColor vo) {
        redisClient.save(DIC_COLOR_KEY, vo.getCode(), JsonUtils.toJSONString(vo));
    }

    /**
     * 保存列表
     * @param list
     */
    public static void saveVoList(List<OrbitVehicleColor> list) {
        if(list == null ){
            return;
        }
        for(OrbitVehicleColor vo : list){
            saveVo(vo);
        }
    }
}

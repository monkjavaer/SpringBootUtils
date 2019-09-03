package com.car.orbit.orbitservice.util.redis;

import com.car.orbit.orbitservice.entity.OrbitVehicleTypeMapping;
import com.car.orbit.orbitutil.tools.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * VehicleTypeMapping缓存
 */
public class VehicleTypeMappingRedis extends BaseBusinessRedis {

    /**
     * 保存对象
     *
     * @param vo
     */
    public static void saveVo(OrbitVehicleTypeMapping vo) {
        redisClient.save(DIC_VEHICLE_TYPE_MAPPING_KEY, vo.getSourceCode(), JsonUtils.toJSONString(vo));
    }

    /**
     * 保存列表
     *
     * @param list
     */
    public static void saveVoList(List<OrbitVehicleTypeMapping> list) {
        if (list == null) {
            return;
        }
        for (OrbitVehicleTypeMapping vo : list) {
            saveVo(vo);
        }
    }

    /**
     * 映射类型
     *
     * @param srcCode
     * @return
     */
    public static String mapping(String srcCode) {
        String value = redisClient.get(DIC_VEHICLE_TYPE_MAPPING_KEY).get(srcCode);

        if (!StringUtils.isEmpty(value)) {
            OrbitVehicleTypeMapping vo = JsonUtils.toBean(value, OrbitVehicleTypeMapping.class);
            return vo.getCode();
        } else {
            return srcCode;
        }
    }
}

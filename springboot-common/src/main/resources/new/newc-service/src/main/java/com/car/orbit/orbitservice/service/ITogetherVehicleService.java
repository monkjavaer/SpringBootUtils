package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.bo.TogetherVehicleBO;
import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.qo.TogetherVehicleQO;
import com.car.orbit.orbitservice.vo.TogetherVehicleVO;

/**
 * @Title: ITogetherVehicleService
 * @Package: com.car.orbit.orbitservice.service
 * @Description: 同行车分析
 * @Author: monkjavaer
 * @Data: 2019/3/27 16:11
 * @Version: V1.0
 */
public interface ITogetherVehicleService {

    /**
     * 同行车分析
     * @param togetherVehicleQO TogetherVehicleQO
     * @return List<TogetherVehicleVO>
     */
    VehicleSearchBO<TogetherVehicleVO> analysisTogetherVehicle(TogetherVehicleQO togetherVehicleQO);

    /**
     * 同行车相信信息
     * @param togetherVehicleQO TogetherVehicleQO
     * @return VehicleSearchBO<TogetherVehicleInfoVO>
     */
    TogetherVehicleBO togetherVehicleInfo(TogetherVehicleQO togetherVehicleQO);
}

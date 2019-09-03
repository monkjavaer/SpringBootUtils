package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.bo.VehicleTypeMappingBO;
import com.car.orbit.orbitservice.qo.VehicleTypeMappingQO;

import java.util.List;

/**
 * 车辆类型映射service
 */
public interface IVehicleTypeMappingService {

    /**
     * 查询类型映射列表
     *
     * @param mappingQO
     * @return
     */
    List<VehicleTypeMappingBO> queryTypeList(VehicleTypeMappingQO mappingQO);
}

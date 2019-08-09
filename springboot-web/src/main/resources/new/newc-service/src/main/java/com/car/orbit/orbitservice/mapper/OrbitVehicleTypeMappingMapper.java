package com.car.orbit.orbitservice.mapper;

import com.car.orbit.orbitservice.entity.OrbitVehicleTypeMapping;
import com.car.orbit.orbitservice.qo.VehicleTypeMappingQO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrbitVehicleTypeMappingMapper extends Mapper<OrbitVehicleTypeMapping> {

    List<OrbitVehicleTypeMapping> queryDistinctList(VehicleTypeMappingQO qo);
}
package com.car.orbit.orbitservice.mapper;

import com.car.orbit.orbitservice.entity.OrbitColorMapping;
import com.car.orbit.orbitservice.qo.ColorMappingQO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrbitColorMappingMapper extends Mapper<OrbitColorMapping> {

    List<OrbitColorMapping> queryDistinctList(ColorMappingQO colorMappingQO);
}
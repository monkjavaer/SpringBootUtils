package com.car.orbit.orbitservice.mapper;

import com.car.orbit.orbitservice.entity.OrbitControlTaskDetail;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrbitControlTaskDetailMapper extends Mapper<OrbitControlTaskDetail>/*,MySqlMapper<OrbitControlTaskDetail>*/ {
    List<OrbitControlTaskDetail> getInitAllDetails();
}
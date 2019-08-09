package com.car.orbit.orbitservice.mapper.clickhouse;

import com.car.orbit.orbitservice.qo.RequestPrameterQo;
import com.car.orbit.orbitservice.vo.VehicleTraitVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrbitVehicleTraitMapper extends Mapper<VehicleTraitVo> {

    List<VehicleTraitVo> getTraitByImgAll() ;

    List<VehicleTraitVo> getTraitByCondition(RequestPrameterQo requestPrameter) ;

}
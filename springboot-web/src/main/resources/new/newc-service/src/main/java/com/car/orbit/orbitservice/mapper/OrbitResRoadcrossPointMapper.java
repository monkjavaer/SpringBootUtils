package com.car.orbit.orbitservice.mapper;

import com.car.orbit.orbitservice.entity.OrbitResRoadcrossPoint;
import com.car.orbit.orbitservice.vo.DivisionSimpleVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrbitResRoadcrossPointMapper extends Mapper<OrbitResRoadcrossPoint> {

    /**
     * 根据路口查询行政区划信息
     * @param roadId
     * @return
     */
    List<DivisionSimpleVO> queryDivisionSimpleVO(String roadId);
}
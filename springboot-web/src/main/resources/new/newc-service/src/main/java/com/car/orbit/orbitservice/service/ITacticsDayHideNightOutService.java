package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.qo.DayHideNightOutQO;
import com.car.orbit.orbitservice.vo.DayHideNightOutVO;
import com.car.orbit.orbitservice.vo.TacticsVehicleBaseInfo;

import java.util.List;

public interface ITacticsDayHideNightOutService {
    VehicleSearchBO<DayHideNightOutVO> queryPageList(DayHideNightOutQO qo);
}

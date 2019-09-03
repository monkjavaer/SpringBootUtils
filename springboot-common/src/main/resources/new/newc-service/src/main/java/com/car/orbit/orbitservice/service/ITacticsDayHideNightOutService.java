package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.qo.DayHideNightOutQO;
import com.car.orbit.orbitservice.vo.DayHideNightOutVO;

public interface ITacticsDayHideNightOutService {
    VehicleSearchBO<DayHideNightOutVO> queryPageList(DayHideNightOutQO qo);
}

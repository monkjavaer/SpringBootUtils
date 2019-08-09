package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.qo.HiddenVehiclesQO;
import com.car.orbit.orbitservice.vo.HiddenVehiclesVO;

/**
 * CreateDate: 2019-3-27 <br/>
 * Author: monkjavaer <br/>
 * Description: 隐匿车辆查询接口
 * Version: 1.0
 **/
public interface IHiddenVehiclesService {

    /**
     * @description 查询隐匿车辆，返回分页结果
     * @date: 2019-3-27 12:06
     * @author: monkjavaer
     * @param hiddenVehiclesQO
     * @return
     */
    VehicleSearchBO<HiddenVehiclesVO> search(HiddenVehiclesQO hiddenVehiclesQO);
}

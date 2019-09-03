package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.qo.HiddenVehiclesQO;
import com.car.orbit.orbitservice.service.IHiddenVehiclesService;
import com.car.orbit.orbitservice.vo.HiddenVehiclesVO;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * CreateDate: 2019-3-27 <br/>
 * Author: monkjavaer <br/>
 * Description: 隐匿车辆查询controller
 * Version: 1.0
 **/

@RestController
@RequestMapping("/hiddenVehicles")
public class HiddenVehiclesController {

    @Autowired
    IHiddenVehiclesService hiddenVehiclesService;

    /**
     * @description 查询隐匿车辆，分页返回结果
     * @date: 2019-3-27 11:59
     * @author: monkjavaer
     * @param hiddenVehiclesQO，查询条件
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult search(@RequestBody HiddenVehiclesQO hiddenVehiclesQO){
        VehicleSearchBO<HiddenVehiclesVO> data =  hiddenVehiclesService.search(hiddenVehiclesQO);
        return ResultUtil.success(data);
    }
}

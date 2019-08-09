package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.bo.TacticsFrequentPassingInfo;
import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.qo.TacticsFrequentPassingQO;
import com.car.orbit.orbitservice.service.ITacticsFrequentPassingService;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CreateDate：2019/3/27 <br/>
 * Author：monkjavaer <br/>
 * Description: 战法研判-频繁过车controller
 **/
@RestController
@RequestMapping("tactics/frequentPassing")
public class TacticsFrequentPassingController {

    /** 频繁过车service */
    @Autowired
    private ITacticsFrequentPassingService frequentPassingService;

    /**
     * 查询频繁过车信息
     *
     * @param tacticsFrequentPassingQO 查询条件
     * @return 过车信息
     */
    @PostMapping("search")
    public OrbitResult findFrequentPassingInfo(@RequestBody TacticsFrequentPassingQO tacticsFrequentPassingQO) {
        VehicleSearchBO<TacticsFrequentPassingInfo> frequentPassingVehicles = frequentPassingService.findFrequentPassingVehicles(tacticsFrequentPassingQO);
        return ResultUtil.success(frequentPassingVehicles);
    }
}
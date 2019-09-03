package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.bo.TogetherVehicleBO;
import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.qo.TogetherVehicleQO;
import com.car.orbit.orbitservice.service.ITogetherVehicleService;
import com.car.orbit.orbitservice.vo.TogetherVehicleVO;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Title: TogetherVehicleController
 * @Package: com.car.orbit.orbitservice.controller
 * @Description: 同行车分析
 * @Author: monkjavaer
 * @Data: 2019/3/27 16:10
 * @Version: V1.0
 */
@RestController
@RequestMapping("/together")
public class TogetherVehicleController {

    @Autowired
    private ITogetherVehicleService togetherVehicleService;

    /**
     * 同行车分析,分页列表查询
     * @param togetherVehicleQO
     * @return
     */
    @PostMapping("/analysisTogetherVehicle")
    @ResponseBody
    public OrbitResult analysisTogetherVehicle(@RequestBody TogetherVehicleQO togetherVehicleQO) {
        VehicleSearchBO<TogetherVehicleVO> togetherList = togetherVehicleService.analysisTogetherVehicle(togetherVehicleQO);
        return ResultUtil.success(togetherList);
    }

    /**
     * 同行车详情
     * @param togetherVehicleQO
     * @return
     */
    @PostMapping("/togetherVehicleInfo")
    @ResponseBody
    public OrbitResult togetherVehicleInfo(@RequestBody TogetherVehicleQO togetherVehicleQO) {
        TogetherVehicleBO togetherVehicleBO = togetherVehicleService.togetherVehicleInfo(togetherVehicleQO);
        return ResultUtil.success(togetherVehicleBO);
    }

}

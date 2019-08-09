package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.bo.FirstEntryCityBO;
import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.qo.FirstEntryCityQO;
import com.car.orbit.orbitservice.service.IFirstEntryCityService;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Title: FirstEntryCityController
 * @Package: com.car.orbit.orbitservice.controller
 * @Description: 首次入城Controller
 * @Author: monkjavaer
 * @Date: 2019/03/26 16:35
 * @Version: V1.0
 */
@RestController
@RequestMapping("/firstEntryCity")
public class FirstEntryCityController {

    @Autowired
    private IFirstEntryCityService firstEntryCityService;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult queryPageList(@RequestBody FirstEntryCityQO firstEntryCityQO) {
        VehicleSearchBO<FirstEntryCityBO> list = firstEntryCityService.queryPageList(firstEntryCityQO);
        return ResultUtil.success(list);
    }
}

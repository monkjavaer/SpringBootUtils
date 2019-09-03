package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.bo.VehicleOneCarOneGearBO;
import com.car.orbit.orbitservice.bo.onecaronegear.OneCarActiveStat;
import com.car.orbit.orbitservice.qo.DeviceQO;
import com.car.orbit.orbitservice.qo.TacticsOneCarOneGearQO;
import com.car.orbit.orbitservice.service.ITacticsOneCarOneGearService;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Title: TacticsOneCarOneGearConteroller
 * @Package: com.car.orbit.orbitservice.controller
 * @Description: 战法研判==之==一车一档Controller
 * @Author: monkjavaer
 * @Date: 2019/03/29 15:51
 * @Version: V1.0
 */
@RestController
@RequestMapping("/tactics/oneCarOneGear")
public class TacticsOneCarOneGearConteroller {

    @Autowired
    private ITacticsOneCarOneGearService tacticsOneCarOneGearService;


    /**
     * 查询
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult list(@RequestBody TacticsOneCarOneGearQO tacticsOneCarOneGearQO) {
        VehicleOneCarOneGearBO records = null;
        //需要传入车牌号
        if(StringUtils.isEmpty(tacticsOneCarOneGearQO.getPlateNumber())){
            return ResultUtil.error(1001,"获取失败！未传入参数");
        }
        records = tacticsOneCarOneGearService.findVehiclesOneCarOneGearInfos(tacticsOneCarOneGearQO);
        if(null != records){
            return ResultUtil.success(records);
        }else{
            return ResultUtil.error(1001,"获取失败！");
        }
    }

    /**
     * 查询
     */
    @RequestMapping(value = "/areaLatLon", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult list(@RequestBody DeviceQO deviceQO) {
        OneCarActiveStat records = null;
        records = tacticsOneCarOneGearService.findVehiclesOneCarOneGearInfos(deviceQO);
        if(null != records){
            return ResultUtil.success(records);
        }else{
            return ResultUtil.error(1001,"获取失败！");
        }
    }


}

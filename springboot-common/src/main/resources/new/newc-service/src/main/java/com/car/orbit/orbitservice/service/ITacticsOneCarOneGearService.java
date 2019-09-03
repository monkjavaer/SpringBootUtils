package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.bo.VehicleOneCarOneGearBO;
import com.car.orbit.orbitservice.bo.onecaronegear.OneCarActiveStat;
import com.car.orbit.orbitservice.qo.DeviceQO;
import com.car.orbit.orbitservice.qo.TacticsOneCarOneGearQO;

/**
 * CreateDate：2019/3/29 <br/>
 * Author：monkjavaer <br/>
 * Description: 战法研判-一车一档service
 **/

public interface ITacticsOneCarOneGearService {
    /**
     * 查询一车一档信息
     *
     * @param tacticsOneCarOneGearQO 一车一档查询条件
     * @return 一车一档统计数据
     */
    VehicleOneCarOneGearBO findVehiclesOneCarOneGearInfos(TacticsOneCarOneGearQO tacticsOneCarOneGearQO);

    /**
     * 查询一车一档信息
     *
     * @param deviceQO 一车一档区域ID查询条件
     * @return 一车一档该区域ID对应的经纬度信息
     */
    OneCarActiveStat findVehiclesOneCarOneGearInfos(DeviceQO deviceQO);
}

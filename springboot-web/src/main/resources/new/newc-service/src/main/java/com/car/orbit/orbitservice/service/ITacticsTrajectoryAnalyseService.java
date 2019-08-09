package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.bo.TacticsTrajectoryInfoBO;
import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.qo.TacticsTrajectoryAnalyseQO;
import com.car.orbit.orbitservice.vo.DeviceDetailVO;

import java.util.List;

/**
 * CreateDate：2019/3/28 <br/>
 * Author：monkjavaer <br/>
 * Description: 战法研判-轨迹分析service
 **/
public interface ITacticsTrajectoryAnalyseService {

    /**
     * 根据查询条件，获得某辆车的运行轨迹
     *
     * @param trajectoryAnalyseQO 查询条件
     * @return 运行轨迹信息集合
     */
    VehicleSearchBO<TacticsTrajectoryInfoBO> findTrajectoryInfo(TacticsTrajectoryAnalyseQO trajectoryAnalyseQO);

    /**
     * 根据查询条件，获得某个车辆运行轨迹经过的设备的信息
     *
     * @param trajectoryAnalyseQO 查询条件
     * @return 设备信息
     */
    List<DeviceDetailVO> findTrajectoryDeviceInfo(TacticsTrajectoryAnalyseQO trajectoryAnalyseQO);
}

package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.bo.TacticsTrajectoryInfoBO;
import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.qo.TacticsTrajectoryAnalyseQO;
import com.car.orbit.orbitservice.service.ITacticsTrajectoryAnalyseService;
import com.car.orbit.orbitservice.vo.DeviceDetailVO;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CreateDate：2019/3/28 <br/>
 * Author：monkjavaer <br/>
 * Description: 战法研判-轨迹分析controller
 **/
@RestController
@RequestMapping("/tactics/trajectoryAnalysis")
public class TacticsTrajectoryAnalyseController {

    /** 轨迹分析service */
    @Autowired
    private ITacticsTrajectoryAnalyseService trajectoryAnalyseService;

    /**
     * 根据查询条件，获得某辆车的运行轨迹
     *
     * @param trajectoryAnalyseQO 查询条件
     * @return 运行轨迹结果
     */
    @PostMapping("search-list")
    public OrbitResult getTrajectoryInfo(@RequestBody TacticsTrajectoryAnalyseQO trajectoryAnalyseQO) {
        VehicleSearchBO<TacticsTrajectoryInfoBO> trajectoryInfo = trajectoryAnalyseService.findTrajectoryInfo(trajectoryAnalyseQO);
        return ResultUtil.success(trajectoryInfo);
    }

    /**
     * 根据查询条件，获得某个车辆运行轨迹经过的设备的信息
     *
     * @param trajectoryAnalyseQO 查询条件
     * @return 运行轨迹结果
     */
    @PostMapping("search-device")
    public OrbitResult getTrajectoryDeviceInfo(@RequestBody TacticsTrajectoryAnalyseQO trajectoryAnalyseQO) {
        List<DeviceDetailVO> trajectoryDeviceInfo = trajectoryAnalyseService.findTrajectoryDeviceInfo(trajectoryAnalyseQO);
        return ResultUtil.success(trajectoryDeviceInfo);
    }
}
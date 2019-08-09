package com.car.orbit.orbitservice.util;

import com.car.orbit.orbitservice.entity.OrbitPassVehicleRecord;
import com.car.orbit.orbitservice.entity.OrbitResDevice;
import com.car.orbit.orbitservice.util.redis.DevicePointRedis;
import com.car.orbit.orbitservice.vo.VehicleTrajectoryVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: VehicleTrajectoryUtil
 * @Package: com.car.orbit.orbitservice.util
 * @Description: 通过过车记录构造轨迹数据
 * @Author: monkjavaer
 * @Data: 2019/3/28 18:01
 * @Version: V1.0
 */
public class VehicleTrajectoryUtil {


    /**
     * 通过过车记录构造轨迹数据
     * @param passList 过车记录列表
     * @return 轨迹列表
     */
    public static List<VehicleTrajectoryVO> getTrajectory(List<OrbitPassVehicleRecord> passList){
        //轨迹集合
        List<VehicleTrajectoryVO> trajectoryList = new ArrayList<>();
        for (OrbitPassVehicleRecord orbitPassVehicleRecord : passList) {
            VehicleTrajectoryVO trajectoryVO = new VehicleTrajectoryVO();
            trajectoryVO.setId(orbitPassVehicleRecord.getId());
            trajectoryVO.setPlateNumber(orbitPassVehicleRecord.getPlateNumber());
            trajectoryVO.setCaptureTime(orbitPassVehicleRecord.getCaptureTime());
            trajectoryVO.setDeviceId(orbitPassVehicleRecord.getDeviceId());
            trajectoryVO.setDeviceName(orbitPassVehicleRecord.getDeviceName());
            trajectoryVO.setCityName(orbitPassVehicleRecord.getCityName());
            trajectoryVO.setAreaName(orbitPassVehicleRecord.getAreaName());
            trajectoryVO.setRoadName(orbitPassVehicleRecord.getRoadName());
            //从Redis获取设备信息
            if (orbitPassVehicleRecord.getDeviceId() != null) {
                OrbitResDevice device = DevicePointRedis.getDevicePointByCode(orbitPassVehicleRecord.getDeviceId());
                if (device != null) {
                    trajectoryVO.setLatitude(device.getLatitude());
                    trajectoryVO.setLongitude(device.getLongitude());
                    trajectoryVO.setDeviceType(device.getType());
                }
            }
            trajectoryList.add(trajectoryVO);
        }
        return trajectoryList;
    }
}

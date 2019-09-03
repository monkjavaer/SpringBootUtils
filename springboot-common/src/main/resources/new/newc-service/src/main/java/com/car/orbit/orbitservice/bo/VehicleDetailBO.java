package com.car.orbit.orbitservice.bo;

import com.car.orbit.orbitservice.entity.OrbitPassVehicleRecord;
import com.car.orbit.orbitservice.vo.VehicleTrajectoryVO;

import java.util.List;

/**
 * @Title: VehicleDetailBO
 * @Package: com.car.orbit.orbitservice.bo
 * @Description: 车辆详细信息
 * @Author: monkjavaer
 * @Data: 2019/3/21 11:20
 * @Version: V1.0
 */
public class VehicleDetailBO {

    /**
     * 轨迹信息集合
     */
    private List<VehicleTrajectoryVO> trajectoryList;
    /**
     * 分页结果
     */
    private VehicleSearchBO<OrbitPassVehicleRecord> vehicleRecordPage;

    public List<VehicleTrajectoryVO> getTrajectoryList() {
        return trajectoryList;
    }

    public void setTrajectoryList(List<VehicleTrajectoryVO> trajectoryList) {
        this.trajectoryList = trajectoryList;
    }

    public VehicleSearchBO<OrbitPassVehicleRecord> getVehicleRecordPage() {
        return vehicleRecordPage;
    }

    public void setVehicleRecordPage(VehicleSearchBO<OrbitPassVehicleRecord> vehicleRecordPage) {
        this.vehicleRecordPage = vehicleRecordPage;
    }
}

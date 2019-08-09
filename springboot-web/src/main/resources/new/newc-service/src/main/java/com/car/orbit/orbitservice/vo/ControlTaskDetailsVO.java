package com.car.orbit.orbitservice.vo;

import com.car.orbit.orbitservice.entity.OrbitControlTaskDetail;

/**
 * @ProjectName: orbit-parent
 * @Package: com.car.orbit.orbitservice.vo
 * @ClassName: ControlTaskVO
 * @Description: 布控任务详情VO类
 * @Author: zks
 * @CreateDate: 2019/4/2 0002 15:44
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/2 0002 15:44
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ControlTaskDetailsVO extends OrbitControlTaskDetail {
    /**
     * 车辆品牌
     */
    private String vehicleBrandName;
    /**
     * 车辆子品牌名称
     */
    private String vehicleBrandChildName;

    public String getVehicleBrandName() {
        return vehicleBrandName;
    }

    public void setVehicleBrandName(String vehicleBrandName) {
        this.vehicleBrandName = vehicleBrandName;
    }

    public String getVehicleBrandChildName() {
        return vehicleBrandChildName;
    }

    public void setVehicleBrandChildName(String vehicleBrandChildName) {
        this.vehicleBrandChildName = vehicleBrandChildName;
    }
}

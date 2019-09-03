package com.car.orbit.orbitservice.bo;

import com.car.orbit.orbitservice.vo.TacticsVehicleBaseInfo;

/**
 * CreateDate：2019/3/27 <br/>
 * Author：monkjavaer <br/>
 * Description: 战法研判-频繁过车查询结果信息
 **/
public class TacticsFrequentPassingInfo extends TacticsVehicleBaseInfo {

    /** 抓拍次数 */
    private long snapshotCount;

    /** 车辆子品牌id */
    private String vehicleBrandChild;

    /** 车辆品牌id */
    private String vehicleBrand;

    public long getSnapshotCount() {
        return snapshotCount;
    }

    public void setSnapshotCount(long snapshotCount) {
        this.snapshotCount = snapshotCount;
    }

    public String getVehicleBrandChild() {
        return vehicleBrandChild;
    }

    public void setVehicleBrandChild(String vehicleBrandChild) {
        this.vehicleBrandChild = vehicleBrandChild;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    @Override
    public String toString() {
        return "TacticsFrequentPassingInfo{" +
                "snapshotCount=" + snapshotCount +
                ", vehicleBrandChild='" + vehicleBrandChild + '\'' +
                ", vehicleBrand='" + vehicleBrand + '\'' +
                "} " + super.toString();
    }
}
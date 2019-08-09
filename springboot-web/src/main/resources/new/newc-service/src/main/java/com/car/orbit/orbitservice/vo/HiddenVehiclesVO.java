package com.car.orbit.orbitservice.vo;

/**
 * CreateDate: 2019-3-27 <br/>
 * Author: monkjavaer <br/>
 * Description: 隐匿车辆简单对象
 * Version: 1.0
 **/
public class HiddenVehiclesVO extends TacticsVehicleBaseInfo{

    /**
     * 抓拍次数
     */
    private int snapshotCount;

    /**
     * 案发前抓拍次数
     */
    private int snapshotCountBeforeIncident;

    /**
     * 案发后抓拍次数
     */
    private int getSnapshotCountAfterIncident;

    public int getSnapshotCount() {
        return snapshotCount;
    }

    public void setSnapshotCount(int snapshotCount) {
        this.snapshotCount = snapshotCount;
    }

    public int getSnapshotCountBeforeIncident() {
        return snapshotCountBeforeIncident;
    }

    public void setSnapshotCountBeforeIncident(int snapshotCountBeforeIncident) {
        this.snapshotCountBeforeIncident = snapshotCountBeforeIncident;
    }

    public int getGetSnapshotCountAfterIncident() {
        return getSnapshotCountAfterIncident;
    }

    public void setGetSnapshotCountAfterIncident(int getSnapshotCountAfterIncident) {
        this.getSnapshotCountAfterIncident = getSnapshotCountAfterIncident;
    }
}

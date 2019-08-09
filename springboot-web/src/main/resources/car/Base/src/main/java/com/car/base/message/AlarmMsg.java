package com.car.base.message;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by Eric on 2018/6/7.
 */
public class AlarmMsg implements Serializable {

    private String  plateNumber;
    private String  captureTime;
    private int level;
    private int bell;
    private BigInteger passedbyVehicleId;
    private BigInteger cityId;
    private BigInteger areaId;

    public BigInteger getCityId() {
        return cityId;
    }

    public void setCityId(BigInteger cityId) {
        this.cityId = cityId;
    }

    public BigInteger getAreaId() {
        return areaId;
    }

    public void setAreaId(BigInteger areaId) {
        this.areaId = areaId;
    }

    public BigInteger getPassedbyVehicleId() {
        return passedbyVehicleId;
    }

    public void setPassedbyVehicleId(BigInteger passedbyVehicleId) {
        this.passedbyVehicleId = passedbyVehicleId;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(String captureTime) {
        this.captureTime = captureTime;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getBell() {
        return bell;
    }

    public void setBell(int bell) {
        this.bell = bell;
    }
}

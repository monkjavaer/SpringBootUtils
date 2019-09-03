package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.device.bo;

import java.math.BigInteger;

/**
 * Created by monkjavaer on 2017/12/13 0013.
 */
public class CityBO {
    private BigInteger id;
    private String name;
    private String adminRegionCode;
    private BigInteger monitorCenterId;
    private String monitorCenterName;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdminRegionCode() {
        return adminRegionCode;
    }

    public void setAdminRegionCode(String adminRegionCode) {
        this.adminRegionCode = adminRegionCode;
    }

    public BigInteger getMonitorCenterId() {
        return monitorCenterId;
    }

    public void setMonitorCenterId(BigInteger monitorCenterId) {
        this.monitorCenterId = monitorCenterId;
    }

    public String getMonitorCenterName() {
        return monitorCenterName;
    }

    public void setMonitorCenterName(String monitorCenterName) {
        this.monitorCenterName = monitorCenterName;
    }
}

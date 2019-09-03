package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.device.vo;

import java.math.BigInteger;

/**
 * Created by monkjavaer on 2017/12/7 0007.
 */
public class DeviceSimpleVO {
    private BigInteger roadCrossPointId;
    private BigInteger cityId;
    private String name;

    public BigInteger getRoadCrossPointId() {
        return roadCrossPointId;
    }

    public void setRoadCrossPointId(BigInteger roadCrossPointId) {
        this.roadCrossPointId = roadCrossPointId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getCityId() {
        return cityId;
    }

    public void setCityId(BigInteger cityId) {
        this.cityId = cityId;
    }
}

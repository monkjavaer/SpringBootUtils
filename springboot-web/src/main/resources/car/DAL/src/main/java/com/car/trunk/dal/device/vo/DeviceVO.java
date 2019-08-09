package com.car.trunk.dal.device.vo;

import java.math.BigInteger;

/**
 * Created by monkjavaer on 2017/12/7 0007.
 */
public class DeviceVO {
    private BigInteger cityId;
    private BigInteger areaId;
    private BigInteger roadCrossPointId;
    private int pageNo;
    private int pageSize;
    private Integer type;
    private Integer devided; //是否划分，1已划分；0未划分
    private Byte online;

    public Integer getDevided() {
        return devided;
    }

    public void setDevided(Integer devided) {
        this.devided = devided;
    }

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

    public BigInteger getRoadCrossPointId() {
        return roadCrossPointId;
    }

    public void setRoadCrossPointId(BigInteger roadCrossPointId) {
        this.roadCrossPointId = roadCrossPointId;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Byte getOnline() {
        return online;
    }

    public void setOnline(Byte online) {
        this.online = online;
    }
}

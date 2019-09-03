package com.car.orbit.orbitservice.entity;

import java.util.ArrayList;
import java.util.List;

public class OrbitResDeviceExtends extends OrbitResDevice{
    /**
     * 区域id列表
     * */
    private List<String> areaIdList = new ArrayList<>();

    /**
     * 城市id列表
     * */
    private List<String> cityIdList = new ArrayList<>();

    /**
     * 路口id列表
     * */
    private List<String> roadIdList = new ArrayList<>();

    public List<String> getAreaIdList() {
        return areaIdList;
    }

    public void setAreaIdList(List<String> areaIdList) {
        this.areaIdList = areaIdList;
    }

    public List<String> getCityIdList() {
        return cityIdList;
    }

    public void setCityIdList(List<String> cityIdList) {
        this.cityIdList = cityIdList;
    }

    public List<String> getRoadIdList() {
        return roadIdList;
    }

    public void setRoadIdList(List<String> roadIdList) {
        this.roadIdList = roadIdList;
    }
}
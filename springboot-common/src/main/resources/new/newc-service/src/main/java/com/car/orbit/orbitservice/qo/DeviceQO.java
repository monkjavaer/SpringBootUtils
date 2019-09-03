package com.car.orbit.orbitservice.qo;

import com.car.orbit.orbitutil.page.PageParentVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: DeviceQO
 * @Package: com.car.orbit.orbitservice.qo
 * @Description: 设备分页查询
 * @Author: monkjavaer
 * @Data: 2019/3/8 13:07
 * @Version: V1.0
 */
public class DeviceQO extends PageParentVO {
    /**
     * 在线状态
     */
    private Integer online;
    /**
     * 设备类型
     */
    private Integer type;
    /**
     * 城市id
     */
    private String cityId;
    /**
     * 区域id
     */
    private String areaId;
    /**
     * 路口id
     */
    private String roadCrossPointId;
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

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getRoadCrossPointId() {
        return roadCrossPointId;
    }

    public void setRoadCrossPointId(String roadCrossPointId) {
        this.roadCrossPointId = roadCrossPointId;
    }

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

package com.car.orbit.orbitservice.vo;

/**
 * @Title: DivisionSimpleVO
 * @Package: com.car.orbit.orbitservice.vo
 * @Description: DivisionSimpleVO
 * @Author: monkjavaer
 * @Data: 2019/3/22 9:26
 * @Version: V1.0
 */
public class DivisionSimpleVO {
    private String cityId;
    private String cityName;
    private String areaId;
    private String areaName;
    private String roadCrossPointId;
    private String roadName;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getRoadCrossPointId() {
        return roadCrossPointId;
    }

    public void setRoadCrossPointId(String roadCrossPointId) {
        this.roadCrossPointId = roadCrossPointId;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }
}

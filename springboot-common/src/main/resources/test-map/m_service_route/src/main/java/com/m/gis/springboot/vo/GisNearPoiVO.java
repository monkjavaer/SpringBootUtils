package com.m.gis.springboot.vo;

import com.m.gis.springboot.geo.base.common.GisCoordinate;

/**
 * @Title: GisNearPoiVO
 * @Package: com.m.gis.springboot.vo
 * @Description: 返回临近设施点
 * @Author: monkjavaer
 * @Data: 2018/11/8 10:00
 * @Version: V1.0
 */
public class GisNearPoiVO extends GisCoordinate {
    private Integer id;
    private String name;
    private String address;
    private String telephone;
    private String fax;
    private String type;
    private String districtCode;
    private String note;
    private Double distance;

    /**
     * 起始点和临近设施点的最短路径
     */
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}

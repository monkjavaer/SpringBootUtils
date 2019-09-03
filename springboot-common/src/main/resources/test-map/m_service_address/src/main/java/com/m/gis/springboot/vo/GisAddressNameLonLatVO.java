package com.m.gis.springboot.vo;

/**
 * @Title: GisAddressNameVO
 * @Package: com.m.gis.springboot.vo
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/19
 * @Version: V1.0
 */
public class GisAddressNameLonLatVO extends GisAddressNameVO {
    /**
     * 查询点和实际返回地名的距离，单位米
     */
    private double distance;

    /**
     * 查询结果状态
     */
    private Integer status;

    public GisAddressNameLonLatVO() {
    }

    public GisAddressNameLonLatVO(Integer status) {
        super();
        this.status = status;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}


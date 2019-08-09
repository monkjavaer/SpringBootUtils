package com.m.gis.springboot.po;

public class GisRouteRoad extends GisBaseRoad {

    /**
     * 返回的多条路径结果id标识，如求AB两点的3条最短路径，routeId标识1、2、3
     */
    private Integer routeId;

    /**
     * 返回的单条路径结果的段id标识，如某路径由A-B-C两条路段构成，则表示routeSegementId标识1、2
     */
    private Integer routeSegementId;

    /**
     *返回的路径的各起始node节点id，用于判断路径方向
     */
    private Integer startNodeId;

    /**
     * 返回路径的实际成本
     */
    private Double routeCost;

    /**
     * 进入路段时，以正北顺时针计算的方位角
     */
    private Double startAzimuth;

    /**
     * 出路段时，以正北顺时针计算的方位角
     */
    private Double endAzimuth;

    /**
     * 是否为补充的步行路段
     */
    private boolean isFootWay;

    public GisRouteRoad() {
    }

    public GisRouteRoad(GisBaseRoad gisBaseRoad) {
        super(gisBaseRoad);
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public Integer getRouteSegementId() {
        return routeSegementId;
    }

    public void setRouteSegementId(Integer routeSegementId) {
        this.routeSegementId = routeSegementId;
    }

    public Integer getStartNodeId() {
        return startNodeId;
    }

    public void setStartNodeId(Integer startNodeId) {
        this.startNodeId = startNodeId;
    }

    public Double getRouteCost() {
        return routeCost;
    }

    public void setRouteCost(Double routeCost) {
        this.routeCost = routeCost;
    }

    public Double getStartAzimuth() {
        return startAzimuth;
    }

    public void setStartAzimuth(Double startAzimuth) {
        this.startAzimuth = startAzimuth;
    }

    public Double getEndAzimuth() {
        return endAzimuth;
    }

    public void setEndAzimuth(Double endAzimuth) {
        this.endAzimuth = endAzimuth;
    }

    public boolean isFootWay() {
        return isFootWay;
    }

    public void setFootWay(boolean footWay) {
        isFootWay = footWay;
    }
}
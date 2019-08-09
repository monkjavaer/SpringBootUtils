package com.m.gis.springboot.po;

public class GisNearestRoad extends GisBaseRoad {
    /**
     * 对应查询点的查询id，如ABC三个点，查找三个点的最近路段时，每个点对应一个查询id
     */
    private Integer queryId;

    /**
     * 点垂直投影到线段上的位置（0-1）
     */
    private Double fraction;

    /**
     *距离该点最近的路段上的点经度
     */
    private Double closestPointLongitude;

    /**
     * 距离该点最近的路段上的点纬度
     */
    private Double closestPointLatitude;

    public GisNearestRoad() {
    }

    public GisNearestRoad(GisBaseRoad gisBaseRoad) {
        super(gisBaseRoad);
    }


    public Integer getQueryId() {
        return queryId;
    }

    public void setQueryId(Integer queryId) {
        this.queryId = queryId;
    }

    public Double getFraction() {
        return fraction;
    }

    public void setFraction(Double fraction) {
        this.fraction = fraction;
    }

    public Double getClosestPointLongitude() {
        return closestPointLongitude;
    }

    public void setClosestPointLongitude(Double closestPointLongitude) {
        this.closestPointLongitude = closestPointLongitude;
    }

    public Double getClosestPointLatitude() {
        return closestPointLatitude;
    }

    public void setClosestPointLatitude(Double closestPointLatitude) {
        this.closestPointLatitude = closestPointLatitude;
    }
}
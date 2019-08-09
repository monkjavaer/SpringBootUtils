package com.car.orbit.orbitservice.bo;

import java.util.List;

/**
 * @Title: MapConfig
 * @Package: com.car.orbit.orbitservice.bo
 * @Description: 用户地图配置（全局）
 * @Author: monkjavaer
 * @Date: 2019/04/16 16:05
 * @Version: V1.0
 */
public class MapConfig {

    private String routeServerIp;
    private String mapUrl;
    private String layers;
    private String format;
    private Integer zoom;
    private Integer minZoom;
    private List<Double> center;
    private boolean doubleClickZoom;
    private boolean attributionControl;
    private boolean zoomControl;
    private boolean isWMS;

    public String getRouteServerIp() {
        return routeServerIp;
    }

    public void setRouteServerIp(String routeServerIp) {
        this.routeServerIp = routeServerIp;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }

    public String getLayers() {
        return layers;
    }

    public void setLayers(String layers) {
        this.layers = layers;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getZoom() {
        return zoom;
    }

    public void setZoom(Integer zoom) {
        this.zoom = zoom;
    }

    public Integer getMinZoom() {
        return minZoom;
    }

    public void setMinZoom(Integer minZoom) {
        this.minZoom = minZoom;
    }

    public List<Double> getCenter() {
        return center;
    }

    public void setCenter(List<Double> center) {
        this.center = center;
    }

    public boolean isDoubleClickZoom() {
        return doubleClickZoom;
    }

    public void setDoubleClickZoom(boolean doubleClickZoom) {
        this.doubleClickZoom = doubleClickZoom;
    }

    public boolean isAttributionControl() {
        return attributionControl;
    }

    public void setAttributionControl(boolean attributionControl) {
        this.attributionControl = attributionControl;
    }

    public boolean isZoomControl() {
        return zoomControl;
    }

    public void setZoomControl(boolean zoomControl) {
        this.zoomControl = zoomControl;
    }

    public boolean isWMS() {
        return isWMS;
    }

    public void setIsWMS(boolean isWMS) {
        this.isWMS = isWMS;
    }
}

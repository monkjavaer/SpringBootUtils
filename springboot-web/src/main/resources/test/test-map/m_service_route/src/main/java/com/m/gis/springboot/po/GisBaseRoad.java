package com.m.gis.springboot.po;

/**
 * @Title: GisBaseRoad
 * @Package: com.m.gis.springboot.po
 * @Description: 从路表中返回的用于计算的有效字段
 * @Author: monkjavaer
 * @Data: 2018/7/24
 * @Version: V1.0
 */
public class GisBaseRoad {
    private Integer id;

    private String name;

    private Short maxspeed;

    private Boolean bridge;

    private Boolean tunnel;

    private String geom;

    private Integer source;

    private Integer target;

    private Double cost;

    private Double reverseCost;

    private Double length;

    private String dir;

    public GisBaseRoad() {
    }

    public GisBaseRoad(GisBaseRoad other) {
        this.id = other.getId();
        this.name = other.getName();
        this.maxspeed = other.getMaxspeed();
        this.bridge = other.getBridge();
        this.tunnel = other.getTunnel();
        this.geom = other.getGeom();
        this.source = other.getSource();
        this.target = other.getTarget();
        this.cost = other.getCost();
        this.reverseCost = other.getReverseCost();
        this.length = other.getLength();
        this.dir = other.getDir();
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

    public Short getMaxspeed() {
        return maxspeed;
    }

    public void setMaxspeed(Short maxspeed) {
        this.maxspeed = maxspeed;
    }

    public Boolean getBridge() {
        return bridge;
    }

    public void setBridge(Boolean bridge) {
        this.bridge = bridge;
    }

    public Boolean getTunnel() {
        return tunnel;
    }

    public void setTunnel(Boolean tunnel) {
        this.tunnel = tunnel;
    }

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getReverseCost() {
        return reverseCost;
    }

    public void setReverseCost(Double reverseCost) {
        this.reverseCost = reverseCost;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

}

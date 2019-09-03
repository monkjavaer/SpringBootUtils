package com.m.gis.springboot.po;

import javax.persistence.*;

@Table(name = "gis_osm_roads_l")
public class GisRoad {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "osm_id")
    private String osmId;

    private Short code;

    private String fclass;

    private String name;

    private String ref;

    private String oneway;

    private Short maxspeed;

    private Double layer;

    private String bridge;

    private String tunnel;

    @Column(name = "the_geom")
    private Object theGeom;

    private Integer source;

    private Integer target;

    private Double cost;

    @Column(name = "reverse_cost")
    private Double reverseCost;

    private Object restricts;

    private String dir;

    @Column(name = "old_id")
    private Integer oldId;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return osm_id
     */
    public String getOsmId() {
        return osmId;
    }

    /**
     * @param osmId
     */
    public void setOsmId(String osmId) {
        this.osmId = osmId;
    }

    /**
     * @return code
     */
    public Short getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(Short code) {
        this.code = code;
    }

    /**
     * @return fclass
     */
    public String getFclass() {
        return fclass;
    }

    /**
     * @param fclass
     */
    public void setFclass(String fclass) {
        this.fclass = fclass;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return ref
     */
    public String getRef() {
        return ref;
    }

    /**
     * @param ref
     */
    public void setRef(String ref) {
        this.ref = ref;
    }

    /**
     * @return oneway
     */
    public String getOneway() {
        return oneway;
    }

    /**
     * @param oneway
     */
    public void setOneway(String oneway) {
        this.oneway = oneway;
    }

    /**
     * @return maxspeed
     */
    public Short getMaxspeed() {
        return maxspeed;
    }

    /**
     * @param maxspeed
     */
    public void setMaxspeed(Short maxspeed) {
        this.maxspeed = maxspeed;
    }

    /**
     * @return layer
     */
    public Double getLayer() {
        return layer;
    }

    /**
     * @param layer
     */
    public void setLayer(Double layer) {
        this.layer = layer;
    }

    /**
     * @return bridge
     */
    public String getBridge() {
        return bridge;
    }

    /**
     * @param bridge
     */
    public void setBridge(String bridge) {
        this.bridge = bridge;
    }

    /**
     * @return tunnel
     */
    public String getTunnel() {
        return tunnel;
    }

    /**
     * @param tunnel
     */
    public void setTunnel(String tunnel) {
        this.tunnel = tunnel;
    }

    /**
     * @return the_geom
     */
    public Object getTheGeom() {
        return theGeom;
    }

    /**
     * @param theGeom
     */
    public void setTheGeom(Object theGeom) {
        this.theGeom = theGeom;
    }

    /**
     * @return source
     */
    public Integer getSource() {
        return source;
    }

    /**
     * @param source
     */
    public void setSource(Integer source) {
        this.source = source;
    }

    /**
     * @return target
     */
    public Integer getTarget() {
        return target;
    }

    /**
     * @param target
     */
    public void setTarget(Integer target) {
        this.target = target;
    }

    /**
     * @return cost
     */
    public Double getCost() {
        return cost;
    }

    /**
     * @param cost
     */
    public void setCost(Double cost) {
        this.cost = cost;
    }

    /**
     * @return reverse_cost
     */
    public Double getReverseCost() {
        return reverseCost;
    }

    /**
     * @param reverseCost
     */
    public void setReverseCost(Double reverseCost) {
        this.reverseCost = reverseCost;
    }

    /**
     * @return restricts
     */
    public Object getRestricts() {
        return restricts;
    }

    /**
     * @param restricts
     */
    public void setRestricts(Object restricts) {
        this.restricts = restricts;
    }

    /**
     * @return dir
     */
    public String getDir() {
        return dir;
    }

    /**
     * @param dir
     */
    public void setDir(String dir) {
        this.dir = dir;
    }

    /**
     * @return old_id
     */
    public Integer getOldId() {
        return oldId;
    }

    /**
     * @param oldId
     */
    public void setOldId(Integer oldId) {
        this.oldId = oldId;
    }
}
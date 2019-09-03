package com.m.gis.springboot.po;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "gis_poi_t")
public class GisPoiT {
    @Id
    private Integer gid;

    private Object geom;

    private String name;

    @Column(name = "type_code")
    private String typeCode;

    private String remark;

    private String address;

    private String telephone;

    private Double longitude;

    private Double latitude;

    private String info;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private String recorder;

    @Column(name = "district_code")
    private String districtCode;

    @Column(name = "visible_zoom")
    private Integer visibleZoom;

    @Column(name = "data_source")
    private String dataSource;

    /**
     * @return gid
     */
    public Integer getGid() {
        return gid;
    }

    /**
     * @param gid
     */
    public void setGid(Integer gid) {
        this.gid = gid;
    }

    /**
     * @return geom
     */
    public Object getGeom() {
        return geom;
    }

    /**
     * @param geom
     */
    public void setGeom(Object geom) {
        this.geom = geom;
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
     * @return type_code
     */
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * @param typeCode
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * @return longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return recorder
     */
    public String getRecorder() {
        return recorder;
    }

    /**
     * @param recorder
     */
    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    /**
     * @return district_code
     */
    public String getDistrictCode() {
        return districtCode;
    }

    /**
     * @param districtCode
     */
    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    /**
     * @return visible_zoom
     */
    public Integer getVisibleZoom() {
        return visibleZoom;
    }

    /**
     * @param visibleZoom
     */
    public void setVisibleZoom(Integer visibleZoom) {
        this.visibleZoom = visibleZoom;
    }

    /**
     * @return data_source
     */
    public String getDataSource() {
        return dataSource;
    }

    /**
     * @param dataSource
     */
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
}
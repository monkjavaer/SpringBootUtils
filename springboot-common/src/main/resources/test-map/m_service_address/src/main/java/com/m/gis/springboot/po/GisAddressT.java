package com.m.gis.springboot.po;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "gis_address_t")
public class GisAddressT {
    @Id
    private Integer gid;

    private Object geom;

    private String name;

    @Column(name = "district_code")
    private String districtCode;

    private String address;

    private Double longitude;

    private Double latitude;

    private String type;

    @Column(name = "sub_type")
    private String subType;

    @Column(name = "relate_id")
    private Integer relateId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "data_source")
    private String dataSource;

    private String recorder;

    private String remark;

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
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return sub_type
     */
    public String getSubType() {
        return subType;
    }

    /**
     * @param subType
     */
    public void setSubType(String subType) {
        this.subType = subType;
    }

    /**
     * @return relate_id
     */
    public Integer getRelateId() {
        return relateId;
    }

    /**
     * @param relateId
     */
    public void setRelateId(Integer relateId) {
        this.relateId = relateId;
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
}
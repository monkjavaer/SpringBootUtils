package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.model;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by monkjavaer on 2017/11/29 0029.
 */
@Entity
@Table(name = "DEVICE")
public class DeviceEntity {
    private BigInteger id;
    private String name;
    private Integer type;
    private Double latitude;
    private Double longitude;
    private Byte online;
    private String manufacturer;
    private Date createTime;
    private Byte deleted;
    private Integer domainCode;
    private String installAddress;
    private BigInteger terminalId;
    private BigInteger roadCrossPointId;
    private Integer roadwayNum;
    private Integer roadway1No;
    private String roadway1Name;
    private Integer roadway2No;
    private String roadway2Name;
    private Integer roadway3No;
    private String roadway3Name;
    private String deviceId;

    private Date updateTime;
    /**
     * 卡口编码
     */
    private String bayonetNo;



    @Id
    @Column(name = "ID", nullable = false)
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME", nullable = true, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "TYPE", nullable = true)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "LATITUDE", nullable = true, precision = 0)
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "LONGITUDE", nullable = true, precision = 0)
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "ONLINE", nullable = true)
    public Byte getOnline() {
        return online;
    }

    public void setOnline(Byte online) {
        this.online = online;
    }

    @Basic
    @Column(name = "MANUFACTURER", nullable = true, length = 128)
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Basic
    @Column(name = "CREATE_TIME", nullable = true)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "UPDATE_TIME", nullable = true)
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "DELETED", nullable = true)
    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    @Basic
    @Column(name = "DOMAIN_CODE", nullable = true)
    public Integer getDomainCode() {
        return domainCode;
    }

    public void setDomainCode(Integer domainCode) {
        this.domainCode = domainCode;
    }

    @Basic
    @Column(name = "INSTALL_ADDRESS", nullable = true, length = 512)
    public String getInstallAddress() {
        return installAddress;
    }

    public void setInstallAddress(String installAddress) {
        this.installAddress = installAddress;
    }

    @Basic
    @Column(name = "TERMINAL_ID", nullable = true)
    public BigInteger getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(BigInteger terminalId) {
        this.terminalId = terminalId;
    }

    @Basic
    @Column(name = "ROAD_CROSS_POINT_ID", nullable = true)
    public BigInteger getRoadCrossPointId() {
        return roadCrossPointId;
    }

    public void setRoadCrossPointId(BigInteger roadCrossPointId) {
        this.roadCrossPointId = roadCrossPointId;
    }

    @Basic
    @Column(name = "ROADWAY_NUM", nullable = true)
    public Integer getRoadwayNum() {
        return roadwayNum;
    }

    public void setRoadwayNum(Integer roadwayNum) {
        this.roadwayNum = roadwayNum;
    }

    @Basic
    @Column(name = "ROADWAY1_NO", nullable = true)
    public Integer getRoadway1No() {
        return roadway1No;
    }

    public void setRoadway1No(Integer roadway1No) {
        this.roadway1No = roadway1No;
    }

    @Basic
    @Column(name = "ROADWAY1_NAME", nullable = true, length = 64)
    public String getRoadway1Name() {
        return roadway1Name;
    }

    public void setRoadway1Name(String roadway1Name) {
        this.roadway1Name = roadway1Name;
    }

    @Basic
    @Column(name = "ROADWAY2_NO", nullable = true)
    public Integer getRoadway2No() {
        return roadway2No;
    }

    public void setRoadway2No(Integer roadway2No) {
        this.roadway2No = roadway2No;
    }

    @Basic
    @Column(name = "ROADWAY2_NAME", nullable = true, length = 64)
    public String getRoadway2Name() {
        return roadway2Name;
    }

    public void setRoadway2Name(String roadway2Name) {
        this.roadway2Name = roadway2Name;
    }

    @Basic
    @Column(name = "ROADWAY3_NO", nullable = true)
    public Integer getRoadway3No() {
        return roadway3No;
    }

    public void setRoadway3No(Integer roadway3No) {
        this.roadway3No = roadway3No;
    }

    @Basic
    @Column(name = "ROADWAY3_NAME", nullable = true, length = 64)
    public String getRoadway3Name() {
        return roadway3Name;
    }

    public void setRoadway3Name(String roadway3Name) {
        this.roadway3Name = roadway3Name;
    }

    @Basic
    @Column(name = "DEVICE_ID", nullable = true, length = 64)
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    @Basic
    @Column(name = "BAYONET_NO", nullable = true, length = 32)
    public String getBayonetNo() {
        return bayonetNo;
    }

    public void setBayonetNo(String bayonetNo) {
        this.bayonetNo = bayonetNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeviceEntity that = (DeviceEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (type != null ? !type.equals(that.type) : that.type != null) {
            return false;
        }
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) {
            return false;
        }
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) {
            return false;
        }
        if (online != null ? !online.equals(that.online) : that.online != null) {
            return false;
        }
        if (manufacturer != null ? !manufacturer.equals(that.manufacturer) : that.manufacturer != null) {
            return false;
        }
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) {
            return false;
        }
        if (deleted != null ? !deleted.equals(that.deleted) : that.deleted != null) {
            return false;
        }
        if (domainCode != null ? !domainCode.equals(that.domainCode) : that.domainCode != null) {
            return false;
        }
        if (installAddress != null ? !installAddress.equals(that.installAddress) : that.installAddress != null) {
            return false;
        }
        if (terminalId != null ? !terminalId.equals(that.terminalId) : that.terminalId != null) {
            return false;
        }
        if (roadCrossPointId != null ? !roadCrossPointId.equals(that.roadCrossPointId) : that.roadCrossPointId != null) {
            return false;
        }
        if (roadwayNum != null ? !roadwayNum.equals(that.roadwayNum) : that.roadwayNum != null) {
            return false;
        }
        if (roadway1No != null ? !roadway1No.equals(that.roadway1No) : that.roadway1No != null) {
            return false;
        }
        if (roadway1Name != null ? !roadway1Name.equals(that.roadway1Name) : that.roadway1Name != null) {
            return false;
        }
        if (roadway2No != null ? !roadway2No.equals(that.roadway2No) : that.roadway2No != null) {
            return false;
        }
        if (roadway2Name != null ? !roadway2Name.equals(that.roadway2Name) : that.roadway2Name != null) {
            return false;
        }
        if (roadway3No != null ? !roadway3No.equals(that.roadway3No) : that.roadway3No != null) {
            return false;
        }
        if (roadway3Name != null ? !roadway3Name.equals(that.roadway3Name) : that.roadway3Name != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (online != null ? online.hashCode() : 0);
        result = 31 * result + (manufacturer != null ? manufacturer.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        result = 31 * result + (domainCode != null ? domainCode.hashCode() : 0);
        result = 31 * result + (installAddress != null ? installAddress.hashCode() : 0);
        result = 31 * result + (terminalId != null ? terminalId.hashCode() : 0);
        result = 31 * result + (roadCrossPointId != null ? roadCrossPointId.hashCode() : 0);
        result = 31 * result + (roadwayNum != null ? roadwayNum.hashCode() : 0);
        result = 31 * result + (roadway1No != null ? roadway1No.hashCode() : 0);
        result = 31 * result + (roadway1Name != null ? roadway1Name.hashCode() : 0);
        result = 31 * result + (roadway2No != null ? roadway2No.hashCode() : 0);
        result = 31 * result + (roadway2Name != null ? roadway2Name.hashCode() : 0);
        result = 31 * result + (roadway3No != null ? roadway3No.hashCode() : 0);
        result = 31 * result + (roadway3Name != null ? roadway3Name.hashCode() : 0);
        return result;
    }
}

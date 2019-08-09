package com.car.trunk.dal.model;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * Created by monkjavaer on 2017/11/29 0029.
 */
@Entity
@Table(name = "ROAD_CROSS_POINT")
public class RoadCrossPointEntity {
    private BigInteger id;
    private String name;
    private Double latitude;
    private Double longitude;
    private Integer directionCode;
    private Integer roadwayNum;
    private BigInteger areaId;
    private Byte deleted;

    @Basic
    @Column(name = "DELETED", nullable = true)
    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

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
    @Column(name = "DIRECTION_CODE", nullable = true)
    public Integer getDirectionCode() {
        return directionCode;
    }

    public void setDirectionCode(Integer directionCode) {
        this.directionCode = directionCode;
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
    @Column(name = "AREA_ID", nullable = true)
    public BigInteger getAreaId() {
        return areaId;
    }

    public void setAreaId(BigInteger areaId) {
        this.areaId = areaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RoadCrossPointEntity that = (RoadCrossPointEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) {
            return false;
        }
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) {
            return false;
        }
        if (directionCode != null ? !directionCode.equals(that.directionCode) : that.directionCode != null) {
            return false;
        }
        if (roadwayNum != null ? !roadwayNum.equals(that.roadwayNum) : that.roadwayNum != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (directionCode != null ? directionCode.hashCode() : 0);
        result = 31 * result + (roadwayNum != null ? roadwayNum.hashCode() : 0);
        return result;
    }
}

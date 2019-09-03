package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.model;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Date;

/**
 * Created by monkjavaer on 2017/11/29 0029.
 */
@Entity
@Table(name = "HOUR_CROSS_POINT_STATISTICS")
public class HourCrossPointStatisticsEntity {
    private BigInteger id;
    private Date date;
    private Integer hour;
    private Integer numVehiclePassed;
    private Integer numViolation;
    private Integer numDisposal;
    private BigInteger roadCrossPointId;
    private Integer numRunRedLight;
    private Integer numOverSpeed;
    private Integer numWrongDirection;
    private Integer numWrongPosition;
    private Integer numViolateNumLimit;
    private Integer numViolateTimeLimit;

    @Id
    @Column(name = "ID", nullable = false)
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Basic
    @Column(name = "DATE", nullable = true)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "HOUR", nullable = true)
    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    @Basic
    @Column(name = "NUM_VEHICLE_PASSED", nullable = true)
    public Integer getNumVehiclePassed() {
        return numVehiclePassed;
    }

    public void setNumVehiclePassed(Integer numVehiclePassed) {
        this.numVehiclePassed = numVehiclePassed;
    }

    @Basic
    @Column(name = "NUM_VIOLATION", nullable = true)
    public Integer getNumViolation() {
        return numViolation;
    }

    public void setNumViolation(Integer numViolation) {
        this.numViolation = numViolation;
    }

    @Basic
    @Column(name = "NUM_DISPOSAL", nullable = true)
    public Integer getNumDisposal() {
        return numDisposal;
    }

    public void setNumDisposal(Integer numDisposal) {
        this.numDisposal = numDisposal;
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
    @Column(name = "NUM_RUN_RED_LIGHT", nullable = true)
    public Integer getNumRunRedLight() {
        return numRunRedLight;
    }

    public void setNumRunRedLight(Integer numRunRedLight) {
        this.numRunRedLight = numRunRedLight;
    }

    @Basic
    @Column(name = "NUM_OVER_SPEED", nullable = true)
    public Integer getNumOverSpeed() {
        return numOverSpeed;
    }

    public void setNumOverSpeed(Integer numOverSpeed) {
        this.numOverSpeed = numOverSpeed;
    }

    @Basic
    @Column(name = "NUM_WRONG_DIRECTION", nullable = true)
    public Integer getNumWrongDirection() {
        return numWrongDirection;
    }

    public void setNumWrongDirection(Integer numWrongDirection) {
        this.numWrongDirection = numWrongDirection;
    }

    @Basic
    @Column(name = "NUM_WRONG_POSITION", nullable = true)
    public Integer getNumWrongPosition() {
        return numWrongPosition;
    }

    public void setNumWrongPosition(Integer numWrongPosition) {
        this.numWrongPosition = numWrongPosition;
    }

    @Basic
    @Column(name = "NUM_VIOLATE_NUM_LIMIT", nullable = true)
    public Integer getNumViolateNumLimit() {
        return numViolateNumLimit;
    }

    public void setNumViolateNumLimit(Integer numViolateNumLimit) {
        this.numViolateNumLimit = numViolateNumLimit;
    }

    @Basic
    @Column(name = "NUM_VIOLATE_TIME_LIMIT", nullable = true)
    public Integer getNumViolateTimeLimit() {
        return numViolateTimeLimit;
    }

    public void setNumViolateTimeLimit(Integer numViolateTimeLimit) {
        this.numViolateTimeLimit = numViolateTimeLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HourCrossPointStatisticsEntity that = (HourCrossPointStatisticsEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (date != null ? !date.equals(that.date) : that.date != null) {
            return false;
        }
        if (hour != null ? !hour.equals(that.hour) : that.hour != null) {
            return false;
        }
        if (numVehiclePassed != null ? !numVehiclePassed.equals(that.numVehiclePassed) : that.numVehiclePassed != null) {
            return false;
        }
        if (numViolation != null ? !numViolation.equals(that.numViolation) : that.numViolation != null) {
            return false;
        }
        if (numDisposal != null ? !numDisposal.equals(that.numDisposal) : that.numDisposal != null) {
            return false;
        }
        if (roadCrossPointId != null ? !roadCrossPointId.equals(that.roadCrossPointId) : that.roadCrossPointId != null) {
            return false;
        }
        if (numRunRedLight != null ? !numRunRedLight.equals(that.numRunRedLight) : that.numRunRedLight != null) {
            return false;
        }
        if (numOverSpeed != null ? !numOverSpeed.equals(that.numOverSpeed) : that.numOverSpeed != null) {
            return false;
        }
        if (numWrongDirection != null ? !numWrongDirection.equals(that.numWrongDirection) : that.numWrongDirection != null) {
            return false;
        }
        if (numWrongPosition != null ? !numWrongPosition.equals(that.numWrongPosition) : that.numWrongPosition != null) {
            return false;
        }
        if (numViolateNumLimit != null ? !numViolateNumLimit.equals(that.numViolateNumLimit) : that.numViolateNumLimit != null) {
            return false;
        }
        if (numViolateTimeLimit != null ? !numViolateTimeLimit.equals(that.numViolateTimeLimit) : that.numViolateTimeLimit != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (hour != null ? hour.hashCode() : 0);
        result = 31 * result + (numVehiclePassed != null ? numVehiclePassed.hashCode() : 0);
        result = 31 * result + (numViolation != null ? numViolation.hashCode() : 0);
        result = 31 * result + (numDisposal != null ? numDisposal.hashCode() : 0);
        result = 31 * result + (roadCrossPointId != null ? roadCrossPointId.hashCode() : 0);
        result = 31 * result + (numRunRedLight != null ? numRunRedLight.hashCode() : 0);
        result = 31 * result + (numOverSpeed != null ? numOverSpeed.hashCode() : 0);
        result = 31 * result + (numWrongDirection != null ? numWrongDirection.hashCode() : 0);
        result = 31 * result + (numWrongPosition != null ? numWrongPosition.hashCode() : 0);
        result = 31 * result + (numViolateNumLimit != null ? numViolateNumLimit.hashCode() : 0);
        result = 31 * result + (numViolateTimeLimit != null ? numViolateTimeLimit.hashCode() : 0);
        return result;
    }
}

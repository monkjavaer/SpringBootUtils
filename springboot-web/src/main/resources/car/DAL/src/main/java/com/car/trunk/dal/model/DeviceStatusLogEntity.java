package com.car.trunk.dal.model;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by monkjavaer on 2017/11/29 0029.
 */
@Entity
@Table(name = "DEVICE_STATUS_LOG")
public class DeviceStatusLogEntity {
    private BigInteger id;
    private Date createTime;
    private Date lastupdateTime;
    private Integer type;
    private BigInteger deviceId;
    private BigInteger terminalId;
    private Byte online;
    private Integer totalTime;

    @Basic
    @Column(name = "TOTAL_TIME", nullable = true)
    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
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
    @Column(name = "LASTUPDATE_TIME", nullable = true)
    public Date getLastupdateTime() {
        return lastupdateTime;
    }

    public void setLastupdateTime(Date lastupdateTime) {
        this.lastupdateTime = lastupdateTime;
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
    @Column(name = "TYPE", nullable = true)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "DEVICE_ID", nullable = true)
    public BigInteger getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(BigInteger deviceId) {
        this.deviceId = deviceId;
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
    @Column(name = "ONLINE", nullable = true)
    public Byte getOnline() {
        return online;
    }

    public void setOnline(Byte online) {
        this.online = online;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeviceStatusLogEntity that = (DeviceStatusLogEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) {
            return false;
        }
        if (type != null ? !type.equals(that.type) : that.type != null) {
            return false;
        }
        if (deviceId != null ? !deviceId.equals(that.deviceId) : that.deviceId != null) {
            return false;
        }
        if (terminalId != null ? !terminalId.equals(that.terminalId) : that.terminalId != null) {
            return false;
        }
        if (online != null ? !online.equals(that.online) : that.online != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
        result = 31 * result + (terminalId != null ? terminalId.hashCode() : 0);
        result = 31 * result + (online != null ? online.hashCode() : 0);
        return result;
    }
}

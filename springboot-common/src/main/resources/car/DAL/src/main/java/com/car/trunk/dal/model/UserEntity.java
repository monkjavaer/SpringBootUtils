package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.model;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by monkjavaer on 2017/11/29 0029.
 */
@Entity
@Table(name = "USER")
public class UserEntity {
    private BigInteger id;
    private String username;
    private String password;
    private String name;
    private Integer gender;
    private String phone;
    private String email;
    private BigInteger monitorCenterId;
    private BigInteger roleId;
    private byte deleted;
    private Date updateTime;


    @Basic
    @Column(name = "UPDATE_TIME", nullable = true)
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
    @Column(name = "USERNAME", nullable = true, length = 16)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "PASSWORD", nullable = true, length = 16)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    @Column(name = "GENDER", nullable = true)
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "PHONE", nullable = true, length = 16)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "EMAIL", nullable = true, length = 64)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "MONITOR_CENTER_ID", nullable = true)
    public BigInteger getMonitorCenterId() {
        return monitorCenterId;
    }

    public void setMonitorCenterId(BigInteger monitorCenterId) {
        this.monitorCenterId = monitorCenterId;
    }

    @Basic
    @Column(name = "ROLE_ID", nullable = true)
    public BigInteger getRoleId() {
        return roleId;
    }

    public void setRoleId(BigInteger roleId) {
        this.roleId = roleId;
    }
    
    @Basic
    @Column(name = "DELETED", nullable = true)
    public byte getDeleted() {
        return deleted;
    }

    public void setDeleted(byte deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserEntity that = (UserEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (username != null ? !username.equals(that.username) : that.username != null) {
            return false;
        }
        if (password != null ? !password.equals(that.password) : that.password != null) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) {
            return false;
        }
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) {
            return false;
        }
        if (email != null ? !email.equals(that.email) : that.email != null) {
            return false;
        }
        if (monitorCenterId != null ? !monitorCenterId.equals(that.monitorCenterId) : that.monitorCenterId != null) {
            return false;
        }
        if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (monitorCenterId != null ? monitorCenterId.hashCode() : 0);
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        return result;
    }
}

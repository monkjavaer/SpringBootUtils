package com.car.trunk.dal.model;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * Created by monkjavaer on 2017/11/29 0029.
 */
@Entity
@Table(name = "AREA")
public class AreaEntity {
    private BigInteger id;
    private String name;
    private String adminRegionCode;
    private BigInteger cityId;
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
    @Column(name = "NAME", nullable = true, length = 256)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "ADMIN_REGION_CODE", nullable = true, length = 32)
    public String getAdminRegionCode() {
        return adminRegionCode;
    }

    public void setAdminRegionCode(String adminRegionCode) {
        this.adminRegionCode = adminRegionCode;
    }

    @Basic
    @Column(name = "CITY_ID", nullable = true)
    public BigInteger getCityId() {
        return cityId;
    }

    public void setCityId(BigInteger cityId) {
        this.cityId = cityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}

        AreaEntity that = (AreaEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {return false;}
        if (name != null ? !name.equals(that.name) : that.name != null) {return false;}
        if (adminRegionCode != null ? !adminRegionCode.equals(that.adminRegionCode) : that.adminRegionCode != null)
        {return false;}
        if (cityId != null ? !cityId.equals(that.cityId) : that.cityId != null) {return false;}

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (adminRegionCode != null ? adminRegionCode.hashCode() : 0);
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        return result;
    }
}

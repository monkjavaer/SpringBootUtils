package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.model;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * Created by monkjavaer on 2017/11/29 0029.
 */
@Entity
@Table(name = "ROLE_AUTHORITY")
@IdClass(RoleAuthorityEntityPK.class)
public class RoleAuthorityEntity {
    private BigInteger roleId;
    private BigInteger authorityId;

    @Id
    @Column(name = "ROLE_ID", nullable = false)
    public BigInteger getRoleId() {
        return roleId;
    }

    public void setRoleId(BigInteger roleId) {
        this.roleId = roleId;
    }

    @Id
    @Column(name = "AUTHORITY_ID", nullable = false)
    public BigInteger getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(BigInteger authorityId) {
        this.authorityId = authorityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RoleAuthorityEntity that = (RoleAuthorityEntity) o;

        if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) {
            return false;
        }
        if (authorityId != null ? !authorityId.equals(that.authorityId) : that.authorityId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleId != null ? roleId.hashCode() : 0;
        result = 31 * result + (authorityId != null ? authorityId.hashCode() : 0);
        return result;
    }
}

package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by monkjavaer on 2017/11/29 0029.
 */
public class RoleAuthorityEntityPK implements Serializable {
    private BigInteger roleId;
    private BigInteger authorityId;

    @Column(name = "ROLE_ID", nullable = false)
    @Id
    public BigInteger getRoleId() {
        return roleId;
    }

    public void setRoleId(BigInteger roleId) {
        this.roleId = roleId;
    }

    @Column(name = "AUTHORITY_ID", nullable = false)
    @Id
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

        RoleAuthorityEntityPK that = (RoleAuthorityEntityPK) o;

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

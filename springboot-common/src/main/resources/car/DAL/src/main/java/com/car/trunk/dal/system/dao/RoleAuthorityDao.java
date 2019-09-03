package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.system.dao;

import com.base.springboot.car.Base.src.main.java.com.car.base.dao.interfaces.IBaseDao;
import com.car.trunk.dal.model.RoleAuthorityEntity;

import java.math.BigInteger;
import java.util.List;

/**
 * Description: 角色权限表DAO接口
 * Original Author: monkjavaer, 2017/12/11
 */
public interface RoleAuthorityDao extends IBaseDao<RoleAuthorityEntity> {
    List<String> getAuthoritiesByRoleId(BigInteger roleId);

    void deleteAuthoritiesByRoleId(BigInteger roleId);
    void insertRoleAuthority(BigInteger roleId, List<String> authorityList);
}

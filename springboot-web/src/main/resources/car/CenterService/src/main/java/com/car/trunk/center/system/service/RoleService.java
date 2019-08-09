package com.car.trunk.center.system.service;

import com.car.base.model.models.ResultTuple;
import com.car.trunk.dal.model.AuthorityEntity;
import com.car.trunk.dal.model.RoleEntity;
import com.car.trunk.dal.system.bo.RoleListBO;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>Description:角色Service </p>
 *
 * @author monkjavaer
 * @version 1.0 创建时间:2017-11-29 16:12
 */
public interface RoleService {

    /**
     * 新增角色
     * @param roleEntity
     */
    void addRole(RoleEntity roleEntity);

    /**
     * 修改角色
     * @param roleEntity
     */
    void modifyRole(RoleEntity roleEntity);

    /**
     * 删除角色
     * @param roleEntity
     */
    void deleteRole(RoleEntity roleEntity);

    /**
     * 获取角色及权限列表
     * @return
     */
    ResultTuple<RoleListBO> queryRoleList();

    /**
     * 获取权限信息列表
     * @return
     */
    List<AuthorityEntity> queryAuthorityList();

    /**
     * 修改角色
     * @param roleId
     * @param authorityName
     */
    void updateRoleAuthority(BigInteger roleId, List<String> authorityName);
}

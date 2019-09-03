package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.entity.OrbitSysRole;
import com.car.orbit.orbitservice.exception.AuthFailException;
import com.car.orbit.orbitservice.exception.DuplicateDataException;
import com.car.orbit.orbitservice.exception.RelationshipException;
import com.car.orbit.orbitservice.qo.RoleQO;
import com.car.orbit.orbitservice.vo.RoleOperatorVO;
import com.car.orbit.orbitservice.vo.RoleVO;
import com.car.orbit.orbitutil.page.PageUtil;

import java.util.List;

/**
 * @Title: IRoleService
 * @Package: com.car.orbit.orbitservice.service.IRoleService
 * @Description: 角色服务接口
 * @Author: monkjavaer
 * @Date: 2019/03/12 14:23
 * @Version: V1.0
 */
public interface IRoleService {

    /**
     * 查询角色列表（前端下拉框使用）
     *
     * @return
     */
    List<OrbitSysRole> queryRoleList();

    /**
     * 查询角色列表（分页）
     *
     * @return
     */
    PageUtil<RoleVO> queryPageList(RoleQO roleQO);

    /**
     * 删除角色
     *
     * @param roleDeleteVO
     * @return
     */
    void deleteRole(RoleOperatorVO roleDeleteVO) throws AuthFailException, RelationshipException;

    /**
     * 更新角色
     *
     * @param roleOperatorVO
     */
    void updateRole(RoleOperatorVO roleOperatorVO) throws DuplicateDataException;

    /**
     * 添加角色
     *
     * @param roleOperatorVO
     */
    void addRole(RoleOperatorVO roleOperatorVO) throws DuplicateDataException;

}

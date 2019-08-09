package com.car.orbit.orbitservice.service;

import com.alibaba.fastjson.JSONObject;
import com.car.orbit.orbitservice.entity.OrbitSysUser;
import com.car.orbit.orbitservice.qo.UserQO;
import com.car.orbit.orbitservice.vo.PasswordResetVO;
import com.car.orbit.orbitservice.vo.UserSimpleVO;
import com.car.orbit.orbitutil.page.PageUtil;

import java.util.List;

/**
 * @Title: IUserManagementService
 * @Package: com.car.orbit.orbitservice.service
 * @Description:
 * @Author: monkjavaer
 * @Date: 2019/03/08 16:03
 * @Version: V1.0
 */
public interface IUserManagementService {

    /**
     * 查询所有用户列表（不分页）
     *
     * @return
     */
    List<UserSimpleVO> queryUserList();

    /**
     * 分页查询用户列表
     *
     * @param userQO
     * @return
     */
    PageUtil<UserSimpleVO> queryPageList(UserQO userQO);

    /**
     * 添加用户
     *
     * @param orbitSysUser
     */
    void addUser(OrbitSysUser orbitSysUser);

    /**
     * 更新用户
     *
     * @param orbitSysUser
     */
    void updateUser(OrbitSysUser orbitSysUser);

    /**
     * 删除用户
     *
     * @param orbitSysUser 需要删除的用户
     */
    void deleteUser(OrbitSysUser orbitSysUser);

    /**
     * 重置用户密码
     *
     * @param operatorId
     * @param passwordResetVO
     * @return
     */
    boolean resetPassword(String operatorId, PasswordResetVO passwordResetVO);

    /**
     * 检查用户名或者姓名是否存在重复
     *
     * @param orbitSysUser 检查的用户
     * @return true-重复，false-不重复
     */
    boolean existDuplicate(OrbitSysUser orbitSysUser);

    /**
     * 配置所有权限均在首页显示
     *
     * @param orbitSysUser 需要配置的用户
     */
    void saveConfig(OrbitSysUser orbitSysUser);
}

package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.bo.UserBO;
import com.car.orbit.orbitservice.entity.OrbitResDeviceExtends;
import com.car.orbit.orbitservice.entity.OrbitSysUser;
import com.car.orbit.orbitservice.exception.IllegalParamException;
import com.car.orbit.orbitservice.vo.UserInfoVO;
import org.elasticsearch.index.query.QueryBuilder;

import javax.security.auth.login.LoginException;
import java.util.List;

/**
 * @Title: IUserService
 * @Package: com.car.orbit.orbitservice.service
 * @Description:
 * @Author: monkjavaer
 * @Date: 2019/03/08 16:05
 * @Version: V1.0
 */
public interface IUserService {

    /**
     * 用户登录
     *
     * @param orbitSysUser 需要登录的用户
     * @return 登录成功的token
     * @throws LoginException 用户名或者密码错误
     */
    String login(OrbitSysUser orbitSysUser) throws LoginException;

    /**
     * 用户退出登录
     *
     * @param token 需要退出登录的用户的token
     */
    void logout(String token) throws IllegalParamException;

    /**
     * 通过用户名和密码查询用户
     *
     * @param name
     * @param password
     * @return
     */
    List<OrbitSysUser> queryUserList(String name, String password);

    /**
     * 查询用户的基础信息
     *
     * @param userId
     * @return
     */
    UserInfoVO queryUserSimpleInfo(String userId);

    /**
     * 查询用户详细信息
     *
     * @param userId
     * @return
     */
    UserBO queryUserInfo(String userId);

    /**
     * 编辑用户基础信息
     *
     * @param orbitSysUser
     */
    void editUserInfo(OrbitSysUser orbitSysUser);

    /**
     * 重置密码
     *
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    boolean resetPassword(String userId, String oldPassword, String newPassword);

    /**
     * @return
     * @description 获取当前用户所管辖的行政区域范围
     * @date: 17:11
     * @author: monkjavaer
     */
    OrbitResDeviceExtends getUserAuthority();

    QueryBuilder getUserAuthorityQeuryBuilder();

    /**
     * 判断姓名是否重复
     *
     * @param orbitSysUser 需要判断的用户
     * @return true-重复，false-不重复
     */
    boolean existDuplicate(OrbitSysUser orbitSysUser);
}

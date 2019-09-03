package com.car.orbit.orbitservice.util.redis;

import com.car.orbit.orbitservice.entity.OrbitSysUser;
import com.car.orbit.orbitservice.util.LocalHolder;
import com.car.orbit.orbitutil.tools.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统用户信息业务redis类
 * LOGIN_USER主要保存登录了的用户信息，没有登录的用户信息没有保存
 * USER_INFO 主要保存所有用户信息
 */
public class OrbitSysUserRedis extends BaseBusinessRedis {
    /**
     * 获取当前登录用户信息
     * @return
     */
    public static OrbitSysUser getCurrentLoginUser(){
        return getLoginUser(LocalHolder.getCurrentToken());
    }

    /**
     * 根据token获取对应用户信息
     * @param token
     * @return
     */
    // public static OrbitSysUser getLoginUser(String token) {
    //     String value = redisClient.get(LOGIN_USER).get(token);
    //     if (!StringUtils.isEmpty(value)) {
    //         OrbitSysUser userInRedis = JsonUtils.toBean(value, OrbitSysUser.class);
    //         return userInRedis;
    //     }else{
    //         return null;
    //     }
    // }
    public static OrbitSysUser getLoginUser(String token) {
        return redisClient.getValue(token, OrbitSysUser.class);
    }

    /**
     * ===============下面是基本信息的保存，包含所有登录未登录用户信息====================
     */
    /**
     * 根据Id获取对应用户信息
     * @param userId
     * @return
     */
    public static OrbitSysUser getUserById(String userId) {
        String value = redisClient.get(USER_INFO).get(userId);
        if (!StringUtils.isEmpty(value)) {
            OrbitSysUser userInRedis = JsonUtils.toBean(value, OrbitSysUser.class);
            return userInRedis;
        }else{
            return null;
        }
    }
    /**
     * 根据逗号切割的Ids获取对应用户信息
     * @return
     */
    public static List<OrbitSysUser> getUsersByIds(String userIds) {
        List<OrbitSysUser> result = new ArrayList<>();
        if(StringUtils.isNotEmpty(userIds)){
            String[] useridArray = userIds.split(",");
            for(String userId : useridArray){
                OrbitSysUser user = getUserById(userId);
                result.add(user);
            }
        }
        return result;
    }
    /**
     * 保存
     */
    public static void saveUserInfo(OrbitSysUser user) {
        user.setPassword(null);
        redisClient.save(USER_INFO, user.getId(), JsonUtils.toJSONString(user));
    }
    /**
     * 保存List
     */
    public static void saveUserInfos(List<OrbitSysUser> users) {
        for (OrbitSysUser user : users){
            saveUserInfo(user);
        }
    }
}

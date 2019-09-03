package com.car.orbit.orbitservice.mapper;

import com.car.orbit.orbitservice.entity.OrbitSysUser;
import com.car.orbit.orbitservice.qo.UserQO;
import com.car.orbit.orbitservice.vo.UserInfoVO;
import com.car.orbit.orbitservice.vo.UserSimpleVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Title: OrbitSysUserMapper
 * @Package: com.car.orbit.orbitservice.mapper
 * @Description:
 * @Author: monkjavaer
 * @Date: 2019/03/08 15:53
 * @Version: V1.0
 */
public interface OrbitSysUserMapper extends Mapper<OrbitSysUser> {

    /**
     * 用户分页查询
     *
     * @param userQO
     * @return
     */
    List<UserSimpleVO> queryUserList(UserQO userQO);

    /**
     * 查询用户的基础信息
     *
     * @param userId
     * @return
     */
    UserInfoVO queryUserInfo(String userId);

    /**
     * 查询与某用户相同机构下的所有用户的id，包括自己
     *
     * @param userId 用户id
     * @return 同机构下的用户id
     */
    List<String> queryUserIdInSameMonitor(String userId);
}

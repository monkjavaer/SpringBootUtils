package com.car.trunk.center.system.service;

import com.car.base.common.exception.ValidateException;
import com.car.trunk.center.vo.userCityArea.request.UserCityAreaUpdateVO;
import com.car.trunk.center.vo.userCityArea.result.UserCityAreaResultVO;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

/**
 * CreateDate：2018/7/11
 * Author：monkjavaer
 * Description: 该类主要用于提供用户城市区域绑定关系服务接口
 */
public interface IUserCityAreaService {
    /**
     * 获取用户对应的城市区域关联信息
     *
     * @param userId         被操作的用户ID
     * @param userName       被操作者用户名
     * @return UserCityAreaResultVO 获取到的关联信息
     */
    public UserCityAreaResultVO queryInfoByUserId(BigInteger userId,String userName) throws ValidateException;


    /**
     * 获取用户对应的城市信息
     *
     * @param userId         被操作的用户ID
     * @return Set<BigInteger> 获取到的关联信息
     */
    public Set<BigInteger> queryCheckedCityByUserId(BigInteger userId);

    /**
     * 获取用户选择城市对应的区域信息
     *
     * @param userId         被操作的用户ID
     * @return Set<BigInteger> 获取到的关联信息
     */
    public Set<BigInteger> queryCheckedAreaByUserIdCityId(BigInteger userId,BigInteger cityId);

    /**
     * 修改用户对应的城市区域关联信息
     *
     * @param userCityAreaUpdateVO 待修改的信息
     * @return boolean 修改结果
     */
    public boolean updateBatch(UserCityAreaUpdateVO userCityAreaUpdateVO);

    /**
     * 删除用户对应的城市区域关联信息
     *
     * @param userId 用户ID
     * @return boolean 修改结果
     */
    public void deleteByUserId(BigInteger userId);

    /**
     * 通过城市区域信息查询有权限用户列表
     * @param cityId 城市主键
     * @param areaId 区域主键
     * @return
     */
    List<String> getUserNamesByCityIdAndAreaId(BigInteger cityId,BigInteger areaId);

}




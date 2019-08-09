package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.entity.OrbitControlBlacklist;
import com.car.orbit.orbitservice.entity.OrbitControlBlacklistType;
import com.car.orbit.orbitservice.exception.DuplicateDataException;
import com.car.orbit.orbitservice.exception.ExistInOtherModuleException;
import com.car.orbit.orbitservice.exception.IllegalParamException;
import com.car.orbit.orbitservice.exception.RelationshipException;
import com.car.orbit.orbitservice.qo.BlackListQO;
import com.car.orbit.orbitutil.page.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * CreateDate：2019/3/28 <br/>
 * Author：monkjavaer <br/>
 * Description: 黑名单管理service
 **/
public interface IBlackListService {

    /**
     * 判断车牌号是否在黑名单里面
     *
     * @param plateNumber 车牌号
     * @param plateColor  车牌颜色id
     * @return true-在 false-不在
     */
    boolean isInBlackList(String plateNumber, String plateColor);

    /**
     * 判断车牌号是否在黑名单里面
     *
     * @param vid 车辆id
     * @return true-在 false-不在
     */
     boolean isInBlackList(String vid);

    /**
     * 查询黑名单
     *
     * @param blackListQO
     * @return
     */
    PageUtil<OrbitControlBlacklist> queryPageList(BlackListQO blackListQO);

    /**
     * 添加黑名单
     *
     * @param blackList
     * @return
     */
    void addBlackList(OrbitControlBlacklist blackList) throws IllegalParamException, ExistInOtherModuleException, DuplicateDataException;

    /**
     * 更新黑名单
     *
     * @param blackList
     * @return
     */
    void updateBlackList(OrbitControlBlacklist blackList);

    /**
     * 删除黑名单
     *
     * @param blacklist 需要删除的黑名单
     */
    void deleteBlackList(OrbitControlBlacklist blacklist);

    /**
     * 导入黑名单
     *
     * @param sourceData
     * @return
     */
    Map<String, Integer> importBlackList(List sourceData);

    /**
     * 获取黑名单类型列表
     *
     * @return
     */
    List<OrbitControlBlacklistType> getAllBlacklistType();

    /**
     * 添加黑名单类型
     *
     * @param blacklistType
     * @return
     */
    void addBlacklistType(OrbitControlBlacklistType blacklistType) throws DuplicateDataException;

    /**
     * 编辑黑名单类型
     *
     * @param blacklistType
     * @return
     */
    void editBlacklistType(OrbitControlBlacklistType blacklistType) throws DuplicateDataException;

    /**
     * 删除黑名单类型
     *
     * @param blacklistType 需要删除的黑名单类型
     * @return 操作结果
     */
    void deleteBlacklistType(OrbitControlBlacklistType blacklistType) throws RelationshipException;

    /**
     * 检查是否有重复的记录
     *
     * @param vid 车辆id
     * @param plateNumber 车牌号
     * @param plateColor 车牌颜色
     * @return true-存在，false-不存在
     */
    boolean checkDuplicate(String vid, String plateNumber, String plateColor);
}

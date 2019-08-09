package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.entity.OrbitControlWhitelist;
import com.car.orbit.orbitservice.entity.OrbitControlWhitelistType;
import com.car.orbit.orbitservice.exception.DuplicateDataException;
import com.car.orbit.orbitservice.exception.ExistInOtherModuleException;
import com.car.orbit.orbitservice.exception.IllegalParamException;
import com.car.orbit.orbitservice.exception.RelationshipException;
import com.car.orbit.orbitservice.qo.WhiteListQO;
import com.car.orbit.orbitutil.page.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * @Title: IWhiteListService
 * @Package: com.car.orbit.orbitservice.service
 * @Description: 白名单管理服务接口
 * @Author: monkjavaer
 * @Date: 2019/03/30 13:59
 * @Version: V1.0
 */
public interface IWhiteListService {

    /**
     * 判断是否存在于白名单中
     *
     * @param plateNumber
     * @param plateColor
     * @return
     */
    boolean isInWhiteList(String plateNumber, String plateColor);

    /**
     * 查询白名单
     *
     * @param whiteListQO
     * @return
     */
    PageUtil<OrbitControlWhitelist> queryPageList(WhiteListQO whiteListQO);

    /**
     * 添加白名单
     *
     * @param whiteList
     * @return
     */
    void addWhiteList(OrbitControlWhitelist whiteList) throws ExistInOtherModuleException, IllegalParamException, DuplicateDataException;

    /**
     * 更新白名单
     *
     * @param whiteList
     * @return
     */
    void updateWhiteList(OrbitControlWhitelist whiteList);

    /**
     * 删除白名单
     *
     * @param whitelist
     */
    void deleteWhiteList(OrbitControlWhitelist whitelist);

    /**
     * 导入白名单
     *
     * @param sourceData
     * @return
     */
    Map<String, Integer> importWhiteList(List sourceData);

    /**
     * 获取白名单类型列表
     *
     * @return
     */
    List<OrbitControlWhitelistType> getAllWhitelistType();

    /**
     * 添加白名单类型
     *
     * @param blacklistType
     * @return
     */
    void addWhitelistType(OrbitControlWhitelistType blacklistType) throws DuplicateDataException;

    /**
     * 编辑白名单类型
     *
     * @param blacklistType
     * @return
     */
    void editWhitelistType(OrbitControlWhitelistType blacklistType) throws DuplicateDataException;

    /**
     * 删除白名单类型
     *
     * @param whitelistType 需要删除的白名单类型
     * @return 是否成功
     */
    void deleteWhitelistType(OrbitControlWhitelistType whitelistType) throws RelationshipException;
}

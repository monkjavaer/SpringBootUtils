package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.entity.OrbitSysConfig;
import com.car.orbit.orbitservice.vo.ModuleVO;

import java.util.List;

/**
 * @Title: IModuleService
 * @Package: com.car.orbit.orbitservice.service
 * @Description: 首页模块服务接口
 * @Author: monkjavaer
 * @Date: 2019/03/09 09:40
 * @Version: V1.0
 */
public interface IModuleService {

    /**
     * 查询首页模块设置
     *
     * @param userId
     * @return
     */
    List<ModuleVO> queryModuleList(String userId);

    /**
     * 编辑首页模块设置
     *
     * @param sysConfig
     */
    void editModule(OrbitSysConfig sysConfig);
}

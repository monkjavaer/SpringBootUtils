package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.entity.OrbitSysMonitorCenter;
import com.car.orbit.orbitservice.qo.MonitorCenterQO;
import com.car.orbit.orbitservice.vo.MonitorCenterVO;
import com.car.orbit.orbitutil.page.PageUtil;

import java.util.List;

/**
 * @Title: IMonitorCenterService
 * @Package: com.car.orbit.orbitservice.service
 * @Description: 机构接口
 * @Author: monkjavaer
 * @Data: 2019/3/7 18:17
 * @Version: V1.0
 */
public interface IMonitorCenterService {

    /**
     * 分页查询机构
     *
     * @param monitorCenterQO
     * @return
     */
    PageUtil<OrbitSysMonitorCenter> queryPageList(MonitorCenterQO monitorCenterQO);

    /**
     * 查询机构
     *
     * @param monitorCenterId
     * @return
     */
    OrbitSysMonitorCenter queryById(String monitorCenterId);

    /**
     * 机构名查重
     *
     * @param name
     * @return true 机构名已经存在
     */
    Boolean hasMonitorCenterName(String name);

    /**
     * 查询所有机构信息
     *
     * @return
     */
    List<MonitorCenterVO> queryAllList();

    /**
     * 添加机构
     *
     * @param orbitSysMonitorCenter
     */
    void addMonitorCenter(OrbitSysMonitorCenter orbitSysMonitorCenter);

    /**
     * 更新机构
     *
     * @param orbitSysMonitorCenter
     */
    void updateMonitorCenter(OrbitSysMonitorCenter orbitSysMonitorCenter);

    /**
     * 删除机构（逻辑删除，delete字段改为2）
     *
     * @param orbitSysMonitorCenter
     */
    void deleteMonitorCenter(OrbitSysMonitorCenter orbitSysMonitorCenter);

    /**
     * 检测机构名是否已存在
     *
     * @param monitorCenterId 机构id，用于查询时排除
     * @param name 需要更改的name
     * @return true-有重复，false-没有重复
     */
    boolean checkNameDuplicate(String monitorCenterId, String name);

    /**
     * 检测某机构下是否含有用户
     *
     * @param monitorCenterId 机构id
     * @return true-含有用户，false-不含有
     */
    boolean hasUsers(String monitorCenterId);
}

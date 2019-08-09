package com.car.trunk.center.system.service;

import com.car.base.common.exception.EntityOperateException;
import com.car.base.common.exception.ValidateException;
import com.car.base.common.utilities.PageList;
import com.car.base.service.interfaces.IBaseService;

import java.math.BigInteger;
import java.util.List;

/**
 * Description:监控中心服务接口
 * Created by monkjavaer on 2017/12/6 0006.
 */
public interface MonitorCenterService extends IBaseService<MonitorCenterEntity,MonitorCenterDao> {

    /**
     * 添加机构
     * @param monitorCenterEntity
     * @throws EntityOperateException
     * @throws ValidateException
     */
    void addMonitor(MonitorCenterEntity monitorCenterEntity) throws EntityOperateException, ValidateException;

    /**
     * 跟新机构
     * @param monitorCenterEntity
     * @throws EntityOperateException
     * @throws ValidateException
     */
    void updateMonitor(MonitorCenterEntity monitorCenterEntity) throws EntityOperateException, ValidateException;

    /**
     * 机构分页列表查询
     * @param monitorCenterVO
     * @return
     */
    PageList<MonitorCenterEntity> queryList(MonitorCenterVO monitorCenterVO);

    /**
     * 查询所有机构列表
     * @return
     */
    List<MonitorCenterBO> queryAllList();

    /**
     * 机构名查重
     * @param name
     * @return
     */
    boolean checkName(String name);

    /**
     * 代码查重
     * @param adminRegionCode
     * @return
     */
    boolean checkCode(String adminRegionCode);

    /**
     * 删除机构
     * @param monitorCenterEntity
     * @throws EntityOperateException
     * @throws ValidateException
     */
    void deleteMonitor(MonitorCenterEntity monitorCenterEntity) throws EntityOperateException, ValidateException;

    /**
     * 根据设备ID查询所属机构信息
     * @param deviceID
     * @return
     */
    MonitorCenterEntity getByDeviceID(BigInteger deviceID);
}

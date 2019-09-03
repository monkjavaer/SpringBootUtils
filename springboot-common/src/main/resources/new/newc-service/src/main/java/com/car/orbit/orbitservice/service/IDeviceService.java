package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.entity.OrbitResDevice;
import com.car.orbit.orbitservice.entity.OrbitResDeviceManufacturer;
import com.car.orbit.orbitservice.entity.OrbitResDeviceType;
import com.car.orbit.orbitservice.exception.DuplicateDataException;
import com.car.orbit.orbitservice.exception.GeoPositionDuplicateException;
import com.car.orbit.orbitservice.exception.IllegalParamException;
import com.car.orbit.orbitservice.exception.RelationshipException;
import com.car.orbit.orbitservice.qo.DeviceQO;
import com.car.orbit.orbitservice.vo.DeviceDetailVO;
import com.car.orbit.orbitservice.vo.DeviceSimpleVO;
import com.car.orbit.orbitutil.page.PageUtil;

import java.util.List;

/**
 * @Title: IDeviceService
 * @Package: com.car.orbit.orbitservice.service
 * @Description: 设备管理接口
 * @Author: monkjavaer
 * @Data: 2019/3/8 11:07
 * @Version: V1.0
 */
public interface IDeviceService {

    /**
     * 添加设备
     * @param device OrbitResDevice
     */
    void addDevice(OrbitResDevice device) throws DuplicateDataException, GeoPositionDuplicateException;

    /**
     * 删除设备
     * @param device OrbitResDevice
     */
    void deleteDevice(OrbitResDevice device) throws RelationshipException;

    /**
     * 修改设备
     * @param device OrbitResDevice
     */
    void updateDevice(OrbitResDevice device) throws IllegalParamException, DuplicateDataException, GeoPositionDuplicateException;

    /**
     * 设备分页列表
     * @param deviceQO 设备分页查询qo
     * @return PageUtil<OrbitResDevice>
     */
    PageUtil<DeviceDetailVO> queryPageList(DeviceQO deviceQO);

    /**
     * 查询所有未被删除设备
     * @param device
     * @return List<DeviceSimpleVO>
     */
    List<DeviceSimpleVO> queryAllList(OrbitResDevice device);

    /**
     * 获取用户权限内所有设备的Id列表
     * @return
     */
    List<String> queryAllDeviceIdList();

    /**
     * 设备类型列表
     * @return
     */
    List<OrbitResDeviceType> queryDeviceType();

    /**
     * 设备厂商列表
     * @return
     */
    List<OrbitResDeviceManufacturer> queryDeviceManufacturer();

    /**
     * 根据id查询设备
     * @param id
     * @return
     */
    OrbitResDevice getDeviceById(String id);

    /**
     * 启动时加载sdk
     */
    void initSdk();
}

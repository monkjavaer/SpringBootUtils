package com.car.orbit.orbitservice.mapper;

import com.car.orbit.orbitservice.entity.OrbitResDevice;
import com.car.orbit.orbitservice.entity.OrbitResDeviceExtends;
import com.car.orbit.orbitservice.qo.DeviceQO;
import com.car.orbit.orbitservice.vo.DeviceDetailVO;
import com.car.orbit.orbitservice.vo.DeviceSimpleVO;
import com.car.orbit.orbitservice.vo.DeviceTreeVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrbitResDeviceMapper extends Mapper<OrbitResDevice> {

    /**
     * 设备分页查询
     * @param deviceQO
     * @return
     */
    List<DeviceDetailVO> queryListByPage(DeviceQO deviceQO);

    /**
     * 查询所有未被删除设备
     * @param device
     * @return
     */
    List<DeviceSimpleVO> queryAllList(OrbitResDeviceExtends device);

    /**
     * 拼装数据用于树形点位选择
     * @return
     */
    List<DeviceTreeVO> queryTreeData(OrbitResDeviceExtends device);
}
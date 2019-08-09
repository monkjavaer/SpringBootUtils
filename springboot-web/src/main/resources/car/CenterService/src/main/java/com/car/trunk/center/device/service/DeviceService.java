package com.car.trunk.center.device.service;

import com.car.base.common.exception.EntityOperateException;
import com.car.base.common.exception.ValidateException;
import com.car.base.common.utilities.PageList;
import com.car.base.service.interfaces.IBaseService;
import com.car.trunk.dal.device.bo.DeviceBO;
import com.car.trunk.dal.device.bo.DeviceQueryVO;
import com.car.trunk.dal.device.bo.DeviceSimpleBO;
import com.car.trunk.dal.device.dao.DeviceDao;
import com.car.trunk.dal.device.vo.DeviceRedisVO;
import com.car.trunk.dal.device.vo.DeviceSimpleVO;
import com.car.trunk.dal.device.vo.DeviceUserVO;
import com.car.trunk.dal.device.vo.DeviceVO;
import com.car.trunk.dal.model.DeviceEntity;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Description:设备接口
 * Created by monkjavaer on 2017/12/6 0006.
 */
public interface DeviceService extends IBaseService<DeviceEntity,DeviceDao> {


    /**
     * 设备分页列表
     * @param deviceVO
     * @return
     */
    PageList<DeviceBO> queryList(DeviceVO deviceVO,BigInteger userId,String userName);

    /**
     * 根据名字，路口模糊查询摄像头列表
     * @param deviceSimpleVO
     * @return
     */
    List<DeviceSimpleBO> queryDeviceList(DeviceSimpleVO deviceSimpleVO);

    /**
     * 根据名字和系统登录账号，路口模糊查询摄像头列表
     * @param deviceSimpleVO
     * @param userId
     * @return
     */
    List<DeviceUserVO> queryDeviceListByUser(DeviceSimpleVO deviceSimpleVO, BigInteger userId);


    /**
     * 同步摄像头时新增device和deviceLog
     * @param deviceEntity
     * @throws EntityOperateException
     * @throws ValidateException
     */
    void addDevice(DeviceEntity deviceEntity) throws EntityOperateException, ValidateException;

    /**
     * 根据设备号查询设备
     * @param deviceId
     * @return
     */
    List<DeviceEntity> queryDeviceByDeviceId(String deviceId);

    /**
     * 同步设备时更新设备和设备状态表
     * @param lastUpdateTime 更新之前的更新时间
     * @param deviceEntity
     * @throws EntityOperateException
     * @throws ValidateException
     * @throws ParseException
     */
    void updateSynchronizeDevice(Date lastUpdateTime,DeviceEntity deviceEntity) throws EntityOperateException, ValidateException, ParseException;

    /**
     * 根据设备主键查询位置信息
     * @param deviceId
     * @return
     */
    List<DeviceQueryVO> queryInfoByDeviceId(BigInteger deviceId);


    /**
     * 查询没有删除的电子警察部分信息
     * @return
     */
    List<DeviceRedisVO> queryDevicetoRedis();
}

package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.device.dao;

import com.base.springboot.car.Base.src.main.java.com.car.base.dao.interfaces.IBaseDao;
import com.base.springboot.car.Base.src.main.java.com.car.base.model.models.ResultTuple;
import com.car.trunk.dal.device.bo.DeviceBO;
import com.car.trunk.dal.device.bo.DeviceQueryVO;
import com.car.trunk.dal.device.bo.DeviceSimpleBO;
import com.car.trunk.dal.device.vo.DeviceRedisVO;
import com.car.trunk.dal.device.vo.DeviceSimpleVO;
import com.car.trunk.dal.device.vo.DeviceVO;
import com.car.trunk.dal.model.DeviceEntity;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;

/**
 * Created by monkjavaer on 2017/12/6 0006.
 */
public interface DeviceDao extends IBaseDao<DeviceEntity> {
    /**
     * 设备分页列表
     * @param deviceVO
     * @return
     */
    ResultTuple<DeviceBO> queryList(DeviceVO deviceVO,BigInteger userId,String userName);

    /**
     * 根据名字，路口模糊查询摄像头列表
     * @param deviceSimpleVO
     * @return
     */
    List<DeviceSimpleBO> queryDeviceList(DeviceSimpleVO deviceSimpleVO);

    /**
     * 根据名字和当前登录账号，路口模糊查询摄像头列表
     * @param deviceSimpleVO
     * @param userId
     * @return
     */
    public List<DeviceSimpleBO> queryDeviceListByUser(DeviceSimpleVO deviceSimpleVO,BigInteger userId);

    /**
     * 根据设备号查询设备
     * @param deviceId
     * @return
     */
    List<DeviceEntity> queryDeviceByDeviceId(String deviceId);

    /**
     * 根据设备主键查询位置信息
     * @param deviceId
     * @return
     */
    List<DeviceQueryVO> queryInfoByDeviceId(BigInteger deviceId);


    /**
     * 每小时定时检查智能主机是否在线
     * @return
     * @throws ParseException
     */
    List<BigInteger> selectOnlineTerminal() throws ParseException;

    /**
     * 查询没有删除的电子警察部分信息
     * @return
     */
    List<DeviceRedisVO> queryDevicetoRedis();
}

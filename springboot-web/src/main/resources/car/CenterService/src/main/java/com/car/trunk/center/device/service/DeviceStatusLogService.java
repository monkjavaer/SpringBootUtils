package com.car.trunk.center.device.service;

import com.car.base.service.interfaces.IBaseService;
import com.car.trunk.dal.device.bo.DeviceLogDTO;
import com.car.trunk.dal.device.dao.DeviceStatusLogDao;
import com.car.trunk.dal.device.vo.DeviceLogVO;
import com.car.trunk.dal.model.DeviceStatusLogEntity;
import org.springframework.dao.DataAccessException;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;

/**
 * Description:设备状态日志服务
 * Created by monkjavaer on 2017/12/6 0006.
 */
public interface DeviceStatusLogService extends IBaseService<DeviceStatusLogEntity,DeviceStatusLogDao> {

    DeviceLogDTO queryList(DeviceLogVO deviceLogVO,BigInteger userId,String userName) throws ParseException;

    /**
     * 根据设备ID查询设备状态实体
     * @param id
     * @return
     */
    DeviceStatusLogEntity querybyDeviceId(BigInteger id);

    /**
     * 根据智能主机ID查询设备状态实体
     * @param id
     * @return
     */
    DeviceStatusLogEntity querybyterminalId(BigInteger id);

    void batchUpdateDeviceStatusLog(List<DeviceStatusLogEntity> deviceStatusLogEntityList) throws DataAccessException;
}

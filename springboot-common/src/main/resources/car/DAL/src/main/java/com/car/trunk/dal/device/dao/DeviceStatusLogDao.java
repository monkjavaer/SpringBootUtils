package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.device.dao;

import com.base.springboot.car.Base.src.main.java.com.car.base.dao.interfaces.IBaseDao;
import com.car.trunk.dal.device.bo.DeviceLogDTO;
import com.car.trunk.dal.device.vo.DeviceLogVO;
import com.car.trunk.dal.model.DeviceStatusLogEntity;
import org.springframework.dao.DataAccessException;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;

/**
 * Created by monkjavaer on 2017/12/6 0006.
 */
public interface DeviceStatusLogDao extends IBaseDao<DeviceStatusLogEntity> {

    DeviceLogDTO queryList(DeviceLogVO deviceLogVO,BigInteger userId,String userName) throws ParseException;

    DeviceStatusLogEntity querybyDeviceId(BigInteger id);

    DeviceStatusLogEntity querybyterminalId(BigInteger id);

    void batchUpdateDeviceStatusLog(List<DeviceStatusLogEntity> deviceStatusLogEntityList) throws DataAccessException;
}

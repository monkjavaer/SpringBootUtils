package com.car.trunk.center.device.service;

import com.car.base.service.services.BaseService;
import com.car.trunk.dal.device.bo.DeviceLogDTO;
import com.car.trunk.dal.device.dao.DeviceStatusLogDao;
import com.car.trunk.dal.device.vo.DeviceLogVO;
import com.car.trunk.dal.model.DeviceStatusLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;

/**
 * Description:
 * Created by monkjavaer on 2017/12/6 0006.
 */
@Service("DeviceStatusLogService")
public class DeviceStatusLogServiceImpl extends BaseService<DeviceStatusLogEntity,DeviceStatusLogDao> implements DeviceStatusLogService {
    @Autowired
    public DeviceStatusLogServiceImpl(@Qualifier("DeviceStatusLogDao")DeviceStatusLogDao dao) {
        super(dao);
    }

    @Override
    public DeviceLogDTO queryList(DeviceLogVO deviceLogVO,BigInteger userId,String userName) throws ParseException {
        return entityDao.queryList(deviceLogVO,userId,userName);
    }

    @Override
    public DeviceStatusLogEntity querybyDeviceId(BigInteger id) {
        return entityDao.querybyDeviceId(id);
    }

    @Override
    public DeviceStatusLogEntity querybyterminalId(BigInteger id) {
        return entityDao.querybyterminalId(id);
    }

    @Override
    public void batchUpdateDeviceStatusLog(List<DeviceStatusLogEntity> deviceStatusLogEntityList) throws DataAccessException {
        entityDao.batchUpdateDeviceStatusLog(deviceStatusLogEntityList);
    }
}

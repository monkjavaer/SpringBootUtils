package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.device.dao;

import com.base.springboot.car.Base.src.main.java.com.car.base.dao.interfaces.IBaseDao;
import com.car.trunk.dal.model.RoadCrossPointEntity;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by monkjavaer on 2017/12/6 0006.
 */
public interface RoadCrossPointDao extends IBaseDao<RoadCrossPointEntity> {
    List<RoadCrossPointEntity> queryList(BigInteger areaId);

    BigInteger queryMonitorCenterId(BigInteger roadCrossPointId);

    List<RoadCrossPointEntity> queryListByCityId(String cityId);

    List<RoadCrossPointEntity> getByName(String name);

    List<RoadCrossPointEntity> queryRoadDevice(BigInteger areaId);
}

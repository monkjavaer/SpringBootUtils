package com.car.trunk.center.device.service;

import com.car.base.service.interfaces.IBaseService;
import com.car.trunk.dal.device.dao.RoadCrossPointDao;
import com.car.trunk.dal.model.RoadCrossPointEntity;

import java.math.BigInteger;
import java.util.List;

/**
 * Description:路口接口
 * Created by monkjavaer on 2017/12/6 0006.
 */
public interface RoadCrossPointService extends IBaseService<RoadCrossPointEntity,RoadCrossPointDao> {
    List<RoadCrossPointEntity> queryList(BigInteger areaId);

    BigInteger queryMonitorCenterId(BigInteger roadCrossPointId);

    List<RoadCrossPointEntity> queryListByCityId(String cityId);

    List<RoadCrossPointEntity> queryRoadDevice(BigInteger areaId);

    boolean checkRoadName(String name);
}

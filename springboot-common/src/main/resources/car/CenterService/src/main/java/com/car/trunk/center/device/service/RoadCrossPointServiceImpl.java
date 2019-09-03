package com.base.springboot.car.CenterService.src.main.java.com.car.trunk.center.device.service;

import com.car.base.service.services.BaseService;
import com.car.trunk.dal.device.dao.RoadCrossPointDao;
import com.car.trunk.dal.model.RoadCrossPointEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * Description:
 * Created by monkjavaer on 2017/12/6 0006.
 */
@Service("RoadCrossPointService")
public class RoadCrossPointServiceImpl extends BaseService<RoadCrossPointEntity,RoadCrossPointDao> implements RoadCrossPointService {
    @Autowired
    public RoadCrossPointServiceImpl(@Qualifier("RoadCrossPointDao")RoadCrossPointDao dao) {
        super(dao);
    }

    @Override
    public List<RoadCrossPointEntity> queryList(BigInteger areaId) {
        return entityDao.queryList(areaId);
    }

    @Override
    public BigInteger queryMonitorCenterId(BigInteger roadCrossPointId) {
        return entityDao.queryMonitorCenterId(roadCrossPointId);
    }

    @Override
    public List<RoadCrossPointEntity> queryListByCityId(String cityId) {
        return entityDao.queryListByCityId(cityId);
    }

    @Override
    public boolean checkRoadName(String name) {
        boolean result = true;
        List<RoadCrossPointEntity> list = entityDao.getByName(name);
        if(list.size() > 0){
            result = false;
        }
        return result;
    }

    @Override
    public List<RoadCrossPointEntity> queryRoadDevice(BigInteger areaId){
        return entityDao.queryRoadDevice(areaId);
    }
}

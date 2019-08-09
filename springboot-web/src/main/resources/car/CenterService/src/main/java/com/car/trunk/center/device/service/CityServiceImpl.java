package com.car.trunk.center.device.service;

import com.car.base.service.services.BaseService;
import com.car.trunk.dal.device.bo.CityBO;
import com.car.trunk.dal.device.dao.CityDao;
import com.car.trunk.dal.model.CityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * Description:城市服务
 * Created by monkjavaer on 2017/12/6 0006.
 */
@Service("CityService")
public class CityServiceImpl extends BaseService<CityEntity,CityDao> implements CityService {

    @Autowired
    public CityServiceImpl(@Qualifier("CityDao")CityDao dao) {
        super(dao);
    }

    @Override
    public List<CityBO> queryList(BigInteger monitorCenterId) {
        return entityDao.queryList(monitorCenterId);
    }

    @Override
    public List<CityBO> queryListByUserId(BigInteger userId,String userName) {
        return entityDao.queryListByUserId(userId,userName);
    }

    @Override
    public boolean checkCityName(String name) {
        boolean result = true;
        List<CityEntity> list = entityDao.getByName(name);
        if(list.size() > 0){
            result = false;
        }
        return result;
    }

    @Override
    public boolean checkCityCode(String code) {
        boolean result = true;
        List<CityEntity> list = entityDao.checkCityCode(code);
        if(list.size() > 0){
            result = false;
        }
        return result;
    }
}

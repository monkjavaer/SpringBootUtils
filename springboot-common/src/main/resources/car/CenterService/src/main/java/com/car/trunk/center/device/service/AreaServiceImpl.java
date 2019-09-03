package com.base.springboot.car.CenterService.src.main.java.com.car.trunk.center.device.service;

import com.car.base.service.services.BaseService;
import com.car.trunk.dal.device.dao.AreaDao;
import com.car.trunk.dal.model.AreaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * Description:区域服务
 * Created by monkjavaer on 2017/12/6 0006.
 */
@Service("AreaService")
public class AreaServiceImpl extends BaseService<AreaEntity,AreaDao> implements AreaService {
    @Autowired
    public AreaServiceImpl(@Qualifier("AreaDao")AreaDao dao) {
        super(dao);
    }

    @Override
    public List<AreaEntity> queryList(BigInteger cityId) {
        return entityDao.queryList(cityId);
    }

    @Override
    public boolean checkAreaName(String name) {
        boolean result = true;
        List<AreaEntity> list = entityDao.getByName(name);
        if(list.size() > 0){
            result = false;
        }
        return result;
    }

    @Override
    public boolean checkAreaCode(String code) {
        boolean result = true;
        List<AreaEntity> list = entityDao.checkAreaCode(code);
        if(list.size() > 0){
            result = false;
        }
        return result;
    }

    @Override
    public List<AreaEntity> queryListByUserId(BigInteger cityId,BigInteger userId, String userName) {
        return entityDao.queryListByUserId(cityId,userId,userName);
    }
}

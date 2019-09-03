package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.device.dao;

import com.base.springboot.car.Base.src.main.java.com.car.base.dao.interfaces.IBaseDao;
import com.car.trunk.dal.model.AreaEntity;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by monkjavaer on 2017/12/6 0006.
 */
public interface AreaDao extends IBaseDao<AreaEntity> {
    List<AreaEntity> queryList(BigInteger cityId);

    List<AreaEntity> getByName(String name);

    List<AreaEntity> checkAreaCode(String code);

    List<AreaEntity> queryListByUserId(BigInteger cityId,BigInteger userId, String userName);
}

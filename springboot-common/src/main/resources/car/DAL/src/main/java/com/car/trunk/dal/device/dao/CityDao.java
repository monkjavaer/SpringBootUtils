package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.device.dao;

import com.base.springboot.car.Base.src.main.java.com.car.base.dao.interfaces.IBaseDao;
import com.car.trunk.dal.device.bo.CityBO;
import com.car.trunk.dal.model.CityEntity;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by monkjavaer on 2017/12/6 0006.
 */
public interface CityDao extends IBaseDao<CityEntity> {
    List<CityBO> queryList(BigInteger monitorCenterId);

    List<CityEntity> getByName(String name);

    List<CityEntity> checkCityCode(String code);

    List<CityBO> queryListByUserId(BigInteger userId,String userName);
}

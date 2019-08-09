package com.car.trunk.center.device.service;

import com.car.base.service.interfaces.IBaseService;
import com.car.trunk.dal.device.bo.CityBO;
import com.car.trunk.dal.device.dao.CityDao;
import com.car.trunk.dal.model.CityEntity;

import java.math.BigInteger;
import java.util.List;

/**
 * Description:城市接口
 * Created by monkjavaer on 2017/12/6 0006.
 */
public interface CityService extends IBaseService<CityEntity,CityDao> {
    /**
     * 根据机构主键查询城市BO集合
     * @param monitorCenterId 机构主键
     * @return
     */
    List<CityBO> queryList(BigInteger monitorCenterId);

    /**
     * 根据用户主键查询城市BO集合
     * @param userId 用户主键
     * @return
     */
    List<CityBO> queryListByUserId(BigInteger userId,String userName);

    /**
     * 城市名字查重
     * @param name 输入城市名
     * @return false重名
     */
    boolean checkCityName(String name);

    /**
     * 城市编码查重
     * @param code 输入代码
     * @return false重名
     */
    boolean checkCityCode(String code);
}

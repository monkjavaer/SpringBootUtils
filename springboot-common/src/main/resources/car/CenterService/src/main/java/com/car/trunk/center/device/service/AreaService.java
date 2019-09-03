package com.base.springboot.car.CenterService.src.main.java.com.car.trunk.center.device.service;

import com.car.base.service.interfaces.IBaseService;
import com.car.trunk.dal.device.dao.AreaDao;
import com.car.trunk.dal.model.AreaEntity;

import java.math.BigInteger;
import java.util.List;

/**
 * Description:区域接口
 * Created by monkjavaer on 2017/12/6 0006.
 */
public interface AreaService  extends IBaseService<AreaEntity,AreaDao> {
    List<AreaEntity> queryList(BigInteger cityId);

    boolean checkAreaName(String name);

    boolean checkAreaCode(String code);

    /**
     * 根据用户信息查询区域
     * @param userId
     * @param userName
     * @return
     */
    List<AreaEntity> queryListByUserId(BigInteger cityId,BigInteger userId, String userName);
}

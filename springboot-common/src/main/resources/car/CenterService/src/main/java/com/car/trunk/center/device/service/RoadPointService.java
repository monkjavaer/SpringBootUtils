package com.base.springboot.car.CenterService.src.main.java.com.car.trunk.center.device.service;

import com.car.base.common.exception.EntityOperateException;
import com.car.base.common.exception.ValidateException;
import com.car.trunk.dal.model.AreaEntity;
import com.car.trunk.dal.model.CityEntity;
import com.car.trunk.dal.model.RoadCrossPointEntity;

/**
 * Description:新增修改城市，区域，路口的公共接口
 * Created by monkjavaer on 2017/12/6 0006.
 */
public interface RoadPointService{
    void addCommon(CityEntity cityEntity, AreaEntity areaEntity, RoadCrossPointEntity roadCrossPointEntity) throws EntityOperateException, ValidateException;
    void updateCommon(CityEntity cityEntity, AreaEntity areaEntity, RoadCrossPointEntity roadCrossPointEntity) throws EntityOperateException, ValidateException;
}

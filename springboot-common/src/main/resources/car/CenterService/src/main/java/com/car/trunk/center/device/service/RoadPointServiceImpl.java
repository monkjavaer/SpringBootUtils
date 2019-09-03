package com.base.springboot.car.CenterService.src.main.java.com.car.trunk.center.device.service;

import com.car.base.common.exception.EntityOperateException;
import com.car.base.common.exception.ValidateException;
import com.car.trunk.dal.model.AreaEntity;
import com.car.trunk.dal.model.CityEntity;
import com.car.trunk.dal.model.RoadCrossPointEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:新增修改城市，区域，路口的公共服务
 * Created by monkjavaer on 2017/12/6 0006.
 */
@Service("RoadPointService")
public class RoadPointServiceImpl implements RoadPointService{

    @Autowired
    private CityService cityService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private RoadCrossPointService roadCrossPointService;

    @Override
    public void addCommon(CityEntity cityEntity, AreaEntity areaEntity, RoadCrossPointEntity roadCrossPointEntity) throws EntityOperateException, ValidateException {
        if (cityEntity!=null){
            cityService.save(cityEntity);
        }
        if (areaEntity!=null){
            areaService.save(areaEntity);
        }
        if (roadCrossPointEntity!=null){
            roadCrossPointService.save(roadCrossPointEntity);
        }
    }

    @Override
    public void updateCommon(CityEntity cityEntity, AreaEntity areaEntity, RoadCrossPointEntity roadCrossPointEntity) throws EntityOperateException, ValidateException {
        if (cityEntity!=null){
            cityService.update(cityEntity);
        }
        if (areaEntity!=null){
            areaService.update(areaEntity);
        }
        if (roadCrossPointEntity!=null){
            roadCrossPointService.update(roadCrossPointEntity);
        }
    }
}

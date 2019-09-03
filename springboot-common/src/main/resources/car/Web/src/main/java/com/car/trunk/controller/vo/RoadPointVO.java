package com.base.springboot.car.Web.src.main.java.com.car.trunk.controller.vo;

import com.car.trunk.dal.model.AreaEntity;
import com.car.trunk.dal.model.CityEntity;
import com.car.trunk.dal.model.RoadCrossPointEntity;

/**
 * Description:路口分页，添加，修改接收参数
 * Created by monkjavaer on 2017/12/6 0006.
 */
public class RoadPointVO {

    private CityEntity cityEntity;
    private AreaEntity areaEntity;
    private RoadCrossPointEntity roadCrossPointEntity;

    public CityEntity getCityEntity() {
        return cityEntity;
    }

    public void setCityEntity(CityEntity cityEntity) {
        this.cityEntity = cityEntity;
    }

    public AreaEntity getAreaEntity() {
        return areaEntity;
    }

    public void setAreaEntity(AreaEntity areaEntity) {
        this.areaEntity = areaEntity;
    }

    public RoadCrossPointEntity getRoadCrossPointEntity() {
        return roadCrossPointEntity;
    }

    public void setRoadCrossPointEntity(RoadCrossPointEntity roadCrossPointEntity) {
        this.roadCrossPointEntity = roadCrossPointEntity;
    }

    public Object getObject(){
        if(cityEntity!=null) {
            return cityEntity;
        }
        if(areaEntity!=null) {
            return areaEntity;
        }
        if(roadCrossPointEntity!=null) {
            return roadCrossPointEntity;
        }
        return null;
    }

}

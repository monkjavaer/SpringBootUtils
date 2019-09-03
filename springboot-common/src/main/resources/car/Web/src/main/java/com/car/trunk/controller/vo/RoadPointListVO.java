package com.base.springboot.car.Web.src.main.java.com.car.trunk.controller.vo;

import com.car.trunk.dal.model.AreaEntity;
import com.car.trunk.dal.model.CityEntity;
import com.car.trunk.dal.model.RoadCrossPointEntity;

import java.util.List;

/**
 * Description:路口删除接收参数
 * Created by monkjavaer on 2017/12/6 0006.
 */
public class RoadPointListVO {

    private List<CityEntity> cityEntitys;
    private List<AreaEntity> areaEntitys;
    private List<RoadCrossPointEntity> roadCrossPointEntitys;

    public List<CityEntity> getCityEntitys() {
        return cityEntitys;
    }

    public void setCityEntitys(List<CityEntity> cityEntitys) {
        this.cityEntitys = cityEntitys;
    }

    public List<AreaEntity> getAreaEntitys() {
        return areaEntitys;
    }

    public void setAreaEntitys(List<AreaEntity> areaEntitys) {
        this.areaEntitys = areaEntitys;
    }

    public List<RoadCrossPointEntity> getRoadCrossPointEntitys() {
        return roadCrossPointEntitys;
    }

    public void setRoadCrossPointEntitys(List<RoadCrossPointEntity> roadCrossPointEntitys) {
        this.roadCrossPointEntitys = roadCrossPointEntitys;
    }
}

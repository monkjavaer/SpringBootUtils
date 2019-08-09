package com.car.orbit.orbitservice.bo;

import com.car.orbit.orbitservice.bo.onecaronegear.TacticsOneCarOneGearInfo;


/**
 * @Title: VehicleSearchBO
 * @Package: com.car.orbit.orbitservice.bo
 * @Description: 一车一档结果返回
 * @Author: monkjavaer
 * @Data: 2019/3/29 10:23
 * @Version: V1.0
 */
public class VehicleOneCarOneGearBO {
    /**
     * 聚合结果，Redis key
     */
    private String searchKey;

    /**
     * 聚合结果总数
     */
    private Integer count;

    /**
     * 聚合分页结果
     */
    private TacticsOneCarOneGearInfo vehicleAllInfos;


    public VehicleOneCarOneGearBO(String searchKey, Integer count, TacticsOneCarOneGearInfo vehicleAllInfos) {
        this.searchKey = searchKey;
        this.count = count;
        this.vehicleAllInfos = vehicleAllInfos;
    }
    public VehicleOneCarOneGearBO() {
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public TacticsOneCarOneGearInfo getVehicleAllInfos() {
        return vehicleAllInfos;
    }

    public void setVehicleAllInfos(TacticsOneCarOneGearInfo vehicleAllInfos) {
        this.vehicleAllInfos = vehicleAllInfos;
    }

    @Override
    public String toString() {
        return "VehicleOneCarOneGearBO{" +
                "searchKey='" + searchKey + '\'' +
                ", count=" + count +
                ", vehicleAllInfos=" + vehicleAllInfos +
                '}';
    }
}

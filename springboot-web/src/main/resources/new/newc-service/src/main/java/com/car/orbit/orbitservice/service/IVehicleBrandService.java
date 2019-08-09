package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.entity.OrbitVehicleBrand;
import com.car.orbit.orbitservice.entity.OrbitVehicleBrandChild;

import java.util.List;

/**
 * @Title: IVehicleBrandService
 * @Package: com.car.orbit.orbitservice.service
 * @Description: 车辆品牌，子品牌接口
 * @Author: monkjavaer
 * @Data: 2019/3/22 11:24
 * @Version: V1.0
 */
public interface IVehicleBrandService {

    /**
     * 从redis获取全部品牌
     *
     * @return
     */
    List<OrbitVehicleBrand> getBrandFromRedis();

    /**
     * 从redis获取全部子品牌
     *
     * @return
     */
    List<OrbitVehicleBrandChild> getChildBrandFromRedis();

    /**
     * 从mysql获取全部品牌
     *
     * @return
     */
    List<OrbitVehicleBrand> getBrand();

    /**
     * 从mysql获取全部子品牌
     *
     * @return
     */
    List<OrbitVehicleBrandChild> getChildBrand();

    /**
     * 从mysql根据品牌id获取子品牌
     *
     * @param brandId
     * @return
     */
    List<OrbitVehicleBrandChild> getChildBrand(String brandId);

    /**
     * 根据品牌code从Redis获取品牌信息
     *
     * @param code 品牌code
     * @return OrbitVehicleBrand
     */
    OrbitVehicleBrand getBrandByCode(String code);

    /**
     * 根据子品牌code从Redis获取子品牌信息
     *
     * @param code 子品牌code
     * @return OrbitVehicleBrandChild
     */
    OrbitVehicleBrandChild getChildBrandByCode(String code);
}

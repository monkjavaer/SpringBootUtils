package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.qo.RequestPrameterQo;
import com.car.orbit.orbitservice.vo.VehicleTraitVo;

import java.util.List;

/**
 * @Title: IVehicleTraitService
 * @Package: com.car.orbit.orbitservice.service
 * @Description:
 * @Author: zoumengcheng
 * @Date: 2019/03/013 16:03
 * @Version: V1.0
 */
public interface IVehicleTraitService {

    List<VehicleTraitVo> getTraitByImgAll();

    List<VehicleTraitVo> getTraitByCondition(RequestPrameterQo requestPrameter);
    /**
     * 组装好ID和对应Name的映射关系返回
     * @param requestPrameter
     * @return
     */
    List<VehicleTraitVo> getTraitWithNameByCondition(RequestPrameterQo requestPrameter);
}

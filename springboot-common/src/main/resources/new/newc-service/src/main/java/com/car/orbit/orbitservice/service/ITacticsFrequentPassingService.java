package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.bo.TacticsFrequentPassingInfo;
import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.qo.TacticsFrequentPassingQO;

/**
 * CreateDate：2019/3/27 <br/>
 * Author：monkjavaer <br/>
 * Description: 战法研判-频繁过车service
 **/
public interface ITacticsFrequentPassingService {

    /**
     * 查询频繁过车信息
     *
     * @param tacticsFrequentPassingQO 频繁过车查询条件
     * @return 频繁过车记录
     */
    VehicleSearchBO<TacticsFrequentPassingInfo> findFrequentPassingVehicles(TacticsFrequentPassingQO tacticsFrequentPassingQO);
}

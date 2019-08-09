package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.bo.PassDeckVehicleDetail;
import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.qo.DeckVehiclesQO;
import com.car.orbit.orbitservice.vo.DeckVehiclesVO;
import com.car.orbit.orbitutil.response.OrbitResult;

/**
 * CreateDate: 2019-3-27 <br/>
 * Author: monkjavaer <br/>
 * Description: 套牌车辆查询接口
 * Version: 1.0
 **/
public interface IDeckVehiclesService {

    /**
     * @description 查询套牌车辆，返回分页结果
     * @date: 2019-3-27 12:06
     * @author: monkjavaer
     * @param deckVehiclesQO
     * @return
     */
    VehicleSearchBO<DeckVehiclesVO> search(DeckVehiclesQO deckVehiclesQO);

    /**
     * @description 排除套牌车辆
     * @date: 2019-3-27 12:06
     * @author: monkjavaer
     * @param deckVehiclesQO
     * @return
     */
    OrbitResult exclude(DeckVehiclesQO deckVehiclesQO);

    /**
     * 查询套牌车详细
     *
     * @param deckVehiclesQO
     * @return
     */
    VehicleSearchBO<PassDeckVehicleDetail> queryDeckVehiclesDetail(DeckVehiclesQO deckVehiclesQO);
}

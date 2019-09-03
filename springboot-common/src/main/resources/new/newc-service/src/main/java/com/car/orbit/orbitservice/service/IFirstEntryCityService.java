package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.bo.FirstEntryCityBO;
import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.qo.FirstEntryCityQO;

/**
 * @Title: IFirstEntryCityService
 * @Package: com.car.orbit.orbitservice.service
 * @Description: 首次入城服务接口
 * @Author: monkjavaer
 * @Date: 2019/03/25 15:52
 * @Version: V1.0
 */
public interface IFirstEntryCityService {

    VehicleSearchBO<FirstEntryCityBO> queryPageList(FirstEntryCityQO firstEnterCityQO);
}

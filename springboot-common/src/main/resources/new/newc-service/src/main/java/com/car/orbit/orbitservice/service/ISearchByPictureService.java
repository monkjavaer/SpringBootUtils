package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.exception.IllegalParamException;
import com.car.orbit.orbitservice.exception.StructureException;
import com.car.orbit.orbitservice.qo.SearchByPictureQO;
import com.car.orbit.orbitservice.vo.OneVehicleFileVO;
import com.car.orbit.orbitservice.vo.VehiclePositionVO;
import com.car.orbit.orbitservice.vo.VehicleSearchVO;
import com.car.orbit.orbitservice.vo.VehicleTraitVo;

import java.io.IOException;
import java.util.List;

/**
 * @Title: ISearchByPictureService
 * @Package: com.car.orbit.orbitservice.service
 * @Description:
 * @Author: zks
 * @Date: 2019/03/18 16:05
 * @Version: V1.0
 */
public interface ISearchByPictureService {
    /**
     * 以图搜图===获取车辆位置信息
     * @param pathUrl
     * @return List<VehiclePositionVO>
     */
    List<VehiclePositionVO> obtainVehiclePosition(String pathUrl) throws IllegalParamException, IOException, StructureException;
    /**
     * 以图搜图===按照条件查询以图搜图的记录信息【最底层的过车信息】
     * 根据参数判断是否缓存命中；【redis】
     * @param condition
     * @return
     */
    VehicleSearchBO<VehicleTraitVo> imageSearch(SearchByPictureQO condition);
    /**
     * 以图搜图===按照条件返回各种情况下的一车一档数据信息
     * @param condition
     * @return
     */
    VehicleSearchBO<OneVehicleFileVO> imageSearchByOneFile(SearchByPictureQO condition);
    /**
     * 以图搜图===按照条件返回各种情况下的聚合数据信息
     * @param condition
     * @return
     */
    VehicleSearchBO<VehicleSearchVO> imageSearchByGroup(SearchByPictureQO condition);
}

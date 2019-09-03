package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.bo.NearbyPassVehicleRecordBO;
import com.car.orbit.orbitservice.bo.VehicleDetailBO;
import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.entity.OrbitPassVehicleRecord;
import com.car.orbit.orbitservice.qo.PassVehicleRecordQO;
import com.car.orbit.orbitservice.vo.OneVehicleFileVO;
import com.car.orbit.orbitservice.vo.VehicleSearchVO;

import java.util.Date;

/**
 * @Title: IPassVehicleRecordService
 * @Package: com.car.orbit.orbitservice.service
 * @Description: 综合搜车服务接口
 * @Author: monkjavaer
 * @Date: 2019/03/15 18:17
 * @Version: V1.0
 */
public interface IPassVehicleRecordService {

    /**
     * 综合搜车，未开启一车一档
     *
     * @param passVehicleRecordQO
     * @return
     */
    VehicleSearchBO<OrbitPassVehicleRecord> queryPassVehicleRecordList(PassVehicleRecordQO passVehicleRecordQO);

    /**
     * 综合搜车，开启一车一档
     *
     * @param passVehicleRecordQO
     * @return
     */
    VehicleSearchBO<OneVehicleFileVO> queryPassVehicleRecordByCar(PassVehicleRecordQO passVehicleRecordQO);

    /**
     * 综合搜车，根据路口ID聚合
     *
     * @param passVehicleRecordQO
     * @return
     */
    VehicleSearchBO<VehicleSearchVO> queryQueryPageByDevice(PassVehicleRecordQO passVehicleRecordQO);

    /**
     * 综合搜车，根据车辆子品牌聚合
     *
     * @param passVehicleRecordQO
     * @return
     */
    VehicleSearchBO<VehicleSearchVO> queryPageByBrandChild(PassVehicleRecordQO passVehicleRecordQO);

    /**
     * 根据过车记录ID查询记录详情
     *
     * @param id
     * @param captureTime 辅助定位数据表
     * @return
     */
    OrbitPassVehicleRecord queryPassVehicleRecordById(String id, Date captureTime);

    /**
     * 开启一车一档后，点击分页车辆详情
     * @param passVehicleRecordQO
     * @return
     */
    VehicleDetailBO queryDetailPage(PassVehicleRecordQO passVehicleRecordQO);

    /**
     * 查询白名单车辆过车记录
     *
     * @param passVehicleRecordQO
     * @return
     */
    VehicleSearchBO<OrbitPassVehicleRecord> querySpecialPassVehicleRecordList(PassVehicleRecordQO passVehicleRecordQO);

    /**
     * 查询前、后一条过车记录
     * @param id
     * @param captureTime
     * @return
     */
    NearbyPassVehicleRecordBO queryNearbyRecord(String id, String captureTime);
}

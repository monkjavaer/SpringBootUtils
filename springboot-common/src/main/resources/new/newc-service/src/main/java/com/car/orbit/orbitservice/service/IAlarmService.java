package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.bo.AlarmDetailBO;
import com.car.orbit.orbitservice.entity.OrbitControlAlarm;
import com.car.orbit.orbitservice.entity.OrbitPassVehicleRecord;
import com.car.orbit.orbitservice.qo.AlarmQO;
import com.car.orbit.orbitservice.vo.AlarmOneFileVO;
import com.car.orbit.orbitservice.vo.AlarmVO;
import com.car.orbit.orbitutil.page.PageUtil;

import java.util.List;

/**
 * @Title: IAlarmService
 * @Package: com.car.orbit.orbitservice.service
 * @Description: 预警service
 * @Author: monkjavaer
 * @Data: 2019/4/1 11:33
 * @Version: V1.0
 */
public interface IAlarmService {

    /**
     * 保存报警信息
     * @param alarm 报警实体类
     */
    void insertAlarm(OrbitControlAlarm alarm);


    /**
     * 查询实时预警(返回最近十条警情)
     * @return List<AlarmVO>
     */
    List<AlarmVO> queryRealTimeAlarm(String startTime,String endTime);


    /**
     * 历史警情分页列表
     * @param alarmQO AlarmQO
     * @return PageUtil<AlarmVO>
     */
    PageUtil<AlarmVO> queryHistoricalAlarm(AlarmQO alarmQO);

    /**
     * 一车一档中查看指定车牌对应的历史警情分页列表
     * @param alarmQO AlarmQO
     * @return PageUtil<AlarmVO>
     */
    PageUtil<AlarmVO> queryHistoricalAlarmOneFile(AlarmQO alarmQO);


    /**
     * 开启一车一档，预警
     * @param alarmQO AlarmQO
     * @return PageUtil<AlarmOneFileVO>
     */
    PageUtil<AlarmOneFileVO> queryAlarmOneFile(AlarmQO alarmQO);

    /**
     * 根据主键查询预警对比信息
     * @param alarmId
     * @return
     */
    AlarmDetailBO alarmDetail(String alarmId);

    /**
     * 查询未处理的警情条数
     * @return
     */
    Integer queryUntreatedAlarmNumber();

    /**
     * 更新警情
     * @param orbitControlAlarm
     */
    boolean updateAlarm(OrbitControlAlarm orbitControlAlarm);

    /**
     * 根据过车主键查询es记录
     * @param captureTime 抓拍时间 取前后5分钟 方便构造索引 提升查询性能
     * @param passVehicleId 过车ID
     * @return
     */
    OrbitPassVehicleRecord getPassVehicleRecord(String captureTime, String passVehicleId);
}

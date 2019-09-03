package com.car.orbit.orbitservice.mapper;

import com.car.orbit.orbitservice.entity.OrbitControlAlarm;
import com.car.orbit.orbitservice.qo.AlarmQO;
import com.car.orbit.orbitservice.vo.AlarmOneFileVO;
import com.car.orbit.orbitservice.vo.AlarmVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrbitControlAlarmMapper extends Mapper<OrbitControlAlarm> {

    /**
     * 查询实时预警(返回最近十条警情)
     * @return List<AlarmVO>
     */
    List<AlarmVO> queryRealTimeAlarm(@Param("userId") String userId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 查询历史警情
     * @param alarmQO
     * @return List<AlarmVO> AlarmQO
     */
    List<AlarmVO> queryHistoricalAlarm(AlarmQO alarmQO);

    /**
     * 查询历史警情-精确车牌
     * @param alarmQO
     * @return List<AlarmVO> AlarmQO
     */
    List<AlarmVO> queryHistoricalAlarmExactPlateNum(AlarmQO alarmQO);

    /**
     * 开启一车一档，预警
     * @param alarmQO AlarmQO
     * @return List<AlarmOneFileVO>
     */
    List<AlarmOneFileVO> queryAlarmOneFile(AlarmQO alarmQO);

    /**
     * 查询未处理的警情条数
     * @return
     */
    Integer queryUntreatedAlarmNumber(@Param("userId") String userId);

    void insertAlarm(OrbitControlAlarm alarm);
}
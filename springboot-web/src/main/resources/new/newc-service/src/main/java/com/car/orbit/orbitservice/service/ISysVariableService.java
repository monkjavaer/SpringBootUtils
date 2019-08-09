package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.entity.OrbitControlAlarmVoice;
import com.car.orbit.orbitservice.entity.OrbitSysVariable;

import java.util.List;

/**
 * @Title: ISysVariableService
 * @Package: com.car.orbit.orbitservice.service
 * @Description: 系统变量接口
 * @Author: monkjavaer
 * @Data: 2019/3/8 9:06
 * @Version: V1.0
 */
public interface ISysVariableService {

    /**
     * 系统参数保存时间获取
     * @return 所有系统参数列表(数据库默认配置过车记录保存时长，报警信息保存时长两条记录)
     */
    List<OrbitSysVariable> queryVariableList();

    /**
     * 更新系统保存时间
     * @param orbitSysVariable 根据主键修改
     */
    void updateSysVariable(OrbitSysVariable orbitSysVariable);

    /**
     * 更新报警铃声
     * @param voice 铃声实体
     */
    void updateVoice(OrbitControlAlarmVoice voice);

    /**
     * 查询报警铃声列表
     * @return 警情1-4级对应铃声
     */
    List<OrbitControlAlarmVoice> queryVoiceList();

    /**
     * 根据等级查询铃声
     * @param level
     * @return
     */
    OrbitControlAlarmVoice queryVoiceByLevel(Integer level);
}

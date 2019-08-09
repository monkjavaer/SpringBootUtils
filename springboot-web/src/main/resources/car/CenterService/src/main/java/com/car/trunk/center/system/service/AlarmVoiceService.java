package com.car.trunk.center.system.service;

import com.car.base.common.exception.EntityOperateException;
import com.car.base.common.exception.ValidateException;
import com.car.base.service.interfaces.IBaseService;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by monkjavaer on 2017/12/15 0015.
 */
public interface AlarmVoiceService extends IBaseService<AlarmVoiceEntity,AlarmVoiceDao> {

    void addAlarmVoice(AlarmVoiceEntity alarmVoiceEntity) throws EntityOperateException, ValidateException;

    void updateAlarmVoice(AlarmVoiceEntity alarmVoiceEntity) throws EntityOperateException, ValidateException;

    List<AlarmVoiceBO> queryList(BigInteger userId);
}

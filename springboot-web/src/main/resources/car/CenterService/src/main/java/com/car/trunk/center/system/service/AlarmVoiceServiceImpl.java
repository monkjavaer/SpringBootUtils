package com.car.trunk.center.system.service;

import com.car.base.common.exception.EntityOperateException;
import com.car.base.common.exception.ValidateException;
import com.car.base.service.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by monkjavaer on 2017/12/15 0015.
 */
@Service("AlarmVoiceService")
public class AlarmVoiceServiceImpl extends BaseService<AlarmVoiceEntity,AlarmVoiceDao> implements  AlarmVoiceService {
    @Autowired
    public AlarmVoiceServiceImpl(@Qualifier("AlarmVoiceDao")AlarmVoiceDao dao) {
        super(dao);
    }

    @Override
    public void addAlarmVoice(AlarmVoiceEntity alarmVoiceEntity) throws EntityOperateException, ValidateException {
        entityDao.save(alarmVoiceEntity);
    }

    @Override
    public void updateAlarmVoice(AlarmVoiceEntity alarmVoiceEntity) throws EntityOperateException, ValidateException {
        entityDao.update(alarmVoiceEntity);
    }

    @Override
    public List<AlarmVoiceBO> queryList(BigInteger userId) {
        return entityDao.queryList(userId);
    }
}

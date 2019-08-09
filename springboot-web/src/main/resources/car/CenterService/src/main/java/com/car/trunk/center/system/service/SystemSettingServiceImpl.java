package com.car.trunk.center.system.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: 系统参数设置
 * Original Author: monkjavaer, 2017/12/21
 */
@Service
public class SystemSettingServiceImpl implements SystemSettingService {
    private static final Logger logger = LoggerFactory.getLogger(SystemSettingServiceImpl.class);

    @Autowired
    private SystemVariableDao systemVariableDao;

    @Override
    public List<SystemVariableEntity> querySettingList() {
        return systemVariableDao.listAll();
    }

    @Override
    public boolean updateSystemSetting(int id, int value) {
        return systemVariableDao.updateSystemSetting(id, value);
    }
}

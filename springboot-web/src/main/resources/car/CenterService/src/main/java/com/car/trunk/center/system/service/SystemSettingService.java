package com.car.trunk.center.system.service;

import java.util.List;

/**
 * Description: 系统参数设置接口
 * Original Author: monkjavaer, 2017/12/21
 */
public interface SystemSettingService {
    List<SystemVariableEntity> querySettingList();
    boolean updateSystemSetting(int id, int value);
}

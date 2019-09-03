package com.base.springboot.car.CenterService.src.main.java.com.car.trunk.center.system.service;

import java.util.List;

/**
 * @Title: ISystemDicService
 * @Package: com.car.trunk.center.system.service
 * @Description: TODO（系统字典接口）
 * @Author: monkjavaer
 * @Data: 2019/5/10 10:27
 * @Version: V1.0
 */
public interface ISystemDicService {


    public List<SystemDicEntity> getAllSystemDic();

    public List<SystemDicEntity> getSystemDicByType(String type);


}

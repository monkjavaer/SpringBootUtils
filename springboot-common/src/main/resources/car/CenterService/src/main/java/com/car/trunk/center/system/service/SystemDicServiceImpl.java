package com.base.springboot.car.CenterService.src.main.java.com.car.trunk.center.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Title: SystemDicServiceImpl
 * @Package: com.car.trunk.center.system.service
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2019/5/10 10:29
 * @Version: V1.0
 */
@Service
public class SystemDicServiceImpl implements ISystemDicService{

    @Autowired
    private SystemDicDAO systemDicDAO;

    @Override
    public List<SystemDicEntity> getAllSystemDic() {
        return systemDicDAO.listAll();
    }

    @Override
    public List<SystemDicEntity> getSystemDicByType(String type) {
        return systemDicDAO.getSystemDicByType(type);
    }
}

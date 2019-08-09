package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.bo.VehicleTypeMappingBO;
import com.car.orbit.orbitservice.entity.OrbitSysUser;
import com.car.orbit.orbitservice.entity.OrbitVehicleTypeMapping;
import com.car.orbit.orbitservice.mapper.OrbitVehicleTypeMappingMapper;
import com.car.orbit.orbitservice.qo.VehicleTypeMappingQO;
import com.car.orbit.orbitservice.service.IVehicleTypeMappingService;
import com.car.orbit.orbitservice.util.redis.OrbitSysUserRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: VehicleTypeMappingServiceImpl
 * @Package: com.car.orbit.orbitservice.service.impl
 * @Description: 车辆类型映射service实现类
 * @Author: monkjavaer
 * @Date: 2019/07/31 15:51
 * @Version: V1.0
 */
@Service
public class VehicleTypeMappingServiceImpl implements IVehicleTypeMappingService {

    @Autowired
    private OrbitVehicleTypeMappingMapper mapper;

    /**
     * 查询车辆类型映射列表
     *
     * @param mappingQO
     * @return
     */
    @Override
    public List<VehicleTypeMappingBO> queryTypeList(VehicleTypeMappingQO mappingQO) {
        List<OrbitVehicleTypeMapping> list = mapper.queryDistinctList(mappingQO);

        OrbitSysUser currentUser = OrbitSysUserRedis.getCurrentLoginUser();
        String language = currentUser.getLanguage().toUpperCase();

        List<VehicleTypeMappingBO> resultList = new ArrayList<>();
        list.stream().forEach(item -> {
            VehicleTypeMappingBO bo = new VehicleTypeMappingBO();
            bo.setId(item.getCode());
            if (language.equals("ZH-CN")) {
                bo.setLabel(item.getZhName());
            } else if (language.equals("EN")) {
                bo.setLabel(item.getEnName());
            } else if (language.equals("ES")) {
                bo.setLabel(item.getEsName());
            } else {
                bo.setLabel(item.getZhName());
            }
            resultList.add(bo);
        });

        return resultList;
    }
}

package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.bo.ColorMappingBO;
import com.car.orbit.orbitservice.entity.OrbitColorMapping;
import com.car.orbit.orbitservice.entity.OrbitSysUser;
import com.car.orbit.orbitservice.mapper.OrbitColorMappingMapper;
import com.car.orbit.orbitservice.qo.ColorMappingQO;
import com.car.orbit.orbitservice.service.IColorMappingService;
import com.car.orbit.orbitservice.util.redis.OrbitSysUserRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: ColorMappingServiceImpl
 * @Package: com.car.orbit.orbitservice.service.impl
 * @Description: 颜色映射service实现类
 * @Author: monkjavaer
 * @Date: 2019/07/31 15:07
 * @Version: V1.0
 */
@Service
public class ColorMappingServiceImpl implements IColorMappingService {

    @Autowired
    private OrbitColorMappingMapper mapper;

    /**
     * 查询颜色映射列表
     *
     * @param colorMappingQO
     * @return
     */
    @Override
    public List<ColorMappingBO> queryColorList(ColorMappingQO colorMappingQO) {
        List<OrbitColorMapping> list = mapper.queryDistinctList(colorMappingQO);

        OrbitSysUser currentUser = OrbitSysUserRedis.getCurrentLoginUser();
        String language = currentUser.getLanguage().toUpperCase();

        List<ColorMappingBO> resultList = new ArrayList<>();
        list.stream().forEach(item -> {
            ColorMappingBO bo = new ColorMappingBO();
            bo.setId(item.getCode());
            bo.setValue(item.getCode());
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

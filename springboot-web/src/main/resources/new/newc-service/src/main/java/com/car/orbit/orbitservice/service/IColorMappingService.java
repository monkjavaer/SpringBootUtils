package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.bo.ColorMappingBO;
import com.car.orbit.orbitservice.qo.ColorMappingQO;

import java.util.List;

/**
 * 颜色映射service
 */
public interface IColorMappingService {

    /**
     * 查询颜色映射列表
     *
     * @param colorMappingQO
     * @return
     */
    List<ColorMappingBO> queryColorList(ColorMappingQO colorMappingQO);
}

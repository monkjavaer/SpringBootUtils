package com.m.gis.springboot.service;

import com.m.gis.springboot.vo.DictVO;

import java.util.List;

/**
 * @Title: GisDistrictTypeService
 * @Package: com.m.gis.springboot.service
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/27
 * @Version: V1.0
 */
public interface GisDistrictTypeService {
    /***
     * @Description: 获取行政区域类型数据字典，从district.xml配置文件返回
     * @Author: monkjavaer
     * @Data: 16:39 2018/7/2
     * @Param: []
     * @Throws
     * @Return java.util.List<com.m.gis.springboot.vo.DictVO>
     */
    public List<DictVO> getDictVO();
}

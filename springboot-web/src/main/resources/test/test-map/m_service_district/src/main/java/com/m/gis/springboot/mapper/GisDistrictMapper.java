package com.m.gis.springboot.mapper;

import com.m.gis.springboot.district.common.GisDistrict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GisDistrictMapper{

    /***
     * @Description: 获取行政区域名称
     * @Author: monkjavaer
     * @Data: 10:15 2018/6/25
     * @Param: [tableName]
     * @Throws
     * @Return java.util.List<com.m.gis.springboot.po.GisDistrict>
     */
    List<GisDistrict> selectAll(@Param("tableName") String tableName,
                                @Param("level") Integer level);

    /***
     * @Description: 获取指定行政区域表的某一个行政区域对象
     * @Author: monkjavaer
     * @Data: 17:04 2018/7/4
     * @Param: [tableName, id]
     * @Throws
     * @Return com.m.gis.springboot.district.common.GisDistrict
     */
    GisDistrict selectById(@Param("tableName") String tableName, @Param("id") Integer id);

}
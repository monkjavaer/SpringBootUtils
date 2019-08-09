package com.m.gis.springboot.service;

import com.m.gis.springboot.vo.GisDistrictDictVO;
import com.m.gis.springboot.vo.GisDistrictVO;

import java.util.List;

/**
 * @Title: GisBolDistrictService
 * @Package: com.m.gis.springboot.service
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/29
 * @Version: V1.0
 */
public interface GisBolDistrictService {

    /***
     * @Description: 获取所有行政区域的数据字典，包含行政区域编码、名字、
     * @Author: monkjavaer
     * @Data: 11:56 2018/6/29
     * @Param: []
     * @Throws
     * @Return java.util.List<GisDistrictDictVO>
     */
    List<GisDistrictDictVO> getDistrictDict();

    /***
     * @Description: 获取所有行政区域对象的信息，包括轮廓、名称、编码
     * @Author: monkjavaer
     * @Data: 10:07 2018/7/10
     * @Param: []
     * @Throws
     * @Return java.util.List<com.m.gis.springboot.vo.GisDistrictVO>
     */
    List<GisDistrictVO> getDistrictAll();

    /***
     * @Description: 返回指定类型和父行政区域编码的行政区域对象
     * @Author: monkjavaer
     * @Data: 15:18 2018/7/11
     * @Param: [districtType, parentCode]
     * @Throws
     * @Return java.util.List<com.m.gis.springboot.vo.GisDistrictVO>
     */
    List<GisDistrictVO> getDistrictByType(String districtTypeCode,String parentCode);

}

package com.m.gis.springboot.service;

import com.m.gis.springboot.district.common.GisDistrict;
import com.m.gis.springboot.district.common.GisDistrictType;
import com.m.gis.springboot.exception.GisDistrictServiceThrowableException;
import com.m.gis.springboot.geo.base.common.GisCoordinate;
import com.m.gis.springboot.vo.GisDistrictNameVO;

import java.util.List;

/**
 * @Title: GisDistrictService
 * @Package: com.m.gis.springboot.service
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/13
 * @Version: V1.0
 */
public interface GisDistrictService {

    /***
     * @Description: 获取指定行政区域类型和gid的行政区域对象
     * @Author: monkjavaer
     * @Data: 17:08 2018/7/4
     * @Param: [districtTypeCode, gid]
     * @Throws
     * @Return com.m.gis.springboot.district.common.GisDistrict
     */
    GisDistrict getDistrictById(String districtTypeCode, Integer gid);

    /***
     * @Description: 获取指定行政区域类型和gid的行政区域对象
     * @Author: monkjavaer
     * @Data: 17:08 2018/7/4
     * @Param: [type, gid]
     * @Throws
     * @Return com.m.gis.springboot.district.common.GisDistrict
     */
    GisDistrict getDistrictById(GisDistrictType type, Integer gid);


    /***
     * @Description: 获取该行政等级的所有行政区域
     * @Author: monkjavaer
     * @Data: 17:49 2018/6/27
     * @Param: [type]
     * @Throws
     * @Return java.util.List<com.m.gis.springboot.po.GisDistrict>
     */
    List<GisDistrict> getDistrict(GisDistrictType type);


    /***
     * @Description: 获取该行政等级且父行政编码为parent的所有行政区域
     * @Author: monkjavaer
     * @Data: 17:50 2018/6/27
     * @Param: [type, code]
     * @Throws
     * @Return java.util.List<com.m.gis.springboot.po.GisDistrict>
     */
    List<GisDistrict> getDistrict(GisDistrictType type, String parent);


    /***
     * @Description: 获取对应编码的行政区域，编码为空或者null时，返回全国的行政区域
     * @Author: monkjavaer
     * @Data: 16:25 2018/6/28
     * @Param: [districtCode]
     * @Throws
     * @Return com.m.gis.springboot.po.GisDistrict
     */
    GisDistrict getDistrict(String districtCode) throws GisDistrictServiceThrowableException;


    /***
     * @Description: 获取用于select中的行政区域列表
     * @Author: monkjavaer
     * @Data: 17:58 2018/6/27
     * @Param: []
     * @Throws
     * @Return java.util.List<com.m.gis.springboot.vo.GisDistrictNameVO>
     */
    List<GisDistrictNameVO> getDistrictItem(String type, String parent);

    /***
     * @Description: 根据经纬度获取对应类型的行政区域
     * @Author: monkjavaer
     * @Data: 14:59 2018/6/27
     * @Param: [coordinate, type]
     * @Throws
     * @Return com.m.gis.springboot.po.GisDistrict
     */
    GisDistrict getDistrict(GisCoordinate coordinate,GisDistrictType type) throws GisDistrictServiceThrowableException;

    /***
     * @Description: 根据经纬度获取该点对应的行政区域编码
     * @Author: monkjavaer
     * @Data: 15:03 2018/6/27
     * @Param: [coordinate]
     * @Throws 
     * @Return java.lang.String
     */ 
    String getDistrictCode(GisCoordinate coordinate);

    /**
     * 根据行政区划代码获取行政区划全名
     *
     *
     * @param districtCode 乡镇区划编码
     * @return 国,省,市,区...
     */
    String getFullNameByDistrictCode(String districtCode);

}

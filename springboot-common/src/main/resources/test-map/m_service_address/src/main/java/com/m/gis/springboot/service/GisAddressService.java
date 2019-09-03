package com.m.gis.springboot.service;

import com.m.gis.springboot.common.GisPage;
import com.m.gis.springboot.po.GisAddressT;
import com.m.gis.springboot.qo.*;
import com.m.gis.springboot.vo.GisAddressNameInfoVO;
import com.m.gis.springboot.vo.GisAddressNameLonLatVO;
import com.m.gis.springboot.vo.GisAddressNameVO;

import java.util.List;

/**
 * @Title: GisAddressService
 * @Package: com.m.gis.springboot.service
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/19
 * @Version: V1.0
 */
public interface GisAddressService {
    /***
     * @Description: 批量插入地名
     * @Author: monkjavaer
     * @Data: 16:16 2018/8/14
     * @Param: [qo]
     * @Throws
     * @Return java.lang.Integer
     */
    Integer insertAddressNameList(List<GisAddressT> qo);

    /***
     * @Description: 批量更新地名
     * @Author: monkjavaer
     * @Data: 16:16 2018/8/14
     * @Param: [qo]
     * @Throws
     * @Return java.lang.Integer
     */
    Integer updateAddressNameList(List<GisAddressT> qo);

    /***
     * @Description: 获取模糊匹配的地名查询分页结果
     * @Author: monkjavaer
     * @Data: 14:50 2018/6/19
     * @Param: [qo]
     * @Throws
     * @Return com.m.gis.springboot.common.GisPage<com.m.gis.springboot.vo.GisAddressNameVO>
     */
    GisPage<GisAddressNameVO> getAddressNamePages(GisAddressNameQO qo);

    /***
     * @Description: 根据经纬度获取地名
     * @Author: monkjavaer
     * @Data: 10:27 2018/6/21
     * @Param: [qo]
     * @Throws
     * @Return com.m.gis.springboot.vo.GisAddressNameLonLatVO
     */
    GisAddressNameLonLatVO getAddressNameByLonLat(GisAddressNameLonLatQO qo);

    /***
     * @Description: 获取地名的详细信息
     * @Author: monkjavaer
     * @Data: 16:56 2018/7/4
     * @Param: [gid, type]
     * @Throws
     * @Return com.m.gis.springboot.vo.GisAddressNameInfoVO
     */
    GisAddressNameInfoVO getAddressNameInformation(Integer gid, String type);


    /***
     * @Description: 批量添加地址名
     * @Author: monkjavaer
     * @Data: 16:58 2018/6/21
     * @Param: [list]
     * @Throws
     * @Return java.lang.Integer
     */
    Integer saveAddressNameList(GisAddressNameSaveQO qo);


    /***
     * @Description: 批量添加或更新地址名
     * @Author: monkjavaer
     * @Data: 16:58 2018/6/21
     * @Param: [list]
     * @Throws
     * @Return java.lang.Integer
     */
    Integer saveOrUpdateAddressNameList(GisAddressNameSaveQO qo);


    /***
     * @Description: 判断地名是否重复，依据为地名是否与已存在的地名经纬度在一定容差内重叠
     * @Author: monkjavaer
     * @Data: 15:58 2018/6/22
     * @Param: [qo]
     * @Throws
     * @Return java.lang.Boolean
     */
    Boolean getAddressNameIfDuplicated(GisCoordinateQO qo);

    /***
     * @Description: 判断地名是否重复，依据为地名是否与已存在的地名经纬度和名字相等
     * @Author: monkjavaer
     * @Data: 16:50 2018/8/14
     * @Param: [qo]
     * @Throws
     * @Return java.lang.Boolean
     */
    Boolean getAddressNameIfDuplicatedByNameAndLonLat(GisAddressNameElementQO qo);

}

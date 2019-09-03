package com.m.gis.springboot.common;

import com.m.gis.springboot.enums.GisAddressTypeEnums;

/**
 * @Title: GisAddressConstants
 * @Package: com.m.gis.springboot.common
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/21
 * @Version: V1.0
 */
public class GisAddressConstants {
    /**
     * 默认的地名容差值，如果两个地名的距离小于该距离，则认为是一个地名（单位度，默认为10米）
     */
    public final static Double DEFAULT_ADDRESS_NAME_TOLERANCE = 0.0001;

    public final static Double DEFAULT_ADDRESS_NAME_TOLERANCE_METERS = DEFAULT_ADDRESS_NAME_TOLERANCE;

    /**
     * 根据经纬度查询地名时，默认的地名类型，不包括行政区域地名
     */
    public final static String DEFAULT_LONLAT_QUERY_ADDRESS_TYPE = GisAddressTypeEnums.getLonLatQueryType();
}

package com.car.orbit.orbitservice.constant;

/**
 * @Title: ExportConstant
 * @Package: com.car.orbit.orbitservice.constant
 * @Description: 导出常量配置
 * @Author: monkjavaer
 * @Data: 2019/3/26 15:27
 * @Version: V1.0
 */
public class ExportConstant {

    //*************导出模块代码(对应数据库表orbit_export的CODE字段)****************/
    /** 10-根据路口聚合导出 **/
    public static final String EXPORT_SEARCH_ROAD = "10";
    /** 11-根据品牌聚合 */
    public static final String EXPORT_SEARCH_BRAND = "11";
    /**13-综合搜车（未开一车一档）*/
    public static final String EXPORT_SEARCH = "12";
    /**12-综合搜车（开一车一档）*/
    public static final String EXPORT_SEARCH_ONLY = "13";
    /**14-以图搜车根据路口聚合*/
    public static final String EXPORT_PIC_SEARCH_ROAD = "14";
    /**15-以图搜车根据品牌聚合*/
    public static final String EXPORT_PIC_SEARCH_BRAND = "15";
    /**16-以图搜车（最底层详情导出）*/
    public static final String EXPORT_PIC_SEARCH = "16";
    /**17-以图搜车（开一车一档层数据导出）*/
    public static final String EXPORT_PIC_SEARCH_ONEFILE = "17";

    /**18-综合搜车带图片（未开一车一档）*/
    public static final String EXPORT_SEARCH_WITHPICTURE = "18";
    /**19-综合搜车带图片（开一车一档）*/
    public static final String EXPORT_SEARCH_ONLY_WITHPICTURE = "19";

    /**16-以图搜车（最底层详情导出）==带图片*/
    public static final String EXPORT_PIC_SEARCH_WITHPICTURE = "20";
    /**17-以图搜车（开一车一档层数据导出）==带图片*/
    public static final String EXPORT_PIC_SEARCH_ONEFILE_WITHPICTURE = "21";
    /**22-白名单导出*/
    public static final String EXPORT_WHITE_LIST = "22";
    /**23-黑名单导出*/
    public static final String EXPORT_BLACK_LIST = "23";
}

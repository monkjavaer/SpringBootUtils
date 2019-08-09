package com.car.orbit.orbitservice.constant;

import com.car.orbit.orbitservice.entity.OrbitVehicleType;
import com.car.vision.VisionSDK;

import java.util.List;

/**
 * @Title: OrbitServiceConstant
 * @Package: com.car.orbit.orbitservice.constant
 * @Description: 系统常量定义
 * @Author: monkjavaer
 * @Data: 2019/3/12 9:37
 * @Version: V1.0
 */
public class OrbitServiceConstant {

    /**
     * 视频监控SDK,系统启动时初始化
     */
    public static VisionSDK visionSDK = null;

    /**
     * Video agent service, external network needs to be configured, internal network is empty
     */
    public static String videoServerIP = null;
    public static String relayIP = null;
    public static String credential = null;
    public static String relayUsername = null;
    public static String relayPassword = null;

    /** 车辆类型 */
    public static List<OrbitVehicleType> vehicleTypeList = null;

    /**
     * 根据车辆类型代码获取类型
     *
     * @param code
     * @return
     */
    public static OrbitVehicleType getOrbitVehicleType(String code) {
        for (OrbitVehicleType orbitVehicleType : vehicleTypeList) {
            if (code.equals(orbitVehicleType.getCode())) {
                return orbitVehicleType;
            }
        }
        return new OrbitVehicleType();
    }


    //*********用户操作日志常量************//
    /** 添加数据 **/
    public static final String ACTION_ADD = "100";
    /** 修改数据 **/
    public static final String ACTION_UPDATE = "101";
    /** 删除数据 **/
    public static final String ACTION_DELETE = "102";
    /** 登录系统 */
    public static final String ACTION_LOGIN = "103";
    /** 退出登录 */
    public static final String ACTION_LOGOUT = "104";
    /** 终止任务 */
    public static final String ACTION_OVER = "105";
    /** 开启黑名单布控 */
    public static final String ACTION_OPEN_CONTROL = "106";
    /** 关闭黑名单布控 */
    public static final String ACTION_CLOSE_CONTROL = "107";

    /** 设备管理数据 **/
    public static final String DATA_DEVICE = "200";
    /** 用户管理数据 **/
    public static final String DATA_USER = "201";
    /** 系统参数数据 **/
    public static final String DATA_SYSVARIABLE = "202";
    /** 系统参数里面的报警铃声，需要单独处理，但是返回前台时不展示该code，仍然展示202 */
    public static final String DATA_ALARM_VOICE = "2021";
    /** 行政区划管理数据 **/
    // public static final String DATA_AREA = "203";
    /** 机构数据 **/
    public static final String DATA_MONITOR_CENTER = "204";
    /** 角色管理数据 **/
    public static final String DATA_ROLE = "205";
    /** 黑名单数据 **/
    public static final String DATA_BLACKLIST = "206";
    /** 白名单数据 **/
    public static final String DATA_WHITELIST = "207";
    /** 车辆布控数据 **/
    public static final String DATA_CONTROL = "208";
    /** 系统登录数据 */
    public static final String DATA_LOGIN = "209";
    /** 黑名单类型数据 */
    public static final String DATA_BLACKLIST_TYPE = "210";
    /** 白名单类型数据 */
    public static final String DATA_WHITELIST_TYPE = "211";
    /** 城市数据 */
    public static final String DATA_CITY = "212";
    /** 区域数据 */
    public static final String DATA_AREA = "213";
    /** 路口数据 */
    public static final String DATA_ROAD = "214";


    public static final String SUPER_USERNAME = "admin";
}

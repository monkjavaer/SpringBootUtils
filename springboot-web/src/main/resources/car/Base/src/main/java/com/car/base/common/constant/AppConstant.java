package com.car.base.common.constant;


import java.util.ArrayList;
import java.util.List;

/**
 * 系统不变Constant
 */
public class AppConstant {

    /**
     * 白名单redis key
     */
    public static final String WHITELIST_REDIS_KEY = "whiteLists";
    /**
     * 黑名单redis key
     */
    public static final String BLACKLIST_REDIS_KEY = "blackLists";

    /**
     * 设备redis key
     */
    public static final String DEVICE_REDIS_KEY = "deviceLists";

    /**
     * websocket
     */
    public static final String WEBSOCKET_USERNAME = "websocket.username";
    public static final String WEBSOCKET_CONTEXT = "websocket.request.context";

    /**
     * session key
     */
    public static final String SESSION_USER_KEY = "session_user_key";

    public static final List<String> FILE_TYPE = new ArrayList<String>();

    public static final List<String> URL_LIST = new ArrayList<String>();

    static {
        FILE_TYPE.add(".js");
        FILE_TYPE.add(".css");
        FILE_TYPE.add(".html");

        FILE_TYPE.add(".jpg");
        FILE_TYPE.add(".png");
        FILE_TYPE.add(".gif");

        FILE_TYPE.add(".eot");
        FILE_TYPE.add(".svg");
        FILE_TYPE.add(".ttf");
        FILE_TYPE.add(".woff");
        FILE_TYPE.add(".mp3");
    }

    static {
        URL_LIST.add("/");

        URL_LIST.add("/alarm/getAlarmInfo");
        URL_LIST.add("/control/addBlackVehicle");
        URL_LIST.add("/control/removeBlackVehicle");
        URL_LIST.add("/control/modifyBlackVehicle");

        URL_LIST.add("/test");
        URL_LIST.add("/alarm/unifiedalarm");
        URL_LIST.add("/alarm/getAlarmStatus");
        URL_LIST.add("/alarm/redis");
        URL_LIST.add("/alarm/del");
    }
}

/**
 * Copyright(C) 2017 Car All rights reserved.
 * Original Author: monkjavaer, 2017/11/10
 */
package com.car.base.common.constant;

import com.car.base.common.utilities.PropertiesUtil;

/**
 *
 */
public class EnvConstant {

    //消息队列
    public static String NODE_PASS_QUEUE_NAME = "node-pass-ang";
    public static String PASS_DETEC_QUEUE_NAME = "pass-detec-ang";
    //PASSDETEC_WEB_QUEUE_NAME he SYSTEM_WEB_QUEUE_NAME 为广播模式
    public static String PASSDETEC_WEB_QUEUE_NAME   = "passdetec-web-ang";
    public static String SYSTEM_WEB_QUEUE_NAME   = "system-web-ang";
    public static String HOST   = "";
    //套牌车 Redis过期时间设置 秒 5天 = 5*24*60*60 = 432000
    public static Integer FAKE_PLATE_TIME = 432000;

    public static Integer MESSAGE_QUEUE_PORT = 5672;
    public static String MESSAGE_QUEUE_USERNAME = "";
    public static String MESSAGE_QUEUE_PASSWORD = "";
    public static String TIME_LIMIT = "";
    /**
     * 超级管理员
     */
    public static String SUPERADMIN = "";

    //Kafka信息队列
//    public static String METADATA_BROKER_LIST = "172.16.3.31:6667,172.16.3.32:6667,172.16.3.33:6667";
    public static String METADATA_BROKER_LIST = "117.121.48.84:6667,117.121.48.84:6668,117.121.48.84:6669";
    public static String REQUEST_REQUIRED_ACKS = "1";
    public static String KAFKA_TOPIC_PASSEDBYVEHICLE = "passedby_vehicle";


    /**
     * 从配置文件读取参数
     */
    static {
        PropertiesUtil propertiesUtil = new PropertiesUtil("/base.properties");
        HOST = propertiesUtil.getPropertieValue("MESSAGE_QUEUE_HOST");
        MESSAGE_QUEUE_USERNAME = propertiesUtil.getPropertieValue("MESSAGE_QUEUE_USERNAME");
        MESSAGE_QUEUE_PASSWORD = propertiesUtil.getPropertieValue("MESSAGE_QUEUE_PASSWORD");
        FAKE_PLATE_TIME = Integer.valueOf(propertiesUtil.getPropertieValue("FAKE_PLATE_TIME"));
        TIME_LIMIT = propertiesUtil.getPropertieValue("TIME_LIMIT");
        MESSAGE_QUEUE_PORT = Integer.valueOf(propertiesUtil.getPropertieValue("MESSAGE_QUEUE_PORT"));
        METADATA_BROKER_LIST = propertiesUtil.getPropertieValue("metadata.broker.list");
        REQUEST_REQUIRED_ACKS = propertiesUtil.getPropertieValue("request.required.acks");
        KAFKA_TOPIC_PASSEDBYVEHICLE = propertiesUtil.getPropertieValue("kafka.topic.passedbyvehicle");
    }

}

package com.base.springboot.car.NodeService.src.main.java.com.car.netty.enums;


public class UnvEnum
{
    /**
     * 宇视报文头
     */
    public static final int UNV_HEADER = 0x77aa77aa;
    /**
     * 宇视报文尾
     */
    public static final int UNV_LAST = 0x77ab77ab;
    /**
     * 队列大小
     */
    public static final int UNV_QUEUE = 500;
    /**
     * 查询字段语句
     */
    public static final String DAO_TBL_VEHICLE_RECORD = " record_id,device_code,plate_code,pass_time, update_time,record_data,trans_flag ";
    /**
     * 查询违法字段语句
     */
    public static final String DAO_TBL_ALARM_DATA = "alarm_id,device_code,plate_code,pass_time,update_time,alarm_data,trans_flag,drive_status";
    /**
     * 查询RFID字段语句
     */
    public static final String DAO_TBL_RFID_DATA = "record_id,dev_id,tollgate_id,pass_time,record_data,trans_flag";
    /**
     * 命令码
     */
    public static final String UNV_CMD = "ICmd";

    /**
     * 异常信息
     */
    public static final String UNV_ERROR = "异常信息:";

    /**
     * 调试信息
     */
    public static final String UNV_DEBUG = "调试信息:";

    /**
     * 正常信息
     */
    public static final String UNV_INFO = "正常信息:";

    /**
     * 过车目录
     */
    public static final String UNV_PASS = "pass";

    /**
     * 违法目录
     */
    public static final String UNV_ALARM = "alarm";

    /**
     * 命令码
     */
    public static final int CONNECT_SIGNAL = 100;



}

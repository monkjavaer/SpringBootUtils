package com.car.orbit.orbitservice.bo;

import java.util.List;

/**
 * @Title: UserBO
 * @Package: com.car.orbit.orbitservice.bo
 * @Description:
 * @Author: monkjavaer
 * @Date: 2019/03/09 16:46
 * @Version: V1.0
 */
public class UserBO {

    /**
     * 头像
     */
    private String avatar;
    /**
     * 用户名
     */
    private String username;
    /**
     * 姓名
     */
    private String name;
    /**
     * 用户id
     */
    private String _id; // 前端要求该字段以下划线开头
    /**
     * 角色相关信息
     */
    private RoleBO role;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 地图配置
     */
    private MapConfig mapConfig;
    /**
     * Socket IP
     */
    private String socketIP;

    /**
     * 未处置警情
     */
    private Integer alarmNumber;
    /**
     * 机构id
     */
    private String monitorCenterId;
    /**
     * 类型映射
     */
    private List<VehicleTypeMappingBO> vehicleTypeArr;
    /**
     * 颜色映射
     */
    private List<ColorMappingBO> plateColorArr;

    public UserBO() {
        role = new RoleBO();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public RoleBO getRole() {
        return role;
    }

    public void setRole(RoleBO role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public MapConfig getMapConfig() {
        return mapConfig;
    }

    public void setMapConfig(MapConfig mapConfig) {
        this.mapConfig = mapConfig;
    }

    public String getSocketIP() {
        return socketIP;
    }

    public void setSocketIP(String socketIP) {
        this.socketIP = socketIP;
    }

    public Integer getAlarmNumber() {
        return alarmNumber;
    }

    public void setAlarmNumber(Integer alarmNumber) {
        this.alarmNumber = alarmNumber;
    }

    public String getMonitorCenterId() {
        return monitorCenterId;
    }

    public void setMonitorCenterId(String monitorCenterId) {
        this.monitorCenterId = monitorCenterId;
    }

    public List<VehicleTypeMappingBO> getVehicleTypeArr() {
        return vehicleTypeArr;
    }

    public void setVehicleTypeArr(List<VehicleTypeMappingBO> vehicleTypeArr) {
        this.vehicleTypeArr = vehicleTypeArr;
    }

    public List<ColorMappingBO> getPlateColorArr() {
        return plateColorArr;
    }

    public void setPlateColorArr(List<ColorMappingBO> plateColorArr) {
        this.plateColorArr = plateColorArr;
    }
}

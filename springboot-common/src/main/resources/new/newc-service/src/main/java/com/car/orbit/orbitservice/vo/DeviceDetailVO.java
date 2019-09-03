package com.car.orbit.orbitservice.vo;

/**
 * @Title: DeviceDetailVO
 * @Package: com.car.orbit.orbitservice.vo
 * @Description: 设备详细信息
 * @Author: monkjavaer
 * @Data: 2019/3/8 14:16
 * @Version: V1.0
 */
public class DeviceDetailVO {
    /**
     * 设备主键
     */
    private String id;
    /**
     * 设备名
     */
    private String name;
    /**
     * 设备类型code
     */
    private Integer type;
    /**
     * 设备类型名
     */
    private String typeName;
    /**
     * 设备在线状态
     */
    private Integer online;
    /**
     * cityName
     */
    private String cityName;
    /**
     *areaName
     */
    private String areaName;
    /**
     *roadName
     */
    private String roadName;
    /**
     *cityId
     */
    private String cityId;
    /**
     *areaId
     */
    private String areaId;
    /**
     *roadCrossPointId
     */
    private String roadCrossPointId;
    /**
     * 安装详细地址
     */
    private String installAddress;
    /**
     * 厂商code
     */
    private Integer manufacturer;
    /**
     * 厂商名
     */
    private String manufacturerName;

    /**
     * 经度
     */
    private Double latitude;
    /**
     * 纬度
     */
    private Double longitude;
    /**
     * 设备ip
     */
    private String ip;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 设备登录名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getRoadCrossPointId() {
        return roadCrossPointId;
    }

    public void setRoadCrossPointId(String roadCrossPointId) {
        this.roadCrossPointId = roadCrossPointId;
    }

    public String getInstallAddress() {
        return installAddress;
    }

    public void setInstallAddress(String installAddress) {
        this.installAddress = installAddress;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Integer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }
}

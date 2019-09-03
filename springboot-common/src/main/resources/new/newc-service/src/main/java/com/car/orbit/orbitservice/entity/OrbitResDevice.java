package com.car.orbit.orbitservice.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "orbit_res_device")
public class OrbitResDevice {
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 名字
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 设备类型code
     */
    @Column(name = "TYPE")
    private Integer type;

    /**
     * 经度
     */
    @Column(name = "LATITUDE")
    private Double latitude;

    /**
     * 纬度
     */
    @Column(name = "LONGITUDE")
    private Double longitude;

    /**
     * 是否在线（(1:不在线; 2:在线)
     */
    @Column(name = "ONLINE")
    private Integer online;

    /**
     * 所属厂商主键code
     */
    @Column(name = "MANUFACTURER")
    private Integer manufacturer;

    /**
     * 域编码
     */
    @Column(name = "DOMAIN_CODE")
    private Integer domainCode;

    /**
     * 设备安装详细地址
     */
    @Column(name = "INSTALL_ADDRESS")
    private String installAddress;

    /**
     * 智能主机ID
     */
    @Column(name = "TERMINAL_ID")
    private String terminalId;

    /**
     * 路口点位ID
     */
    @Column(name = "ROAD_CROSS_POINT_ID")
    private String roadCrossPointId;

    /**
     * 监控的车道数量
     */
    @Column(name = "ROADWAY_NUM")
    private Integer roadwayNum;

    /**
     * 设备更新时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 设备相机ip
     */
    @Column(name = "IP")
    private String ip;

    /**
     * 设备端口
     */
    @Column(name = "PORT")
    private Integer port;

    /**
     * 设备登录名
     */
    @Column(name = "USERNAME")
    private String username;

    /**
     * 登录密码
     */
    @Column(name = "PASSWORD")
    private String password;

    /**
     * 是否已删除
     */
    @Column(name = "DELETED")
    private Integer deleted;

    /**
     * 添加设备后视频监控平台返回ID
     */
    @Column(name = "VIDEO_DEVICE_ID")
    private String videoDeviceId;


    public String getVideoDeviceId() {
        return videoDeviceId;
    }

    public void setVideoDeviceId(String videoDeviceId) {
        this.videoDeviceId = videoDeviceId;
    }

    /**
     * 获取主键
     *
     * @return ID - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取名字
     *
     * @return NAME - 名字
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名字
     *
     * @param name 名字
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取类型(1:电警摄像机；2卡口摄像机； 3: 普通摄像机,4智能主机)
     *
     * @return TYPE - 类型(1:电警摄像机；2卡口摄像机； 3: 普通摄像机,4智能主机)
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型(1:电警摄像机；2卡口摄像机； 3: 普通摄像机,4智能主机)
     *
     * @param type 类型(1:电警摄像机；2卡口摄像机； 3: 普通摄像机,4智能主机)
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取经度
     *
     * @return LATITUDE - 经度
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * 设置经度
     *
     * @param latitude 经度
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * 获取纬度
     *
     * @return LONGITUDE - 纬度
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * 设置纬度
     *
     * @param longitude 纬度
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * 获取是否在线（(0:不在线; 1:在线)
     *
     * @return ONLINE - 是否在线（(0:不在线; 1:在线)
     */
    public Integer getOnline() {
        return online;
    }

    /**
     * 设置是否在线（(0:不在线; 1:在线)
     *
     * @param online 是否在线（(0:不在线; 1:在线)
     */
    public void setOnline(Integer online) {
        this.online = online;
    }

    /**
     * 获取所属厂商
     *
     * @return MANUFACTURER - 所属厂商
     */
    public Integer getManufacturer() {
        return manufacturer;
    }

    /**
     * 设置所属厂商
     *
     * @param manufacturer 所属厂商
     */
    public void setManufacturer(Integer manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * 获取域编码
     *
     * @return DOMAIN_CODE - 域编码
     */
    public Integer getDomainCode() {
        return domainCode;
    }

    /**
     * 设置域编码
     *
     * @param domainCode 域编码
     */
    public void setDomainCode(Integer domainCode) {
        this.domainCode = domainCode;
    }

    /**
     * 获取设备安装详细地址
     *
     * @return INSTALL_ADDRESS - 设备安装详细地址
     */
    public String getInstallAddress() {
        return installAddress;
    }

    /**
     * 设置设备安装详细地址
     *
     * @param installAddress 设备安装详细地址
     */
    public void setInstallAddress(String installAddress) {
        this.installAddress = installAddress;
    }

    /**
     * 获取智能主机ID
     *
     * @return TERMINAL_ID - 智能主机ID
     */
    public String getTerminalId() {
        return terminalId;
    }

    /**
     * 设置智能主机ID
     *
     * @param terminalId 智能主机ID
     */
    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    /**
     * 获取路口点位ID
     *
     * @return ROAD_CROSS_POINT_ID - 路口点位ID
     */
    public String getRoadCrossPointId() {
        return roadCrossPointId;
    }

    /**
     * 设置路口点位ID
     *
     * @param roadCrossPointId 路口点位ID
     */
    public void setRoadCrossPointId(String roadCrossPointId) {
        this.roadCrossPointId = roadCrossPointId;
    }

    /**
     * 获取监控的车道数量
     *
     * @return ROADWAY_NUM - 监控的车道数量
     */
    public Integer getRoadwayNum() {
        return roadwayNum;
    }

    /**
     * 设置监控的车道数量
     *
     * @param roadwayNum 监控的车道数量
     */
    public void setRoadwayNum(Integer roadwayNum) {
        this.roadwayNum = roadwayNum;
    }

    /**
     * 获取设备更新时间
     *
     * @return UPDATE_TIME - 设备更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置设备更新时间
     *
     * @param updateTime 设备更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取创建时间
     *
     * @return CREATE_TIME - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取设备相机ip
     *
     * @return IP - 设备相机ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置设备相机ip
     *
     * @param ip 设备相机ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取设备端口
     *
     * @return PORT - 设备端口
     */
    public Integer getPort() {
        return port;
    }

    /**
     * 设置设备端口
     *
     * @param port 设备端口
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * 获取设备登录名
     *
     * @return USERNAME - 设备登录名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置设备登录名
     *
     * @param username 设备登录名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取登录密码
     *
     * @return PASSWORD - 登录密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置登录密码
     *
     * @param password 登录密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取是否已删除
     *
     * @return DELETED - 是否已删除
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * 设置是否已删除
     *
     * @param deleted 是否已删除
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
package com.base.springboot.car.Web.src.main.java.com.car.trunk.controller.vo;

import java.math.BigInteger;
import java.util.List;

/**
 * Description:修改，删除智能主机接收参数
 * Created by monkjavaer on 2017/12/7 0007.
 */
public class DeviceTerminalVO {
    private BigInteger id;
    private String name;
    private Integer type;
    private BigInteger roadCrossPointId;
    private BigInteger terminalId;
    private String installAddress;
    private Double latitude;
    private Double longitude;
    private String ip;
    private Integer port;
    private Integer deviceNum;
    private List<BigInteger> monitorCenterIds;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getRoadCrossPointId() {
        return roadCrossPointId;
    }

    public void setRoadCrossPointId(BigInteger roadCrossPointId) {
        this.roadCrossPointId = roadCrossPointId;
    }

    public BigInteger getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(BigInteger terminalId) {
        this.terminalId = terminalId;
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

    public List<BigInteger> getMonitorCenterIds() {
        return monitorCenterIds;
    }

    public void setMonitorCenterIds(List<BigInteger> monitorCenterIds) {
        this.monitorCenterIds = monitorCenterIds;
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

    public Integer getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Integer deviceNum) {
        this.deviceNum = deviceNum;
    }
}

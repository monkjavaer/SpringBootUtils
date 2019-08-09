package com.car.trunk.dal.device.bo;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by monkjavaer on 2017/12/7 0007.
 */
public class DeviceBO {
    private BigInteger id;
    private String name;
    private Integer type;
    private Byte online;
    private String cityName;
    private String areaName;
    private String roadName;
    private BigInteger monitorCenterId;
    private BigInteger terminalId;
    private String installAddress;
    private BigInteger cityId;
    private BigInteger areaId;
    private BigInteger roadCrossPointId;
    private Double latitude;
    private Double longitude;
    List<BigInteger> ableMonitoridList;
    private String ip;
    private Integer port;
    private Integer deviceNum;

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

    public List<BigInteger> getAbleMonitoridList() {
        return ableMonitoridList;
    }

    public void setAbleMonitoridList(List<BigInteger> ableMonitoridList) {
        this.ableMonitoridList = ableMonitoridList;
    }

    public BigInteger getCityId() {
        return cityId;
    }

    public void setCityId(BigInteger cityId) {
        this.cityId = cityId;
    }

    public BigInteger getAreaId() {
        return areaId;
    }

    public void setAreaId(BigInteger areaId) {
        this.areaId = areaId;
    }

    public BigInteger getRoadCrossPointId() {
        return roadCrossPointId;
    }

    public void setRoadCrossPointId(BigInteger roadCrossPointId) {
        this.roadCrossPointId = roadCrossPointId;
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

    public String getInstallAddress() {
        return installAddress;
    }

    public void setInstallAddress(String installAddress) {
        this.installAddress = installAddress;
    }

    public BigInteger getMonitorCenterId() {
        return monitorCenterId;
    }

    public void setMonitorCenterId(BigInteger monitorCenterId) {
        this.monitorCenterId = monitorCenterId;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
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

    public Byte getOnline() {
        return online;
    }

    public void setOnline(Byte online) {
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

    public BigInteger getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(BigInteger terminalId) {
        this.terminalId = terminalId;
    }
}

package com.car.orbit.orbitservice.qo;

import com.car.orbit.orbitutil.page.PageParentVO;

import java.util.List;

/**
 * @Title: SearchByPictureConditionQO
 * @Package: com.car.orbit.orbitservice.qo
 * @Description: 以图搜车分页查询
 * @Author: zks
 * @Date: 2019/03/19 09:43
 * @Version: V1.0
 */
public class SearchByPictureQO extends PageParentVO {
    public static int all = 0;
    public static int point = 1;
    public static int brand = 2;
    /**
     * 查询标识
     */
    private String searchKey;
    /**
     * 车辆图片url
     */
    private String picUrl;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 设备编号
     */
    private List<String> deviceId;

    /**
     * 车辆品牌
     */
    private String brandCode;

    /**
     * 车辆子品牌
     */
    private String childBrandCode;
    /**
     * 聚合类型：0：ALL  1:按照卡口点位聚合   2：按照车辆品牌聚合
     */
    private int groupType;
    /**
     * 是否开启一车一档
     */
    private boolean oneFile;
    /**
     * 要打开的聚合点位ID
     */
    private String openPointId;
    /**
     * 要打开的聚合品牌子类型编码
     */
    private String openBrandChildCode;

    /**
     * 要打开的一车一档的VID
     */
    private String openVid;
    /**
     * 图片特征值
     */
    private float[] featureList;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<String> getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(List<String> deviceId) {
        this.deviceId = deviceId;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getChildBrandCode() {
        return childBrandCode;
    }

    public void setChildBrandCode(String childBrandCode) {
        this.childBrandCode = childBrandCode;
    }

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    public boolean isOneFile() {
        return oneFile;
    }

    public void setOneFile(boolean oneFile) {
        this.oneFile = oneFile;
    }

    public String getOpenPointId() {
        return openPointId;
    }

    public void setOpenPointId(String openPointId) {
        this.openPointId = openPointId;
    }

    public String getOpenBrandChildCode() {
        return openBrandChildCode;
    }

    public void setOpenBrandChildCode(String openBrandChildCode) {
        this.openBrandChildCode = openBrandChildCode;
    }

    public String getOpenVid() {
        return openVid;
    }

    public void setOpenVid(String openVid) {
        this.openVid = openVid;
    }

    public float[] getFeatureList() {
        return featureList;
    }

    public void setFeatureList(float[] featureList) {
        this.featureList = featureList;
    }
}

package com.car.orbit.orbitservice.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * CreateDate: 2019-4-2 <br/>
 * Author: monkjavaer <br/>
 * Description: 布控任务匹配内
 * Version: 1.0
 **/
public class ControlTaskDetailMatcher {

    /**
     * 设备id列表
     */
    private List<String> deviceIdList = new ArrayList<>();
    private Set<String> deviceIdSet = new HashSet<>();

    /**
     * 布控类型，1-精确匹配，2-模糊匹配
     */
    private Integer type;
    /**
     * 车牌号码
     */
    private String plateNumber;

    /**
     * 车牌颜色
     */
    private String plateColor;

    /**
     * 品牌id
     */
    private String brand;

    /**
     * 子品牌id
     */
    private String childBrand;

    /**
     * 车身颜色id
     */
    private String vehicleColor;

    /**
     * 车辆类型id
     */
    private String vehicleType;

    public ControlTaskDetailMatcher(Integer type, List<String> deviceIdList) {
        this.type = type;
        setDeviceIdList(deviceIdList);
    }

    public ControlTaskDetailMatcher() {

    }

    public int getType() {
        return type;
    }

    protected void setType(int type) {
        this.type = type;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    /**
     * @description 添加布控的时候使用
     * @date: 2019-5-28 17:38
     * @author: monkjavaer
      * @param plateNumber
     * @return
     */
    public void addPlateNumber(String plateNumber){
        this.plateNumber = plateNumber;
        if (type == null){
            System.out.println("!!!!!!!!!!!!error, 请先设置type参数");
        }
        if (type == 1){
            this.plateNumber = plateNumber;
        }else{
            /** 验证车牌号，只支持“京AAB*123”或者“京AABD123”这类车牌，通配符为“*”*/
            String sepChar = ".*[`~!@%#$^&()=|{}':;',\\[\\].<>/?~！@#￥……&（）——|{}【】‘；：”“'。，、？]+.*";
            if(Pattern.matches(sepChar, plateNumber)){
                System.out.println("!!!!!!!!!!!!error, 输入不能包含*以外的特殊字符");
                return;
            }
            plateNumber = plateNumber.replaceAll("\\*", "\\.\\*");
            plateNumber = ".*" + plateNumber + ".*";
            this.plateNumber = plateNumber;
        }
    }


    public Set<String> getDeviceIdSet() {
        return deviceIdSet;
    }

    public void setDeviceIdSet(Set<String> deviceIdSet) {
        this.deviceIdSet = deviceIdSet;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    public List<String> getDeviceIdList() {
        return deviceIdList;
    }

    protected void setDeviceIdList(List<String> deviceIdList) {
        this.deviceIdList = deviceIdList;
        deviceIdSet = new HashSet<>(this.deviceIdList);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getChildBrand() {
        return childBrand;
    }

    public void setChildBrand(String childBrand) {
        this.childBrand = childBrand;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    /**
     * @description 模糊匹配车牌
     * @date: 2019-4-2 19:30
     * @author: monkjavaer
     * @param plateNumber,车牌号
     * @return
     */
    private boolean matchWildPlateNumber(String plateNumber){
        if (!StringUtils.isEmpty(this.plateNumber)){
            if (!StringUtils.isEmpty(plateNumber)) {
                return Pattern.matches(this.getPlateNumber(), plateNumber);
            }else{
                return  false;
            }
        }
        return true;
    }

    /**
     * @description 精确匹配车牌
     * @date: 2019-4-2 19:30
     * @author: monkjavaer
     * @param plateNumber,车牌号
     * @return
     */
    private boolean matchPlateNumber(String plateNumber){
        if (!StringUtils.isEmpty(this.plateNumber)){
            if (!StringUtils.isEmpty(plateNumber)) {
                return this.getPlateNumber().equals(plateNumber);
            }else{
                return  false;
            }
        }
        return true;
    }

    /**
     * @description 匹配车牌颜色
     * @date: 2019-4-2 19:30
     * @author: monkjavaer
     * @param plateColor,车牌颜色
     * @return
     */
    private boolean matchPlateColor(String plateColor){
        if (!StringUtils.isEmpty(this.plateColor)){
            if (!StringUtils.isEmpty(plateColor)) {
                return this.plateColor.equals(plateColor);
            }else{
                return  false;
            }
        }
        return true;
    }

    /**
     * @description 匹配设备id
     * @date: 2019-4-2 19:30
     * @author: monkjavaer
     * @param deviceId,设备id
     * @return
     */
    private boolean matchDeviceId(String deviceId){
        if (!this.deviceIdSet.isEmpty()){
            if (!StringUtils.isEmpty(deviceId)) {
                return this.deviceIdSet.contains(deviceId);
            }else{
                return  false;
            }
        }
        return true;
    }

    /**
     * @description 匹配品牌
     * @date: 2019-4-2 19:30
     * @author: monkjavaer
     * @param brand,品牌id
     * @return
     */
    private boolean matchBrand(String brand){
        if (!StringUtils.isEmpty(this.brand)){
            if (!StringUtils.isEmpty(brand)) {
                return this.brand.equals(brand);
            }else{
                return  false;
            }
        }
        return true;
    }

    /**
     * @description 匹配子品牌
     * @date: 2019-4-2 19:30
     * @author: monkjavaer
     * @param childBrand,子品牌id
     * @return
     */
    private boolean matchChildBrand(String childBrand){
        if (!StringUtils.isEmpty(this.childBrand)){
            if (!StringUtils.isEmpty(childBrand)) {
                return this.childBrand.equals(childBrand);
            }else{
                return  false;
            }
        }
        return true;
    }

    /**
     * @description 车辆颜色
     * @date: 2019-4-2 19:30
     * @author: monkjavaer
     * @param vehicleColor,车辆颜色id
     * @return
     */
    private boolean matchVehicleColor(String vehicleColor){
        if (!StringUtils.isEmpty(this.vehicleColor)){
            if (!StringUtils.isEmpty(vehicleColor)) {
                return this.vehicleColor.equals(vehicleColor);
            }else{
                return  false;
            }
        }
        return true;
    }

    /**
     * @description 车辆类型
     * @date: 2019-4-2 19:30
     * @author: monkjavaer
     * @param vehicleType,车辆类型id
     * @return
     */
    private boolean matchVehicleType(String vehicleType){
        if (!StringUtils.isEmpty(this.vehicleType)){
            if (!StringUtils.isEmpty(vehicleType)) {
                for(String str : this.vehicleType.split(",")){
                    if(vehicleType.equals(str)){
                        return true;
                    }
                }
                return false;
            }else{
                return  false;
            }
        }
        return true;
    }

    public boolean match(String plateNumber , String plateColor, String deviceId, String brand, String childBrand, String vehicleColor, String vehicleType) {
        //当没有匹配类型默认哪种类型或者直接不匹配？
        if(null == type ){
            return false;
        }
        /** 精确匹配 */
        if ( type == 1){
            return matchPlateNumber(plateNumber);
        } else if (  type == 2){
            /** 模糊匹配 */
            //匹配车牌
            if (!matchWildPlateNumber(plateNumber)){
                return false;
            }
            // 匹配车牌颜色
            if (!matchPlateColor(plateColor)){
                return false;
            }
            // 匹配设备id
            if (!matchDeviceId(deviceId)){
                return false;
            }
            // 匹配品牌
            if (!matchBrand(brand)){
                return false;
            }
            // 匹配子品牌
            if (!matchChildBrand(childBrand)){
                return false;
            }
            // 匹配车辆颜色
            if (!matchVehicleColor(vehicleColor)){
                return false;
            }
            // 匹配车辆类型
            if (!matchVehicleType(vehicleType)){
                return false;
            }
            return true;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ControlTaskDetailMatcher{" +
                "deviceIdList=" + deviceIdList +
                ", deviceIdSet=" + deviceIdSet +
                ", type=" + type +
                ", plateNumber='" + plateNumber + '\'' +
                ", plateColor='" + plateColor + '\'' +
                ", brand='" + brand + '\'' +
                ", childBrand='" + childBrand + '\'' +
                ", vehicleColor='" + vehicleColor + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                '}';
    }
}

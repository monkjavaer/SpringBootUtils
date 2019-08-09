package com.car.orbit.orbitservice.entity;

import javax.persistence.*;

@Table(name = "orbit_control_task_detail")
public class OrbitControlTaskDetail {
    /**
     * 任务id
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 任务ID
     */
    @Column(name = "TASK_ID")
    private String taskId;

    /**
     * 车牌颜色
     */
    @Column(name = "PLATE_COLOR")
    private String plateColor;

    /**
     * 车牌号
     */
    @Column(name = "PLATE_NUMBER")
    private String plateNumber;

    /**
     * 模糊的标识位（1-精确匹配，2-模糊匹配）
     */
    @Column(name = "FUZZY")
    private Integer fuzzy;

    /**
     * 车辆品牌
     */
    @Column(name = "VEHICLE_BRAND")
    private String vehicleBrand;
    @Transient
    private String vehicleBrandName;

    /**
     * 车辆子品牌
     */
    @Column(name = "VEHICLE_BRAND_CHILD")
    private String vehicleBrandChild;
    @Transient
    private String vehicleBrandChildName;

    /**
     * 车辆颜色
     */
    @Column(name = "VEHICLE_COLOR")
    private String vehicleColor;

    /**
     * 车辆类型
     */
    @Column(name = "VEHICLE_TYPE")
    private String vehicleType;

    /**
     * 获取任务id
     *
     * @return ID - 任务id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置任务id
     *
     * @param id 任务id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取任务ID
     *
     * @return TASK_ID - 任务ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 设置任务ID
     *
     * @param taskId 任务ID
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * 获取车牌号
     *
     * @return PLATE_COLOR - 车牌号
     */
    public String getPlateColor() {
        return plateColor;
    }

    /**
     * 设置车牌号
     *
     * @param plateColor 车牌号
     */
    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    /**
     * 获取车牌号
     *
     * @return PLATE_NUMBER - 车牌号
     */
    public String getPlateNumber() {
        return plateNumber;
    }

    /**
     * 设置车牌号
     *
     * @param plateNumber 车牌号
     */
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    /**
     * 获取模糊的标识位（1-精确匹配，2-模糊匹配）
     *
     * @return FUZZY - 模糊的标识位（1-精确匹配，2-模糊匹配）
     */
    public Integer getFuzzy() {
        return fuzzy;
    }

    /**
     * 设置模糊的标识位（1-精确匹配，2-模糊匹配）
     *
     * @param fuzzy 模糊的标识位（1-精确匹配，2-模糊匹配）
     */
    public void setFuzzy(Integer fuzzy) {
        this.fuzzy = fuzzy;
    }

    /**
     * 获取车辆品牌
     *
     * @return VEHICLE_BRAND - 车辆品牌
     */
    public String getVehicleBrand() {
        return vehicleBrand;
    }

    /**
     * 设置车辆品牌
     *
     * @param vehicleBrand 车辆品牌
     */
    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    /**
     * 获取车辆子品牌
     *
     * @return VEHICLE_BRAND_CHILD - 车辆子品牌
     */
    public String getVehicleBrandChild() {
        return vehicleBrandChild;
    }

    /**
     * 设置车辆子品牌
     *
     * @param vehicleBrandChild 车辆子品牌
     */
    public void setVehicleBrandChild(String vehicleBrandChild) {
        this.vehicleBrandChild = vehicleBrandChild;
    }

    /**
     * 获取车辆颜色
     *
     * @return VEHICLE_COLOR - 车辆颜色
     */
    public String getVehicleColor() {
        return vehicleColor;
    }

    /**
     * 设置车辆颜色
     *
     * @param vehicleColor 车辆颜色
     */
    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleBrandName() {
        return vehicleBrandName;
    }

    public void setVehicleBrandName(String vehicleBrandName) {
        this.vehicleBrandName = vehicleBrandName;
    }

    public String getVehicleBrandChildName() {
        return vehicleBrandChildName;
    }

    public void setVehicleBrandChildName(String vehicleBrandChildName) {
        this.vehicleBrandChildName = vehicleBrandChildName;
    }
}
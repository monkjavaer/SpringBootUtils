package com.car.orbit.orbitservice.bo;

/**
 * @Title: RolePermissionBO
 * @Package: com.car.orbit.orbitservice.bo
 * @Description:
 * @Author: monkjavaer
 * @Date: 2019/03/09 16:52
 * @Version: V1.0
 */
public class RolePermissionBO {

    /**
     * 车辆搜索
     */
    private PermissionBO vehicleSearch;
    /**
     * 以图搜图
     */
    private PermissionBO searchByPicture;
    /**
     * 过车查询
     */
    private PermissionBO carInquiry;
    /**
     * 首次入城
     */
    private PermissionBO firstCarInCity;
    /**
     * 同行车辆
     */
    private PermissionBO peerVehicle;
    /**
     * 昼伏夜出
     */
    private PermissionBO crouchingAtNight;
    /**
     * 夜间频出
     */
    private PermissionBO frequentAtNight;
    /**
     * 隐匿车辆
     */
    private PermissionBO hiddenVehicle;
    /**
     * 落脚点分析
     */
    private PermissionBO analysisOfTheFoothold;
    /**
     * 频繁过车
     */
    private PermissionBO frequentDriving;
    /**
     * 轨迹分析
     */
    private PermissionBO trajectoryAnalysis;
    /**
     * 套牌分析
     */
    private PermissionBO deckAnalysis;
    /**
     * 一车一档
     */
    private PermissionBO oneCarOneFile;
    /**
     * 车辆布控
     */
    private PermissionBO vehicleControl;
    /**
     * 黑名单管理
     */
    private PermissionBO blacklistManagement;
    /**
     * 违章管理
     */
    private PermissionBO violationManagement;
    /**
     * 特殊车辆管理
     */
    private PermissionBO specialVehicleManagement;
    /**
     * 实时预警
     */
    private PermissionBO realTimeAlarm;
    /**
     * 历史预警
     */
    private PermissionBO historyAlarm;
    /**
     * 布控审批
     */
    private PermissionBO controlApproval;
    /**
     * 用户管理
     */
    private PermissionBO userManagement;
    /**
     * 设备管理
     */
    private PermissionBO equipmentManagement;
    /**
     * 系统日志
     */
    private PermissionBO systemLog;
    /**
     * 系统参数
     */
    private PermissionBO systemParameters;
    /**
     * 行政区划管理
     */
    private PermissionBO divisionManagement;
    /**
     * 机构管理
     */
    private PermissionBO institutionalManagement;
    /**
     * 角色管理
     */
    private PermissionBO roleManagement;
    /**
     * 名单类型管理
     */
    private PermissionBO listTypeManagement;

    private PermissionBO videoManagement;

    public RolePermissionBO() {
        vehicleSearch = new PermissionBO();
        searchByPicture = new PermissionBO();
        carInquiry = new PermissionBO();
        firstCarInCity = new PermissionBO();
        peerVehicle = new PermissionBO();
        crouchingAtNight = new PermissionBO();
        frequentAtNight = new PermissionBO();
        hiddenVehicle = new PermissionBO();
        analysisOfTheFoothold = new PermissionBO();
        frequentDriving = new PermissionBO();
        trajectoryAnalysis = new PermissionBO();
        deckAnalysis = new PermissionBO();
        oneCarOneFile = new PermissionBO();
        vehicleControl = new PermissionBO();
        blacklistManagement = new PermissionBO();
        violationManagement = new PermissionBO();
        specialVehicleManagement = new PermissionBO();
        realTimeAlarm = new PermissionBO();
        historyAlarm = new PermissionBO();
        controlApproval = new PermissionBO();
        userManagement = new PermissionBO();
        equipmentManagement = new PermissionBO();
        systemLog = new PermissionBO();
        systemParameters = new PermissionBO();
        divisionManagement = new PermissionBO();
        institutionalManagement = new PermissionBO();
        roleManagement = new PermissionBO();
        listTypeManagement = new PermissionBO();
        videoManagement = new PermissionBO();
    }

    public PermissionBO getVehicleSearch() {
        return vehicleSearch;
    }

    public void setVehicleSearch(PermissionBO vehicleSearch) {
        this.vehicleSearch = vehicleSearch;
    }

    public PermissionBO getSearchByPicture() {
        return searchByPicture;
    }

    public void setSearchByPicture(PermissionBO searchByPicture) {
        this.searchByPicture = searchByPicture;
    }

    public PermissionBO getCarInquiry() {
        return carInquiry;
    }

    public void setCarInquiry(PermissionBO carInquiry) {
        this.carInquiry = carInquiry;
    }

    public PermissionBO getFirstCarInCity() {
        return firstCarInCity;
    }

    public void setFirstCarInCity(PermissionBO firstCarInCity) {
        this.firstCarInCity = firstCarInCity;
    }

    public PermissionBO getPeerVehicle() {
        return peerVehicle;
    }

    public void setPeerVehicle(PermissionBO peerVehicle) {
        this.peerVehicle = peerVehicle;
    }

    public PermissionBO getCrouchingAtNight() {
        return crouchingAtNight;
    }

    public void setCrouchingAtNight(PermissionBO crouchingAtNight) {
        this.crouchingAtNight = crouchingAtNight;
    }

    public PermissionBO getFrequentAtNight() {
        return frequentAtNight;
    }

    public void setFrequentAtNight(PermissionBO frequentAtNight) {
        this.frequentAtNight = frequentAtNight;
    }

    public PermissionBO getHiddenVehicle() {
        return hiddenVehicle;
    }

    public void setHiddenVehicle(PermissionBO hiddenVehicle) {
        this.hiddenVehicle = hiddenVehicle;
    }

    public PermissionBO getAnalysisOfTheFoothold() {
        return analysisOfTheFoothold;
    }

    public void setAnalysisOfTheFoothold(PermissionBO analysisOfTheFoothold) {
        this.analysisOfTheFoothold = analysisOfTheFoothold;
    }

    public PermissionBO getFrequentDriving() {
        return frequentDriving;
    }

    public void setFrequentDriving(PermissionBO frequentDriving) {
        this.frequentDriving = frequentDriving;
    }

    public PermissionBO getTrajectoryAnalysis() {
        return trajectoryAnalysis;
    }

    public void setTrajectoryAnalysis(PermissionBO trajectoryAnalysis) {
        this.trajectoryAnalysis = trajectoryAnalysis;
    }

    public PermissionBO getDeckAnalysis() {
        return deckAnalysis;
    }

    public void setDeckAnalysis(PermissionBO deckAnalysis) {
        this.deckAnalysis = deckAnalysis;
    }

    public PermissionBO getOneCarOneFile() {
        return oneCarOneFile;
    }

    public void setOneCarOneFile(PermissionBO oneCarOneFile) {
        this.oneCarOneFile = oneCarOneFile;
    }

    public PermissionBO getVehicleControl() {
        return vehicleControl;
    }

    public void setVehicleControl(PermissionBO vehicleControl) {
        this.vehicleControl = vehicleControl;
    }

    public PermissionBO getBlacklistManagement() {
        return blacklistManagement;
    }

    public void setBlacklistManagement(PermissionBO blacklistManagement) {
        this.blacklistManagement = blacklistManagement;
    }

    public PermissionBO getViolationManagement() {
        return violationManagement;
    }

    public void setViolationManagement(PermissionBO violationManagement) {
        this.violationManagement = violationManagement;
    }

    public PermissionBO getSpecialVehicleManagement() {
        return specialVehicleManagement;
    }

    public void setSpecialVehicleManagement(PermissionBO specialVehicleManagement) {
        this.specialVehicleManagement = specialVehicleManagement;
    }

    public PermissionBO getRealTimeAlarm() {
        return realTimeAlarm;
    }

    public void setRealTimeAlarm(PermissionBO realTimeAlarm) {
        this.realTimeAlarm = realTimeAlarm;
    }

    public PermissionBO getHistoryAlarm() {
        return historyAlarm;
    }

    public void setHistoryAlarm(PermissionBO historyAlarm) {
        this.historyAlarm = historyAlarm;
    }

    public PermissionBO getControlApproval() {
        return controlApproval;
    }

    public void setControlApproval(PermissionBO controlApproval) {
        this.controlApproval = controlApproval;
    }

    public PermissionBO getUserManagement() {
        return userManagement;
    }

    public void setUserManagement(PermissionBO userManagement) {
        this.userManagement = userManagement;
    }

    public PermissionBO getEquipmentManagement() {
        return equipmentManagement;
    }

    public void setEquipmentManagement(PermissionBO equipmentManagement) {
        this.equipmentManagement = equipmentManagement;
    }

    public PermissionBO getSystemLog() {
        return systemLog;
    }

    public void setSystemLog(PermissionBO systemLog) {
        this.systemLog = systemLog;
    }

    public PermissionBO getSystemParameters() {
        return systemParameters;
    }

    public void setSystemParameters(PermissionBO systemParameters) {
        this.systemParameters = systemParameters;
    }

    public PermissionBO getDivisionManagement() {
        return divisionManagement;
    }

    public void setDivisionManagement(PermissionBO divisionManagement) {
        this.divisionManagement = divisionManagement;
    }

    public PermissionBO getInstitutionalManagement() {
        return institutionalManagement;
    }

    public void setInstitutionalManagement(PermissionBO institutionalManagement) {
        this.institutionalManagement = institutionalManagement;
    }

    public PermissionBO getRoleManagement() {
        return roleManagement;
    }

    public void setRoleManagement(PermissionBO roleManagement) {
        this.roleManagement = roleManagement;
    }

    public PermissionBO getListTypeManagement() {
        return listTypeManagement;
    }

    public void setListTypeManagement(PermissionBO listTypeManagement) {
        this.listTypeManagement = listTypeManagement;
    }

    public PermissionBO getVideoManagement() {
        return videoManagement;
    }

    public void setVideoManagement(PermissionBO videoManagement) {
        this.videoManagement = videoManagement;
    }
}

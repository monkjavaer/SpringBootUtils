package com.base.springboot.car.NodeService.src.main.java.com.car.netty.struct.base;

/**
 * 过车信息
 *
 *
 */
public class VehicleInfo
{
    /**
     * 卡口相机编号
     */
    public String camID = "";
    /**
     * 设备编号
     */
    public String devID = "";
    /**
     * 采集类型
     */
    public String equipmentType = "";
    /**
     * 全景标志 00全景相机 01普通相机
     */
    public String panoramaFlag = "01";
    /**
     * 记录ID号
     */
    public String recordID = "";
    /**
     * 卡口编号
     */
    public String tollgateID = "";
    /**
     * 卡口名称
     */
    public String tollgateName = "";
    /**
     * 经过时刻
     */
    public String passTime = "";
    /**
     * 地点编号
     */
    public String placeCode = "";
    /**
     * 地点名称
     */
    public String placeName = "";
    /**
     * 车道编号
     */
    public String laneID = "0";
    /**
     * 车道类型
     */
    public String laneType = "";
    /**
     * 方向编号
     */
    public String direction = "0";
    /**
     * 方向名称
     */
    public String directionName = "";
    /**
     * 号牌号码
     */
    public String carPlate = "";
    /**
     * 号牌置信度
     */
    public Integer plateConfidence = 0;
    /**
     * 号牌种类
     */
    public String plateType = "99";
    /**
     * 号牌颜色 0 白色 1 黄色 2 蓝色 3 黑色 4 其他
     */
    public String plateColor = "4";
    /**
     * 号牌数量
     */
    public Integer plateNumber = 1;
    /**
     * 是否使用图像数据内容 1-使用；0-不使用；
     */
    public Integer isUseImageData = 0;
    /**
     * 号牌一致
     */
    public String plateCoincide = "";
    /**
     * 尾部号牌号码
     */
    public String rearVehiclePlateID = "";
    /**
     * 尾部号牌置信度
     */
    public Integer rearPlateConfidence = 0;
    /**
     * 尾部号牌颜色
     */
    public String rearPlateColor = "";
    /**
     * 尾部号牌种类
     */
    public String rearPlateType = "";
    /**
     * 图像数量
     */
    public Integer picNumber = 0;
    /**
     * 图像1索引
     */
    public Integer imageIndex1 = 1;
    /**
     * 图像1名称
     */
    public String imageURL1 = "";
    /**
     * 图像1数据内容
     */
    public String imageData1 = "";
    /**
     * 图像1类型
     */
    public Integer imageType1 = 0;
    /**
     * 图像1长
     */
    public Integer imageWidth1 = 0;
    /**
     * 图像1高
     */
    public Integer imageHeight1 = 0;
    /**
     * 图像2索引
     */
    public Integer imageIndex2 = 2;
    /**
     * 图像2名称url
     */
    public String imageURL2 = "";
    /**
     * 图像2数据内容
     */
    public String imageData2 = "";
    /**
     * 图像2类型
     */
    public Integer imageType2 = 0;
    /**
     * 图像2长
     */
    public Integer imageWidth2 = 0;
    /**
     * 图像2高
     */
    public Integer imageHeight2 = 0;
    /**
     * 图像2索引
     */
    public Integer imageIndex3 = 3;
    /**
     * 图像3名称URL
     */
    public String imageURL3 = "";
    /**
     * 图像3数据内容
     */
    public String imageData3 = "";
    /**
     * 图像3类型
     */
    public Integer imageType3 = 0;
    /**
     * 图像3长
     */
    public Integer imageWidth3 = 0;
    /**
     * 图像4高
     */
    public Integer imageHeight3 = 0;
    /**
     * 图像4索引
     */
    public Integer imageIndex4 = 4;
    /**
     * 图像4名称URL
     */
    public String imageURL4 = "";

    /**
     * 图像4数据内容
     */
    public String imageData4 = "";

    /**
     * 图像4类型
     */
    public Integer imageType4 = 0;
    /**
     * 图像4长
     */
    public Integer imageWidth4 = 0;

    /**
     * 图像4高
     */
    public Integer imageHeight4 = 0;
    /**
     * 图像索引(车牌图片)
     */
    public Integer imageIndex5 = 0;
    /**
     * 图像名称(车牌图片URL)
     */
    public String imageURL5 = "";
    /**
     * 图像名称(车牌图片数据内容)
     */
    public String imageData5 = "";
    /**
     * 图像类型(车牌图片)
     */
    public Integer imageType5 = 5;
    /**
     * 图像长(车牌图片)
     */
    public Integer imageWidth5 = 0;
    /**
     * 图像高(车牌图片)
     */
    public Integer imageHeight5 = 0;
    /**
     * 车辆速度
     */
    public Integer vehicleSpeed = 0;
    /**
     * 执法限速
     */
    public Integer limitedSpeed = 0;
    /**
     * 标识限速
     */
    public Integer markedSpeed = 0;
    /**
     * 行驶状态
     */
    public String driveStatus = "0";
    /**
     * 车辆品牌
     */
    public String vehicleBrand = "99";
    /**
     * 车辆外型
     */
    public String vehicleBody = "";
    /**
     * 车辆类型
     */
    public String vehicleType = "0";
    /**
     * 车外廓长
     */
    public Integer vehicleLength = 0;
    /**
     * 车身颜色 Z 其他 默认值
     */
    public String vehicleColor = "Z";
    /**
     * 车身颜色深浅 0 默认值其他
     */
    public String vehicleColorDept = "0";
    /**
     * 行人衣着颜色
     */
    public String dressColor = "";
    /**
     * 红灯时间
     */
    public Integer redLightTime = 0;

    /**
     * 红灯开始时间
     */
    public String redLightStartTime = "";

    /**
     * 红灯结束时间
     */
    public String redLightEndTime = "";

    /**
     * 处理标记
     */
    public String dealTag = "0";
    /**
     * 识别状态
     */
    public String identifyStatus = "0";
    /**
     * 识别时间
     */
    public Integer identifyTime = 0;

    /**
     * 车头识别品牌字段
     */
    public String faceVehicleBrand = "";

    /**
     * 车头识别品牌型号字段
     */
    public String faceVehicleBrandType = "";

    /**
     * 车头识别品牌年份字段
     */
    public String faceVehicleBrandYear = "";

    /**
     * 车头识别品牌展示字段
     */
    public String faceVehicleBrandModel = "";

    /**
     * 车头识别车头/车尾字段
     */
    public String faceIsVehicleHead= "";

    /**
     * 应用类型 0－车辆卡口 默认值 1－ 电警 2－ 人员卡口 3－ 泛卡口
     */
    public Integer applicationType = 0;
    /**
     * 全局合成标志 只针对违法 0－不需要合成 默认 1－自适应（图片数量2张及以上） 2－第1张提取特写（图片数量2～4张），合成后第1张原图丢弃
     * 3－第1张提取特写（图片数量1～3张），合成后第1张原图不丢弃
     */
    public Integer globalComposeFlag = 0;

    /**
     * 默认的命令码
     */
    public int iCmd = 119;


    /** Begin智能数据 */
    /**
     *年检标状态
     */
    public String aimStatus = "";
    /**
     *正驾驶遮阳板状态
     */
    public String driverSunVisorStatus = "";
    /**
     *副驾驶遮阳板状态
     */
    public String codriverSunVisorStatus = "";
    /**
     *正驾驶安全带状态
     */
    public String driverSeatBeltStatus = "";
    /**
     *副驾驶安全带状态
     */
    public String codriverSeatBeltStatus = "";
    /**
     *正驾驶开车打电话状态
     */
    public String driverMobileStatus = "";
    /**
     *危险品标志状态
     */
    public String dangerousGoodsMarkStatus = "";
    /**
     *黄标车标志状态
     */
    public String yellowPlateMarkStatus = "";
    /**
     *出租车标志状态
     */
    public String taxiMarkStatus = "";
    /**
     *天窗状态
     */
    public String scuttleStatus = "";
    /**
     *纸巾盒状态
     */
    public String napkinBoxStatus = "";
    /**
     *挂坠状态
     */
    public String pendantStatus = "";
    /**
     *半结构化数据对比块
     */
    public String featuresBlock = "";
    /**
     *半结构化数据
     */
    public String features = "";

    /** End 智能数据 */

    /** Begin GpsInfo*/
    /**
     *经度
     */
    public String longitude = "";
    /**
     *纬度
     */
    public String latitude = "";
    /**
     *海拔
     */
    public String altitude = "";
    /** End GpsInfo */

    public String videoURL = "";

    public String videoURL2 = "";

    /**
     * 车辆坐标（左上角x）
     */
    public String vehicleTopX = "";

    /**
     *车辆坐标（左上角y）
     */
    public String vehicleTopY = "";

    /**
     *车辆坐标（右下角x）
     */
    public String vehicleBotX = "";

    /**
     *车辆坐标（右下角y）
     */
    public String vehicleBotY = "";

    /**
     *车牌坐标（左上角x）
     */
    public String lPRRectTopX = "";

    /**
     *车牌坐标（左上角y）
     */
    public String lPRRectTopY = "";

    /**
     *车牌坐标（右下角x）
     */
    public String lPRRectBotX = "";

    /**
     *车牌坐标（右下角y）
     */
    public String lPRRectBotY = "";

    /**
     * 车牌小图
     */
    public String strPlatePic = "";

    public String vehicleFace = "";

    /**
     * 图像索引(车牌图片)
     */
    public Integer imageIndex6 = 0;
    /**
     * 图像名称(车牌图片URL)
     */
    public String imageURL6 = "";

    /**
     * 图像名称(车牌图片数据内容)
     */
    public String imageData6 = "";
    /**
     * 图像类型(车牌图片)
     */
    public Integer imageType6 = 0;
    /**
     * 图像长(车牌图片)
     */
    public Integer imageWidth6 = 0;
    /**
     * 图像高(车牌图片)
     */
    public Integer imageHeight6 = 0;

    /**
     * 图像索引(车牌图片)
     */
    public Integer imageIndex7 = 0;
    /**
     * 图像名称(车牌图片URL)
     */
    public String imageURL7 = "";
    /**
     * 图像名称(车牌图片数据内容)
     */
    public String imageData7 = "";
    /**
     * 图像类型(车牌图片)
     */
    public Integer imageType7 = 0;
    /**
     * 图像长(车牌图片)
     */
    public Integer imageWidth7 = 0;
    /**
     * 图像高(车牌图片)
     */
    public Integer imageHeight7 = 0;
    /**
     * 图像索引(车牌图片)
     */
    public Integer imageIndex8 = 0;
    /**
     * 图像名称(车牌图片URL)
     */
    public String imageURL8 = "";
    /**
     * 图像名称(车牌图片数据内容)
     */
    public String imageData8 = "";
    /**
     * 图像类型(车牌图片)
     */
    public Integer imageType8 = 0;
    /**
     * 图像长(车牌图片)
     */
    public Integer imageWidth8 = 0;
    /**
     * 图像高(车牌图片)
     */
    public Integer imageHeight8 = 0;

    /**
     * 图像索引(车牌图片)
     */
    public Integer imageIndex9 = 0;
    /**
     * 图像名称(车牌图片URL)
     */
    public String imageURL9 = "";
    /**
     * 图像名称(车牌图片数据内容)
     */
    public String imageData9 = "";
    /**
     * 图像类型(车牌图片)
     */
    public Integer imageType9 = 0;
    /**
     * 图像长(车牌图片)
     */
    public Integer imageWidth9 = 0;
    /**
     * 图像高(车牌图片)
     */
    public Integer imageHeight9 = 0;
    /**
     * 校对用户账号
     */
    public String correctUserId = "";
    /**
     * 校对时间
     */
    public String correctTime = "";

    /**
     * 车道排队长度
     */
    public String laneQueueLength = "";

    /**
     * 警员编号
     */
    public String policeCode = "";

    /**
     * 预留字段1
     */
    public String reservedField1 = "";

    /**
     * 预留字段2
     */
    public String reservedField2 = "";


    public String getCamID() {
        return camID;
    }

    public void setCamID(String camID) {
        this.camID = camID;
    }

    public String getDevID() {
        return devID;
    }

    public void setDevID(String devID) {
        this.devID = devID;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getPanoramaFlag() {
        return panoramaFlag;
    }

    public void setPanoramaFlag(String panoramaFlag) {
        this.panoramaFlag = panoramaFlag;
    }

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public String getTollgateID() {
        return tollgateID;
    }

    public void setTollgateID(String tollgateID) {
        this.tollgateID = tollgateID;
    }

    public String getTollgateName() {
        return tollgateName;
    }

    public void setTollgateName(String tollgateName) {
        this.tollgateName = tollgateName;
    }

    public String getPassTime() {
        return passTime;
    }

    public void setPassTime(String passTime) {
        this.passTime = passTime;
    }

    public String getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(String placeCode) {
        this.placeCode = placeCode;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getLaneID() {
        return laneID;
    }

    public void setLaneID(String laneID) {
        this.laneID = laneID;
    }

    public String getLaneType() {
        return laneType;
    }

    public void setLaneType(String laneType) {
        this.laneType = laneType;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirectionName() {
        return directionName;
    }

    public void setDirectionName(String directionName) {
        this.directionName = directionName;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public Integer getPlateConfidence() {
        return plateConfidence;
    }

    public void setPlateConfidence(Integer plateConfidence) {
        this.plateConfidence = plateConfidence;
    }

    public String getPlateType() {
        return plateType;
    }

    public void setPlateType(String plateType) {
        this.plateType = plateType;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    public Integer getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(Integer plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Integer getIsUseImageData() {
        return isUseImageData;
    }

    public void setIsUseImageData(Integer isUseImageData) {
        this.isUseImageData = isUseImageData;
    }

    public String getPlateCoincide() {
        return plateCoincide;
    }

    public void setPlateCoincide(String plateCoincide) {
        this.plateCoincide = plateCoincide;
    }

    public String getRearVehiclePlateID() {
        return rearVehiclePlateID;
    }

    public void setRearVehiclePlateID(String rearVehiclePlateID) {
        this.rearVehiclePlateID = rearVehiclePlateID;
    }

    public Integer getRearPlateConfidence() {
        return rearPlateConfidence;
    }

    public void setRearPlateConfidence(Integer rearPlateConfidence) {
        this.rearPlateConfidence = rearPlateConfidence;
    }

    public String getRearPlateColor() {
        return rearPlateColor;
    }

    public void setRearPlateColor(String rearPlateColor) {
        this.rearPlateColor = rearPlateColor;
    }

    public String getRearPlateType() {
        return rearPlateType;
    }

    public void setRearPlateType(String rearPlateType) {
        this.rearPlateType = rearPlateType;
    }

    public Integer getPicNumber() {
        return picNumber;
    }

    public void setPicNumber(Integer picNumber) {
        this.picNumber = picNumber;
    }

    public Integer getImageIndex1() {
        return imageIndex1;
    }

    public void setImageIndex1(Integer imageIndex1) {
        this.imageIndex1 = imageIndex1;
    }

    public String getImageURL1() {
        return imageURL1;
    }

    public void setImageURL1(String imageURL1) {
        this.imageURL1 = imageURL1;
    }

    public String getImageData1() {
        return imageData1;
    }

    public void setImageData1(String imageData1) {
        this.imageData1 = imageData1;
    }

    public Integer getImageType1() {
        return imageType1;
    }

    public void setImageType1(Integer imageType1) {
        this.imageType1 = imageType1;
    }

    public Integer getImageWidth1() {
        return imageWidth1;
    }

    public void setImageWidth1(Integer imageWidth1) {
        this.imageWidth1 = imageWidth1;
    }

    public Integer getImageHeight1() {
        return imageHeight1;
    }

    public void setImageHeight1(Integer imageHeight1) {
        this.imageHeight1 = imageHeight1;
    }

    public Integer getImageIndex2() {
        return imageIndex2;
    }

    public void setImageIndex2(Integer imageIndex2) {
        this.imageIndex2 = imageIndex2;
    }

    public String getImageURL2() {
        return imageURL2;
    }

    public void setImageURL2(String imageURL2) {
        this.imageURL2 = imageURL2;
    }

    public String getImageData2() {
        return imageData2;
    }

    public void setImageData2(String imageData2) {
        this.imageData2 = imageData2;
    }

    public Integer getImageType2() {
        return imageType2;
    }

    public void setImageType2(Integer imageType2) {
        this.imageType2 = imageType2;
    }

    public Integer getImageWidth2() {
        return imageWidth2;
    }

    public void setImageWidth2(Integer imageWidth2) {
        this.imageWidth2 = imageWidth2;
    }

    public Integer getImageHeight2() {
        return imageHeight2;
    }

    public void setImageHeight2(Integer imageHeight2) {
        this.imageHeight2 = imageHeight2;
    }

    public Integer getImageIndex3() {
        return imageIndex3;
    }

    public void setImageIndex3(Integer imageIndex3) {
        this.imageIndex3 = imageIndex3;
    }

    public String getImageURL3() {
        return imageURL3;
    }

    public void setImageURL3(String imageURL3) {
        this.imageURL3 = imageURL3;
    }

    public String getImageData3() {
        return imageData3;
    }

    public void setImageData3(String imageData3) {
        this.imageData3 = imageData3;
    }

    public Integer getImageType3() {
        return imageType3;
    }

    public void setImageType3(Integer imageType3) {
        this.imageType3 = imageType3;
    }

    public Integer getImageWidth3() {
        return imageWidth3;
    }

    public void setImageWidth3(Integer imageWidth3) {
        this.imageWidth3 = imageWidth3;
    }

    public Integer getImageHeight3() {
        return imageHeight3;
    }

    public void setImageHeight3(Integer imageHeight3) {
        this.imageHeight3 = imageHeight3;
    }

    public Integer getImageIndex4() {
        return imageIndex4;
    }

    public void setImageIndex4(Integer imageIndex4) {
        this.imageIndex4 = imageIndex4;
    }

    public String getImageURL4() {
        return imageURL4;
    }

    public void setImageURL4(String imageURL4) {
        this.imageURL4 = imageURL4;
    }

    public String getImageData4() {
        return imageData4;
    }

    public void setImageData4(String imageData4) {
        this.imageData4 = imageData4;
    }

    public Integer getImageType4() {
        return imageType4;
    }

    public void setImageType4(Integer imageType4) {
        this.imageType4 = imageType4;
    }

    public Integer getImageWidth4() {
        return imageWidth4;
    }

    public void setImageWidth4(Integer imageWidth4) {
        this.imageWidth4 = imageWidth4;
    }

    public Integer getImageHeight4() {
        return imageHeight4;
    }

    public void setImageHeight4(Integer imageHeight4) {
        this.imageHeight4 = imageHeight4;
    }

    public Integer getImageIndex5() {
        return imageIndex5;
    }

    public void setImageIndex5(Integer imageIndex5) {
        this.imageIndex5 = imageIndex5;
    }

    public String getImageURL5() {
        return imageURL5;
    }

    public void setImageURL5(String imageURL5) {
        this.imageURL5 = imageURL5;
    }

    public String getImageData5() {
        return imageData5;
    }

    public void setImageData5(String imageData5) {
        this.imageData5 = imageData5;
    }

    public Integer getImageType5() {
        return imageType5;
    }

    public void setImageType5(Integer imageType5) {
        this.imageType5 = imageType5;
    }

    public Integer getImageWidth5() {
        return imageWidth5;
    }

    public void setImageWidth5(Integer imageWidth5) {
        this.imageWidth5 = imageWidth5;
    }

    public Integer getImageHeight5() {
        return imageHeight5;
    }

    public void setImageHeight5(Integer imageHeight5) {
        this.imageHeight5 = imageHeight5;
    }

    public Integer getVehicleSpeed() {
        return vehicleSpeed;
    }

    public void setVehicleSpeed(Integer vehicleSpeed) {
        this.vehicleSpeed = vehicleSpeed;
    }

    public Integer getLimitedSpeed() {
        return limitedSpeed;
    }

    public void setLimitedSpeed(Integer limitedSpeed) {
        this.limitedSpeed = limitedSpeed;
    }

    public Integer getMarkedSpeed() {
        return markedSpeed;
    }

    public void setMarkedSpeed(Integer markedSpeed) {
        this.markedSpeed = markedSpeed;
    }

    public String getDriveStatus() {
        return driveStatus;
    }

    public void setDriveStatus(String driveStatus) {
        this.driveStatus = driveStatus;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public String getVehicleBody() {
        return vehicleBody;
    }

    public void setVehicleBody(String vehicleBody) {
        this.vehicleBody = vehicleBody;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Integer getVehicleLength() {
        return vehicleLength;
    }

    public void setVehicleLength(Integer vehicleLength) {
        this.vehicleLength = vehicleLength;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getVehicleColorDept() {
        return vehicleColorDept;
    }

    public void setVehicleColorDept(String vehicleColorDept) {
        this.vehicleColorDept = vehicleColorDept;
    }

    public String getDressColor() {
        return dressColor;
    }

    public void setDressColor(String dressColor) {
        this.dressColor = dressColor;
    }

    public Integer getRedLightTime() {
        return redLightTime;
    }

    public void setRedLightTime(Integer redLightTime) {
        this.redLightTime = redLightTime;
    }

    public String getRedLightStartTime() {
        return redLightStartTime;
    }

    public void setRedLightStartTime(String redLightStartTime) {
        this.redLightStartTime = redLightStartTime;
    }

    public String getRedLightEndTime() {
        return redLightEndTime;
    }

    public void setRedLightEndTime(String redLightEndTime) {
        this.redLightEndTime = redLightEndTime;
    }

    public String getDealTag() {
        return dealTag;
    }

    public void setDealTag(String dealTag) {
        this.dealTag = dealTag;
    }

    public String getIdentifyStatus() {
        return identifyStatus;
    }

    public void setIdentifyStatus(String identifyStatus) {
        this.identifyStatus = identifyStatus;
    }

    public Integer getIdentifyTime() {
        return identifyTime;
    }

    public void setIdentifyTime(Integer identifyTime) {
        this.identifyTime = identifyTime;
    }

    public String getFaceVehicleBrand() {
        return faceVehicleBrand;
    }

    public void setFaceVehicleBrand(String faceVehicleBrand) {
        this.faceVehicleBrand = faceVehicleBrand;
    }

    public String getFaceVehicleBrandType() {
        return faceVehicleBrandType;
    }

    public void setFaceVehicleBrandType(String faceVehicleBrandType) {
        this.faceVehicleBrandType = faceVehicleBrandType;
    }

    public String getFaceVehicleBrandYear() {
        return faceVehicleBrandYear;
    }

    public void setFaceVehicleBrandYear(String faceVehicleBrandYear) {
        this.faceVehicleBrandYear = faceVehicleBrandYear;
    }

    public String getFaceVehicleBrandModel() {
        return faceVehicleBrandModel;
    }

    public void setFaceVehicleBrandModel(String faceVehicleBrandModel) {
        this.faceVehicleBrandModel = faceVehicleBrandModel;
    }

    public String getFaceIsVehicleHead() {
        return faceIsVehicleHead;
    }

    public void setFaceIsVehicleHead(String faceIsVehicleHead) {
        this.faceIsVehicleHead = faceIsVehicleHead;
    }

    public Integer getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(Integer applicationType) {
        this.applicationType = applicationType;
    }

    public Integer getGlobalComposeFlag() {
        return globalComposeFlag;
    }

    public void setGlobalComposeFlag(Integer globalComposeFlag) {
        this.globalComposeFlag = globalComposeFlag;
    }

    public int getiCmd() {
        return iCmd;
    }

    public void setiCmd(int iCmd) {
        this.iCmd = iCmd;
    }

    public String getAimStatus() {
        return aimStatus;
    }

    public void setAimStatus(String aimStatus) {
        this.aimStatus = aimStatus;
    }

    public String getDriverSunVisorStatus() {
        return driverSunVisorStatus;
    }

    public void setDriverSunVisorStatus(String driverSunVisorStatus) {
        this.driverSunVisorStatus = driverSunVisorStatus;
    }

    public String getCodriverSunVisorStatus() {
        return codriverSunVisorStatus;
    }

    public void setCodriverSunVisorStatus(String codriverSunVisorStatus) {
        this.codriverSunVisorStatus = codriverSunVisorStatus;
    }

    public String getDriverSeatBeltStatus() {
        return driverSeatBeltStatus;
    }

    public void setDriverSeatBeltStatus(String driverSeatBeltStatus) {
        this.driverSeatBeltStatus = driverSeatBeltStatus;
    }

    public String getCodriverSeatBeltStatus() {
        return codriverSeatBeltStatus;
    }

    public void setCodriverSeatBeltStatus(String codriverSeatBeltStatus) {
        this.codriverSeatBeltStatus = codriverSeatBeltStatus;
    }

    public String getDriverMobileStatus() {
        return driverMobileStatus;
    }

    public void setDriverMobileStatus(String driverMobileStatus) {
        this.driverMobileStatus = driverMobileStatus;
    }

    public String getDangerousGoodsMarkStatus() {
        return dangerousGoodsMarkStatus;
    }

    public void setDangerousGoodsMarkStatus(String dangerousGoodsMarkStatus) {
        this.dangerousGoodsMarkStatus = dangerousGoodsMarkStatus;
    }

    public String getYellowPlateMarkStatus() {
        return yellowPlateMarkStatus;
    }

    public void setYellowPlateMarkStatus(String yellowPlateMarkStatus) {
        this.yellowPlateMarkStatus = yellowPlateMarkStatus;
    }

    public String getTaxiMarkStatus() {
        return taxiMarkStatus;
    }

    public void setTaxiMarkStatus(String taxiMarkStatus) {
        this.taxiMarkStatus = taxiMarkStatus;
    }

    public String getScuttleStatus() {
        return scuttleStatus;
    }

    public void setScuttleStatus(String scuttleStatus) {
        this.scuttleStatus = scuttleStatus;
    }

    public String getNapkinBoxStatus() {
        return napkinBoxStatus;
    }

    public void setNapkinBoxStatus(String napkinBoxStatus) {
        this.napkinBoxStatus = napkinBoxStatus;
    }

    public String getPendantStatus() {
        return pendantStatus;
    }

    public void setPendantStatus(String pendantStatus) {
        this.pendantStatus = pendantStatus;
    }

    public String getFeaturesBlock() {
        return featuresBlock;
    }

    public void setFeaturesBlock(String featuresBlock) {
        this.featuresBlock = featuresBlock;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getVideoURL2() {
        return videoURL2;
    }

    public void setVideoURL2(String videoURL2) {
        this.videoURL2 = videoURL2;
    }

    public String getVehicleTopX() {
        return vehicleTopX;
    }

    public void setVehicleTopX(String vehicleTopX) {
        this.vehicleTopX = vehicleTopX;
    }

    public String getVehicleTopY() {
        return vehicleTopY;
    }

    public void setVehicleTopY(String vehicleTopY) {
        this.vehicleTopY = vehicleTopY;
    }

    public String getVehicleBotX() {
        return vehicleBotX;
    }

    public void setVehicleBotX(String vehicleBotX) {
        this.vehicleBotX = vehicleBotX;
    }

    public String getVehicleBotY() {
        return vehicleBotY;
    }

    public void setVehicleBotY(String vehicleBotY) {
        this.vehicleBotY = vehicleBotY;
    }

    public String getlPRRectTopX() {
        return lPRRectTopX;
    }

    public void setlPRRectTopX(String lPRRectTopX) {
        this.lPRRectTopX = lPRRectTopX;
    }

    public String getlPRRectTopY() {
        return lPRRectTopY;
    }

    public void setlPRRectTopY(String lPRRectTopY) {
        this.lPRRectTopY = lPRRectTopY;
    }

    public String getlPRRectBotX() {
        return lPRRectBotX;
    }

    public void setlPRRectBotX(String lPRRectBotX) {
        this.lPRRectBotX = lPRRectBotX;
    }

    public String getlPRRectBotY() {
        return lPRRectBotY;
    }

    public void setlPRRectBotY(String lPRRectBotY) {
        this.lPRRectBotY = lPRRectBotY;
    }

    public String getStrPlatePic() {
        return strPlatePic;
    }

    public void setStrPlatePic(String strPlatePic) {
        this.strPlatePic = strPlatePic;
    }

    public String getVehicleFace() {
        return vehicleFace;
    }

    public void setVehicleFace(String vehicleFace) {
        this.vehicleFace = vehicleFace;
    }

    public Integer getImageIndex6() {
        return imageIndex6;
    }

    public void setImageIndex6(Integer imageIndex6) {
        this.imageIndex6 = imageIndex6;
    }

    public String getImageURL6() {
        return imageURL6;
    }

    public void setImageURL6(String imageURL6) {
        this.imageURL6 = imageURL6;
    }

    public String getImageData6() {
        return imageData6;
    }

    public void setImageData6(String imageData6) {
        this.imageData6 = imageData6;
    }

    public Integer getImageType6() {
        return imageType6;
    }

    public void setImageType6(Integer imageType6) {
        this.imageType6 = imageType6;
    }

    public Integer getImageWidth6() {
        return imageWidth6;
    }

    public void setImageWidth6(Integer imageWidth6) {
        this.imageWidth6 = imageWidth6;
    }

    public Integer getImageHeight6() {
        return imageHeight6;
    }

    public void setImageHeight6(Integer imageHeight6) {
        this.imageHeight6 = imageHeight6;
    }

    public Integer getImageIndex7() {
        return imageIndex7;
    }

    public void setImageIndex7(Integer imageIndex7) {
        this.imageIndex7 = imageIndex7;
    }

    public String getImageURL7() {
        return imageURL7;
    }

    public void setImageURL7(String imageURL7) {
        this.imageURL7 = imageURL7;
    }

    public String getImageData7() {
        return imageData7;
    }

    public void setImageData7(String imageData7) {
        this.imageData7 = imageData7;
    }

    public Integer getImageType7() {
        return imageType7;
    }

    public void setImageType7(Integer imageType7) {
        this.imageType7 = imageType7;
    }

    public Integer getImageWidth7() {
        return imageWidth7;
    }

    public void setImageWidth7(Integer imageWidth7) {
        this.imageWidth7 = imageWidth7;
    }

    public Integer getImageHeight7() {
        return imageHeight7;
    }

    public void setImageHeight7(Integer imageHeight7) {
        this.imageHeight7 = imageHeight7;
    }

    public Integer getImageIndex8() {
        return imageIndex8;
    }

    public void setImageIndex8(Integer imageIndex8) {
        this.imageIndex8 = imageIndex8;
    }

    public String getImageURL8() {
        return imageURL8;
    }

    public void setImageURL8(String imageURL8) {
        this.imageURL8 = imageURL8;
    }

    public String getImageData8() {
        return imageData8;
    }

    public void setImageData8(String imageData8) {
        this.imageData8 = imageData8;
    }

    public Integer getImageType8() {
        return imageType8;
    }

    public void setImageType8(Integer imageType8) {
        this.imageType8 = imageType8;
    }

    public Integer getImageWidth8() {
        return imageWidth8;
    }

    public void setImageWidth8(Integer imageWidth8) {
        this.imageWidth8 = imageWidth8;
    }

    public Integer getImageHeight8() {
        return imageHeight8;
    }

    public void setImageHeight8(Integer imageHeight8) {
        this.imageHeight8 = imageHeight8;
    }

    public Integer getImageIndex9() {
        return imageIndex9;
    }

    public void setImageIndex9(Integer imageIndex9) {
        this.imageIndex9 = imageIndex9;
    }

    public String getImageURL9() {
        return imageURL9;
    }

    public void setImageURL9(String imageURL9) {
        this.imageURL9 = imageURL9;
    }

    public String getImageData9() {
        return imageData9;
    }

    public void setImageData9(String imageData9) {
        this.imageData9 = imageData9;
    }

    public Integer getImageType9() {
        return imageType9;
    }

    public void setImageType9(Integer imageType9) {
        this.imageType9 = imageType9;
    }

    public Integer getImageWidth9() {
        return imageWidth9;
    }

    public void setImageWidth9(Integer imageWidth9) {
        this.imageWidth9 = imageWidth9;
    }

    public Integer getImageHeight9() {
        return imageHeight9;
    }

    public void setImageHeight9(Integer imageHeight9) {
        this.imageHeight9 = imageHeight9;
    }

    public String getCorrectUserId() {
        return correctUserId;
    }

    public void setCorrectUserId(String correctUserId) {
        this.correctUserId = correctUserId;
    }

    public String getCorrectTime() {
        return correctTime;
    }

    public void setCorrectTime(String correctTime) {
        this.correctTime = correctTime;
    }

    public String getLaneQueueLength() {
        return laneQueueLength;
    }

    public void setLaneQueueLength(String laneQueueLength) {
        this.laneQueueLength = laneQueueLength;
    }

    public String getPoliceCode() {
        return policeCode;
    }

    public void setPoliceCode(String policeCode) {
        this.policeCode = policeCode;
    }

    public String getReservedField1() {
        return reservedField1;
    }

    public void setReservedField1(String reservedField1) {
        this.reservedField1 = reservedField1;
    }

    public String getReservedField2() {
        return reservedField2;
    }

    public void setReservedField2(String reservedField2) {
        this.reservedField2 = reservedField2;
    }
}


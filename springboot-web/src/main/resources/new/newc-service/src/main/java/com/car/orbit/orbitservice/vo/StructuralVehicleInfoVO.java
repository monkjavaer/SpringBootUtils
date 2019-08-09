package com.car.orbit.orbitservice.vo;

/**
 * 结构化服务器所返回使用的对象信息
 */
public class StructuralVehicleInfoVO {
    private String plateNum; //车牌号	String	是
    private String plateColor;	//车牌颜色ZH	String	是
    private String color;	//车辆颜色ZH	String	是
    private String colorCode;	//车辆颜色code	String	是
    private String brand;	//车辆品牌ZH_Name	String	是
    private String brandCode;	//车辆品牌code	String	是
    private String subBrand;	//车辆子品牌ZH_Name	String	是
    private String subBrandCode;	//车辆子品牌code	String	是
    private String year;	//车辆年款	 String	是
    private String typeCode;	//车辆类型编码 	String	是
    private String type;	//车辆类型ZH 	String	是
    private Boolean driver;	//是否识别到驾驶员	Boolean	是	true：识别到；false：没有识别到
    private Boolean face;	//是否识别到人脸	Boolean	是	true：识别到；false：没有识别到
    private Boolean belt;	//是否识别到安全带	Boolean	是	true：识别到；false：没有识别到
    private Boolean sunShield;	//是否识别到遮阳板	Boolean	是	true：识别到；false：没有识别到
    private Boolean tag;	//是否识别到年检标等标签	Boolean	是	true：识别到；false：没有识别到
    private Boolean decoration;	//是否识别到装饰	Boolean	是	true：识别到；false：没有识别到
    private Boolean napkinBox;	//是否识别到纸巾盒	Boolean	是	true：识别到；false：没有识别到
    private Boolean zhuanjt;	//是否识别到转经筒	Boolean	是	true：识别到；false：没有识别到
    private Boolean callPhone;	//是否识别到打电话行为	Boolean	是	true：识别到；false：没有识别到
    private Boolean sunRoof;	//是否识别到天窗	Boolean	是	true：识别到；false：没有识别到
    private Boolean holder;	//是否识别到行李架	Boolean	是	true：识别到；false：没有识别到
    private Boolean smoke;	//是否识别到吸烟行为	Boolean	是	true：识别到；false：没有识别到

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSubBrand() {
        return subBrand;
    }

    public void setSubBrand(String subBrand) {
        this.subBrand = subBrand;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getDriver() {
        return driver;
    }

    public void setDriver(Boolean driver) {
        this.driver = driver;
    }

    public Boolean getFace() {
        return face;
    }

    public void setFace(Boolean face) {
        this.face = face;
    }

    public Boolean getBelt() {
        return belt;
    }

    public void setBelt(Boolean belt) {
        this.belt = belt;
    }

    public Boolean getSunShield() {
        return sunShield;
    }

    public void setSunShield(Boolean sunShield) {
        this.sunShield = sunShield;
    }

    public Boolean getTag() {
        return tag;
    }

    public void setTag(Boolean tag) {
        this.tag = tag;
    }

    public Boolean getDecoration() {
        return decoration;
    }

    public void setDecoration(Boolean decoration) {
        this.decoration = decoration;
    }

    public Boolean getNapkinBox() {
        return napkinBox;
    }

    public void setNapkinBox(Boolean napkinBox) {
        this.napkinBox = napkinBox;
    }

    public Boolean getZhuanjt() {
        return zhuanjt;
    }

    public void setZhuanjt(Boolean zhuanjt) {
        this.zhuanjt = zhuanjt;
    }

    public Boolean getCallPhone() {
        return callPhone;
    }

    public void setCallPhone(Boolean callPhone) {
        this.callPhone = callPhone;
    }

    public Boolean getSunRoof() {
        return sunRoof;
    }

    public void setSunRoof(Boolean sunRoof) {
        this.sunRoof = sunRoof;
    }

    public Boolean getHolder() {
        return holder;
    }

    public void setHolder(Boolean holder) {
        this.holder = holder;
    }

    public Boolean getSmoke() {
        return smoke;
    }

    public void setSmoke(Boolean smoke) {
        this.smoke = smoke;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getSubBrandCode() {
        return subBrandCode;
    }

    public void setSubBrandCode(String subBrandCode) {
        this.subBrandCode = subBrandCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}

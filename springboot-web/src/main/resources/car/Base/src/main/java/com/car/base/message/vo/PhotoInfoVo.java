package com.car.base.message.vo;


import java.io.Serializable;

/**
 * Description: 图片信息
 * Original Author: monkjavaer, 2017/12/22
 */
public class PhotoInfoVo implements Serializable {

    private String imageIndex;
    private String imageUrl;
    private String imageType;
//    private String imageFormat;
    private String passTime;


    //图像的base64编码
    private String imageData;


    public String getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(String imageIndex) {
        this.imageIndex = imageIndex;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getPassTime() {
        return passTime;
    }

    public void setPassTime(String passTime) {
        this.passTime = passTime;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}

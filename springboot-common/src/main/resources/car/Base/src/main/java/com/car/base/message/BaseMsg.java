/**
 * Copyright(C) 2017 Car All rights reserved.
 * Original Author: monkjavaer, 2017/11/10
 */
package com.base.springboot.car.Base.src.main.java.com.car.base.message;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
public class BaseMsg implements Serializable {
    private String messageId;
    private Date catchTime;

    private String imagePath; //图片访问路径
    private String imageData; //Base64数据

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Date getCatchTime() {
        return catchTime;
    }

    public void setCatchTime(Date catchTime) {
        this.catchTime = catchTime;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}

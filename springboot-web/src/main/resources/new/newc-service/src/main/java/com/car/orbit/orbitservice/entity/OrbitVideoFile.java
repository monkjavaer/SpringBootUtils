package com.car.orbit.orbitservice.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Title: OrbitVideoFile
 * @Package: com.car.orbit.orbitservice.entity
 * @Description:
 * @Author: monkjavaer
 * @Date: 2019/07/02 17:28
 * @Version: V1.0
 */
@Table(name = "orbit_video_file")
public class OrbitVideoFile {

    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 文件名
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 用户id
     */
    @Column(name = "USER_ID")
    private String userId;

    /**
     * 文件夹id
     */
    @Column(name = "FILEFOLDER_ID")
    private String fileFolderId;

    /**
     * 设备id
     */
    @Column(name = "DEVICE_ID")
    private String deviceId;

    /**
     * 状态
     */
    @Column(name = "STATUS")
    private Integer status;

    /**
     * 文件在fastdfs上的存储地址
     */
    @Column(name = "FILE_PATH")
    private String filePath;

    /**
     * 上传时间
     */
    @Column(name = "UPLOAD_TIME")
    private Date uploadTime;

    /**
     * 文件描述
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * 文件大小（MB为单位）
     */
    @Column(name = "SIZE")
    private Float size;

    /**
     * 视频时长（秒为单位）
     */
    @Column(name = "TIME")
    private Integer time;

    /**
     * 视频首帧图片
     */
    @Column(name = "PICTURE")
    private String picture;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFileFolderId() {
        return fileFolderId;
    }

    public void setFileFolderId(String fileFolderId) {
        this.fileFolderId = fileFolderId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}

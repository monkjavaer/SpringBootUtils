package com.car.orbit.orbitservice.vo;

/**
 * @Title: LogVO
 * @Package: com.car.orbit.orbitservice.vo
 * @Description: 日志分页结果
 * @Author: monkjavaer
 * @Data: 2019/3/14 8:39
 * @Version: V1.0
 */
public class LogVO {

    private String username;
    private String name;
    private Integer dataType;
    private Integer actionType;
    private String createTime;
    private String description;
    private String photoUrl;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}

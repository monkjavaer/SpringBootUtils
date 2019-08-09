package com.car.orbit.orbitservice.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Title: OrbitVideoFileFolder
 * @Package: com.car.orbit.orbitservice.entity
 * @Description:
 * @Author: monkjavaer
 * @Date: 2019/07/02 15:06
 * @Version: V1.0
 */
@Table(name = "orbit_video_filefolder")
public class OrbitVideoFileFolder {

    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 文件夹名
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 用户id
     */
    @Column(name = "USER_ID")
    private String userId;

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
}

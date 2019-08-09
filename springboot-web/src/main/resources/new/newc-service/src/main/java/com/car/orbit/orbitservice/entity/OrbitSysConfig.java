package com.car.orbit.orbitservice.entity;

import javax.persistence.*;

@Table(name = "orbit_sys_config")
public class OrbitSysConfig {
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 用户ID
     */
    @Column(name = "USER_ID")
    private String userId;

    /**
     * 权限ID字符串以，隔开
     */
    @Column(name = "AUTHORITY_IDSTR")
    private String authorityIdstr;

    /**
     * 主题（1、black,2、white）
     */
    @Column(name = "TOPIC")
    private Integer topic;

    /**
     * 语言/1、chinese/2、english）
     */
    @Column(name = "LANGUAGE")
    private Integer language;

    /**
     * 获取主键
     *
     * @return ID - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取用户ID
     *
     * @return USER_ID - 用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取权限ID字符串以，隔开
     *
     * @return AUTHORITY_IDSTR - 权限ID字符串以，隔开
     */
    public String getAuthorityIdstr() {
        return authorityIdstr;
    }

    /**
     * 设置权限ID字符串以，隔开
     *
     * @param authorityIdstr 权限ID字符串以，隔开
     */
    public void setAuthorityIdstr(String authorityIdstr) {
        this.authorityIdstr = authorityIdstr;
    }

    /**
     * 获取主题（1、black,2、white）
     *
     * @return TOPIC - 主题（1、black,2、white）
     */
    public Integer getTopic() {
        return topic;
    }

    /**
     * 设置主题（1、black,2、white）
     *
     * @param topic 主题（1、black,2、white）
     */
    public void setTopic(Integer topic) {
        this.topic = topic;
    }

    /**
     * 获取语言/1、chinese/2、english）
     *
     * @return LANGUAGE - 语言/1、chinese/2、english）
     */
    public Integer getLanguage() {
        return language;
    }

    /**
     * 设置语言/1、chinese/2、english）
     *
     * @param language 语言/1、chinese/2、english）
     */
    public void setLanguage(Integer language) {
        this.language = language;
    }
}
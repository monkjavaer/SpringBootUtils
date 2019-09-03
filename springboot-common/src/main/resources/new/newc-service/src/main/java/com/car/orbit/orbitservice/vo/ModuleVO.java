package com.car.orbit.orbitservice.vo;

/**
 * @Title: ModuleVO
 * @Package: com.car.orbit.orbitservice.vo
 * @Description: 首页模块
 * @Author: monkjavaer
 * @Date: 2019/03/13 11:09
 * @Version: V1.0
 */
public class ModuleVO {

    /**
     * authority id
     */
    private String id;
    /**
     * 模块名
     */
    private String name;
    /**
     * 模块名（中文）
     */
    private String zhName;
    /**
     * 是否有权限
     */
    private boolean findPermission;
    /**
     * 用户是否配置到首页
     */
    private boolean config;
    /**
     * 辅助判断是否为同一组
     */
    private int parentNode;

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

    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    public boolean isFindPermission() {
        return findPermission;
    }

    public void setFindPermission(boolean findPermission) {
        this.findPermission = findPermission;
    }

    public boolean isConfig() {
        return config;
    }

    public void setConfig(boolean config) {
        this.config = config;
    }

    public int getParentNode() {
        return parentNode;
    }

    public void setParentNode(int parentNode) {
        this.parentNode = parentNode;
    }
}

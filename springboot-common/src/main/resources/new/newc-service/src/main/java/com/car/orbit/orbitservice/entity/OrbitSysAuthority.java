package com.car.orbit.orbitservice.entity;

import javax.persistence.*;

@Table(name = "orbit_sys_authority")
public class OrbitSysAuthority {
    /**
     * 权限id
     */
    @Id
    private String id;

    /**
     * 权限名用于国际化映射
     */
    @Column(name = "authority_name")
    private String authorityName;

    /**
     * 权限中文名
     */
    @Column(name = "zh_name")
    private String zhName;

    /**
     * 权限代码
     */
    private String node;

    /**
     * 父级权限
     */
    @Column(name = "parent_node")
    private String parentNode;

    /**
     * 页面的URL
     */
    private String url;

    /**
     * 页面的标识值
     */
    private String path;

    /**
     * 权限模块图标
     */
    private String icon;

    /**
     * 添加权限：1有，2无 
     */
    @Column(name = "operate_add")
    private Integer operateAdd;

    /**
     * 修改权限：1有，2无
     */
    @Column(name = "operate_update")
    private Integer operateUpdate;

    /**
     * 删除权限：1有，2无
     */
    @Column(name = "operate_delete")
    private Integer operateDelete;

    /**
     *  备注
     */
    private String comment;

    /**
     * 获取权限id
     *
     * @return id - 权限id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置权限id
     *
     * @param id 权限id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取权限名用于国际化映射
     *
     * @return authority_name - 权限名用于国际化映射
     */
    public String getAuthorityName() {
        return authorityName;
    }

    /**
     * 设置权限名用于国际化映射
     *
     * @param authorityName 权限名用于国际化映射
     */
    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    /**
     * 获取权限中文名
     *
     * @return zh_name - 权限中文名
     */
    public String getZhName() {
        return zhName;
    }

    /**
     * 设置权限中文名
     *
     * @param zhName 权限中文名
     */
    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    /**
     * 获取权限代码
     *
     * @return node - 权限代码
     */
    public String getNode() {
        return node;
    }

    /**
     * 设置权限代码
     *
     * @param node 权限代码
     */
    public void setNode(String node) {
        this.node = node;
    }

    /**
     * 获取父级权限
     *
     * @return parent_node - 父级权限
     */
    public String getParentNode() {
        return parentNode;
    }

    /**
     * 设置父级权限
     *
     * @param parentNode 父级权限
     */
    public void setParentNode(String parentNode) {
        this.parentNode = parentNode;
    }

    /**
     * 获取页面的URL
     *
     * @return url - 页面的URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置页面的URL
     *
     * @param url 页面的URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取页面的标识值
     *
     * @return path - 页面的标识值
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置页面的标识值
     *
     * @param path 页面的标识值
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取权限模块图标
     *
     * @return icon - 权限模块图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置权限模块图标
     *
     * @param icon 权限模块图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取添加权限：1有，2无 
     *
     * @return operate_add - 添加权限：1有，2无 
     */
    public Integer getOperateAdd() {
        return operateAdd;
    }

    /**
     * 设置添加权限：1有，2无 
     *
     * @param operateAdd 添加权限：1有，2无 
     */
    public void setOperateAdd(Integer operateAdd) {
        this.operateAdd = operateAdd;
    }

    /**
     * 获取修改权限：1有，2无
     *
     * @return operate_update - 修改权限：1有，2无
     */
    public Integer getOperateUpdate() {
        return operateUpdate;
    }

    /**
     * 设置修改权限：1有，2无
     *
     * @param operateUpdate 修改权限：1有，2无
     */
    public void setOperateUpdate(Integer operateUpdate) {
        this.operateUpdate = operateUpdate;
    }

    /**
     * 获取删除权限：1有，2无
     *
     * @return operate_delete - 删除权限：1有，2无
     */
    public Integer getOperateDelete() {
        return operateDelete;
    }

    /**
     * 设置删除权限：1有，2无
     *
     * @param operateDelete 删除权限：1有，2无
     */
    public void setOperateDelete(Integer operateDelete) {
        this.operateDelete = operateDelete;
    }

    /**
     * 获取 备注
     *
     * @return comment -  备注
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置 备注
     *
     * @param comment  备注
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}
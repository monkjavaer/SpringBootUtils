package com.car.orbit.orbitservice.vo;

import java.util.List;

/**
 * @Title: RoleVO
 * @Package: com.car.orbit.orbitservice.vo
 * @Description: 角色分页查询结果
 * @Author: monkjavaer
 * @Date: 2019/03/19 15:33
 * @Version: V1.0
 */
public class RoleVO {

    /**
     * 角色id
     */
    private String id;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 创建者头像
     */
    private String creatorPhoto;
    /**
     * 角色权限情况
     */
    private List<RoleAuthorityVO> authorityList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorPhoto() {
        return creatorPhoto;
    }

    public void setCreatorPhoto(String creatorPhoto) {
        this.creatorPhoto = creatorPhoto;
    }

    public List<RoleAuthorityVO> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(List<RoleAuthorityVO> moduleVOList) {
        this.authorityList = moduleVOList;
    }
}

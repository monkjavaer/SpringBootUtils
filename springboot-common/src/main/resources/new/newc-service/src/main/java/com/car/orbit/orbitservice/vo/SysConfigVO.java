package com.car.orbit.orbitservice.vo;

/**
 * @Title: SysConfigVO
 * @Package: com.car.orbit.orbitservice.vo
 * @Description:
 * @Author: monkjavaer
 * @Date: 2019/03/09 09:42
 * @Version: V1.0
 */
public class SysConfigVO {

    /**
     * 用户id
     */
    private String userId;
    /**
     * 权限id
     */
    private String authorityIdStr;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuthorityIdStr() {
        return authorityIdStr;
    }

    public void setAuthorityIdStr(String authorityIdStr) {
        this.authorityIdStr = authorityIdStr;
    }
}

package com.car.orbit.orbitservice.vo;

/**
 * @Title: PasswordResetVO
 * @Package: com.car.orbit.orbitservice.vo
 * @Description:
 * @Author: monkjavaer
 * @Date: 2019/03/11 09:49
 * @Version: V1.0
 */
public class PasswordResetVO {

    /**
     * 用户id
     */
    private String userId;
    /**
     * 旧密码
     */
    private String oldPassword;
    /**
     * 新密码
     */
    private String newPassword;
    /**
     * 当前操作用户的密码（用于安全校验）
     */
    private String myPassword;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getMyPassword() {
        return myPassword;
    }

    public void setMyPassword(String myPassword) {
        this.myPassword = myPassword;
    }
}

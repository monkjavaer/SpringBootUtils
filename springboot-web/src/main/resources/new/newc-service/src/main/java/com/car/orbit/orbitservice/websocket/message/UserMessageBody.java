package com.car.orbit.orbitservice.websocket.message;

import java.io.Serializable;

/**
 * @Title: UserMessageBody
 * @Package: com.car.orbit.orbitservice.websocket.message
 * @Description: 用户退出消息（重复登录，删除用户，重置密码）
 * @Author: monkjavaer
 * @Data: 2019/4/8 14:41
 * @Version: V1.0
 */
public class UserMessageBody implements Serializable {
    /**
     * 用户名
     */
    private String  userName;

    /**
     * 退出标志
     */
    private boolean logout = true;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isLogout() {
        return logout;
    }

    public void setLogout(boolean logout) {
        this.logout = logout;
    }
}

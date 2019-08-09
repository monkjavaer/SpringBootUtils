package com.car.orbit.orbitservice.websocket.message;

/**
 * @Title: MessageType
 * @Package: com.car.orbit.orbitservice.websocket
 * @Description: WebSocket消息类型定义
 * @Author: monkjavaer
 * @Data: 2019/4/4 18:19
 * @Version: V1.0
 */
public enum MessageType {
    //布控报警
    alarm((byte)1, "alarm"),

    //重复登录
    login((byte)2, "login"),

    //删除用户
    delete((byte)3, "delete"),

    //重置密码
    reset((byte)4, "reset"),

    //WebSocket心跳检测
    heart((byte)5, "heart"),

    //角色权限被修改
    role((byte)6,"role");

    public byte value;
    public String prefix;


    MessageType(byte value, String prefix) {
        this.value = value;
        this.prefix = prefix;
    }
}

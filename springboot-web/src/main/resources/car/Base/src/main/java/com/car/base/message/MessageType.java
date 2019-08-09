package com.car.base.message;

public enum MessageType {
	//过车
	VEHICLE_STRUCTURE((byte)1, "structure"),

	//布控
	VEHICLE_DETECTION((byte)2, "detection"),

	//违章
	TRAFFIC_VIOLATION((byte)10, "violation"),

	//过车首页展示
	PASSEDBY_VEHICLE((byte)3, "passedby"),

	//LOGIN
	REPEAT_LOGIN((byte)4, "login"),

	//DELETE_USER
	DELETE_USER((byte)5, "delete"),

	//RESET_PASSWORD
	RESET_PASSWORD((byte)6, "reset"),

	//修改数据权限
	UPDATE_AUTHORITY((byte)7, "authority"),
    //心跳检测
    HEART_BEAT((byte)8, "heart"),
	UNKOWN((byte)0, "unw");
 
	public byte value;
	public String prefix;
 
	
	MessageType(byte value, String prefix) {
		this.value = value;
		this.prefix = prefix;
	}
}
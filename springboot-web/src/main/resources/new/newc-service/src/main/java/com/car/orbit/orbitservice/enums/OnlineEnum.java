package com.car.orbit.orbitservice.enums;

/**
 * Description:是否在线字典
 * Created by monkjavaer on 2017/12/6 0006.
 */
public enum OnlineEnum {

	/** 离线 **/
	NO(0, "离线"),
	/** 在线 **/
	YES(1, "在线");

	private int value;
	private String description;

	OnlineEnum(int value, String description) {
		this.value = value;
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
}

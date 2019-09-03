package com.car.orbit.orbitservice.enums;

/**
 * Description:是否删除字典，数据库delete字段
 * Created by monkjavaer on 2017/12/6 0006.
 */
public enum HasDeleteEnum {

	/** 未删除 **/
	NO(1, "未删除"),
	/** 已删除 **/
	YES(2, "已删除");

	private int value;
	private String deleteDescription;

	HasDeleteEnum(int value, String deleteDescription) {
		this.value = value;
		this.deleteDescription = deleteDescription;
	}

	public int getValue() {
		return value;
	}

	public String getDeleteDescription() {
		return deleteDescription;
	}
}

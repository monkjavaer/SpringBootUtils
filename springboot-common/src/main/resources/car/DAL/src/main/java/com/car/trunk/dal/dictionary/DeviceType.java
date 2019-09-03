package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.dictionary;

import org.springframework.web.servlet.support.RequestContext;

/**
 * Description:设备类型字典
 * Created by monkjavaer on 2017/12/6 0006.
 */
public enum DeviceType {
	/**
	 * 电警摄像机
	 */
	ELECTRONIC_POLICE((Integer)0, "电警摄像机", "dic.devicetype.electronic_police"),

	BAYONET((Integer)1, "卡口摄像机", "dic.devicetype.bayonet"),

	HOST((Integer)2, "主机", "dic.devicetype.host");

	public Integer value;
	public String desc;
	public String label;

	private DeviceType(Integer value, String desc, String label) {
		this.value = value;
		this.desc = desc;
		this.label = label;
	}
	
	public void setDesc(Integer value, String desc) {
		for(DeviceType type: DeviceType.values()) {
			if(type.value.equals(value)) {
				type.desc = desc;
				break;
			}
		}
	}
	
	public static DeviceType get(Integer value) {
		for(DeviceType type: DeviceType.values()) {
			if(type.value.equals(value)) {
				return type;
			}
		}
		return null;
	}	
	
	public String getDesc(RequestContext context) {
		return context.getMessage(label, desc);
	}	
}

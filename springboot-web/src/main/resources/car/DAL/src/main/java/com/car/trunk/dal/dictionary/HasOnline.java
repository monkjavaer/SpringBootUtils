package com.car.trunk.dal.dictionary;

import org.springframework.web.servlet.support.RequestContext;

/**
 * Description:设备是否在线字典
 * Created by monkjavaer on 2017/12/6 0006.
 */
public enum HasOnline {
	/**
	 * 不在线
	 */
	NO((byte)0, "不在线", "dic.hasonline.no"),

	YES((byte)1, "在线", "dic.hasonline.yes");

	public byte value;
	public String desc;
	public String label;

	private HasOnline(byte value, String desc, String label) {
		this.value = value;
		this.desc = desc;
		this.label = label;
	}
	
	public void setDesc(byte value, String desc) {
		for(HasOnline type: HasOnline.values()) {
			if(type.value == value) {
				type.desc = desc;
				break;
			}
		}
	}
	
	public static HasOnline get(byte value) {
		for(HasOnline type: HasOnline.values()) {
			if(type.value == value) {
				return type;
			}
		}
		return null;
	}	
	
	public String getDesc(RequestContext context) {
		return context.getMessage(label, desc);
	}	
}

package com.car.trunk.dal.dictionary;

import org.springframework.web.servlet.support.RequestContext;

/**
 * Description:黑名单，白名单是否启用字典
 * Created by monkjavaer on 2017/12/6 0006.
 */
public enum Status {
	/**
	 * 关闭
	 */
	NO((byte)0, "关闭", "dic.status.no"),

	YES((byte)1, "开启", "dic.status.yes");

	public byte value;
	public String desc;
	public String label;

	private Status(byte value, String desc, String label) {
		this.value = value;
		this.desc = desc;
		this.label = label;
	}
	
	public void setDesc(byte value, String desc) {
		for(Status type: Status.values()) {
			if(type.value == value) {
				type.desc = desc;
				break;
			}
		}
	}
	
	public static Status get(byte value) {
		for(Status type: Status.values()) {
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

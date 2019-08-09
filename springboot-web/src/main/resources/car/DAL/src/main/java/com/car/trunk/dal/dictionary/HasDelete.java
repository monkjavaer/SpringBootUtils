package com.car.trunk.dal.dictionary;

import org.springframework.web.servlet.support.RequestContext;

/**
 * Description:是否删除字典，数据库delete字段
 * Created by monkjavaer on 2017/12/6 0006.
 */
public enum HasDelete {
	/**
	 * 未删除
	 */
	NO((byte)0, "未删除", "dic.hasdelete.no"),
 
	YES((byte)1, "已删除", "dic.hasdelete.yes");
 
	public byte value;
	public String desc;
	public String label;
	
	private HasDelete(byte value, String desc, String label) {
		this.value = value;
		this.desc = desc;
		this.label = label;
	}
	
	public void setDesc(byte value, String desc) {
		for(HasDelete type: HasDelete.values()) {
			if(type.value == value) {
				type.desc = desc;
				break;
			}
		}
	}
	
	public static HasDelete get(byte value) {
		for(HasDelete type: HasDelete.values()) {
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

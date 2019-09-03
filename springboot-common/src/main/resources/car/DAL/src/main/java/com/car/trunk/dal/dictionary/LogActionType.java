package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.dictionary;

import org.springframework.web.servlet.support.RequestContext;

/**
 * Description:报用户日志操作字典
 * Created by monkjavaer on 2017/12/6 0006.
 */
public enum LogActionType {
	/**
	 * 添加数据
	 */
	ADD((Integer)0, "添加数据", "dic.logactiontype.add"),

	UPDATE((Integer)1, "修改数据", "dic.logactiontype.update"),

	DELETE((Integer)2, "删除数据", "dic.logactiontype.delete");

	public Integer value;
	public String desc;
	public String label;

	private LogActionType(Integer value, String desc, String label) {
		this.value = value;
		this.desc = desc;
		this.label = label;
	}
	
	public void setDesc(Integer value, String desc) {
		for(LogActionType type: LogActionType.values()) {
			if(type.value.equals(value)) {
				type.desc = desc;
				break;
			}
		}
	}
	
	public static LogActionType get(Integer value) {
		for(LogActionType type: LogActionType.values()) {
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

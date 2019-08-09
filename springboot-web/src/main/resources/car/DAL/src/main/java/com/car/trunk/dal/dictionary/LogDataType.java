package com.car.trunk.dal.dictionary;

import org.springframework.web.servlet.support.RequestContext;
/**
 * Description:用户日志数据类型字典
 * Created by monkjavaer on 2017/12/6 0006.
 */
public enum LogDataType {
	/**
	 * 违章数据变更
	 */
	VIOLATION_DATA_CHANGES((Integer)0, "违章数据变更", "dic.logdatatype.violation_data_changes"),

	VEHICLECONTROL_DATA_CHANGES((Integer)1, "车辆布控数据变更", "dic.logdatatype.vehiclecontrol_data_changes"),

	DEVICE_DATA_CHANGES((Integer)2, "设备数据变更", "dic.logdatatype.device_data_changes"),

	SYSTEM_DATA_CHANGES((Integer)3, "系统数据变更", "dic.logdatatype.system_data_changes");

	public Integer value;
	public String desc;
	public String label;

	private LogDataType(Integer value, String desc, String label) {
		this.value = value;
		this.desc = desc;
		this.label = label;
	}
	
	public void setDesc(Integer value, String desc) {
		for(LogDataType type: LogDataType.values()) {
			if(type.value.equals(value)) {
				type.desc = desc;
				break;
			}
		}
	}
	
	public static LogDataType get(Integer value) {
		for(LogDataType type: LogDataType.values()) {
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

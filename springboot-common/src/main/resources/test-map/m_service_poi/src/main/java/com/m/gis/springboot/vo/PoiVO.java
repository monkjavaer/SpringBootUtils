package com.m.gis.springboot.vo;

import com.m.gis.springboot.geo.base.common.GisCoordinate;

/**
 * @Title: PoiVO.java
 * @Package com.m.gis.springboot.vo
 * @Description: 返回给前端的poi vo
 * @author monkjavaer
 * @date 2017年11月30日 上午11:54:20
 * @version V1.0
 */
public class PoiVO extends GisCoordinate {
	private Integer id;
	private String name;
	private String address;
	private String telephone;
	private String fax;
	private String type;
	private String districtCode;
	private String note;
	public PoiVO() {
		super(true);
		// TODO Auto-generated constructor stub
	}
	public PoiVO(Integer id, String name, String address, String telephone,
                 String fax, String type, String districtCode, String note, Double longitude,
                 Double latitude) {
		super(longitude,latitude,true);
		this.id = id;
		this.name = name;
		this.address = address;
		this.telephone = telephone;
		this.fax = fax;
		this.type = type;
		this.districtCode = districtCode;
		this.note = note;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
}


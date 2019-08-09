package com.m.gis.springboot.bo;
/**
 * @Title: RoadResultBO.java
 * @Package com.m.gis.springboot.bo
 * @Description: TODO(添加描述)
 * @author monkjavaer
 * @date 2018年1月5日 下午3:32:17
 * @version V1.0
 */
public class RoadResultBO{
	private String id;
	private double length;
	private String geom;
	public RoadResultBO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RoadResultBO(String id, double length, String geom) {
		super();
		this.id = id;
		this.length = length;
		this.geom = geom;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public String getGeom() {
		return geom;
	}
	public void setGeom(String geom) {
		this.geom = geom;
	}
}

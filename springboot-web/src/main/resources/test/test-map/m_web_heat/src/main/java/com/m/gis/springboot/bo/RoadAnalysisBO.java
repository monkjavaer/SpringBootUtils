package com.m.gis.springboot.bo;

import com.vividsolutions.jts.geom.MultiLineString;

import java.util.List;

/**
 * @Title: RoadAnalysisBO.java
 * @Package com.m.gis.springboot.bo
 * @Description: TODO(添加描述)
 * @author monkjavaer
 * @date 2018年1月5日 下午3:32:56
 * @version V1.0
 */
public class RoadAnalysisBO {

	private long count;
	private List<MultiLineString> roadList;
	private double totalLength;
	private long averageCount;
	
	public RoadAnalysisBO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public List<MultiLineString> getRoadList() {
		return roadList;
	}
	public void setRoadList(List<MultiLineString> roadList) {
		this.roadList = roadList;
	}
	public double getTotalLength() {
		return totalLength;
	}
	public void setTotalLength(double totalLength) {
		this.totalLength = totalLength;
	}
	public long getAverageCount() {
		return averageCount;
	}
	public void setAverageCount(long averageCount) {
		this.averageCount = averageCount;
	}
	
	public String toString(){
		return new String("{count="+count+",totalLength="+totalLength+",averageCount="+averageCount+",road nums="+roadList.size()+"}");
	}
}


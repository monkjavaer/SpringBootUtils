package com.m.gis.springboot.qo;

import javax.validation.Valid;
import java.util.List;

/**
 * @Title: GisCameraRouteQO.java
 * @Package com.m.gis.springboot.qo
 * @Description: 用于根据摄像头位置计算车流量分布
 * @author monkjavaer
 * @date 2018年1月3日 下午3:49:30
 * @version V1.0
 */
public class GisCameraRouteQO extends GisBaseQO {
	@Valid
	private List<GisCameraPosQO> cameraList;

	public GisCameraRouteQO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public GisCameraRouteQO(List<GisCameraPosQO> cameraList) {
		super();
		this.cameraList = cameraList;
	}

/*	public GisCameraRouteQO(List<GisCameraPosQO> cameraList, double minLat, double maxLat, double minLon,
			double maxLon) {
		super(minLat, maxLat, minLon, maxLon);
		this.cameraList = cameraList;
	}*/

	public List<GisCameraPosQO> getCameraList() {
		return cameraList;
	}

	public void setCameraList(List<GisCameraPosQO> cameraList) {
		this.cameraList = cameraList;
	}
}


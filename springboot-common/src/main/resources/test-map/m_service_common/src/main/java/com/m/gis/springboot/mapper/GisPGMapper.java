package com.m.gis.springboot.mapper;

import org.apache.ibatis.annotations.Param;

public interface GisPGMapper {
	/***
	 * @Description: 从数据库获取缓冲区图形
	 * @Author: monkjavaer
	 * @Data: 12:50 2018/6/15
	 * @Param: [geomWkt, distanceInMeters]
	 * @Throws
	 * @Return java.lang.String
	 */
	String getGeodesyBuffer(@Param("geomWkt")String geomWkt, @Param("distanceInMeters")double distanceInMeters);
}
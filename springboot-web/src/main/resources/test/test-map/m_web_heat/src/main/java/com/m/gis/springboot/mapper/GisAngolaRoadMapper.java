package com.m.gis.springboot.mapper;

import com.m.gis.springboot.po.GisAngolaRoad;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GisAngolaRoadMapper {
	
	/**
	 * 
	 * @name selectByPoint
	 * @description  根据传入的经纬度点查询相交的路，容差值tolerance
	 * @param lon 经度
	 * @param lat 纬度
	 * @param tolerance 容差值，查找以输入点为圆心，该值为半径的圆内的所有路网数据
	 * @return 
	 * @author monkjavaer
	 * @date 2018年1月3日
	 */
	List<GisAngolaRoad> selectByPoint(@Param("lon") double lon, @Param("lat") double lat, @Param("tolerance") double tolerance);

	String selectByPointBatch(@Param("paramJson") String param, @Param("tolerance") double tolerance);
	
	
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gis_angola_road_l
     *
     * @mbg.generated Wed Jan 03 16:27:38 CST 2018
     */
    int deleteByPrimaryKey(Integer gid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gis_angola_road_l
     *
     * @mbg.generated Wed Jan 03 16:27:38 CST 2018
     */
    int insert(GisAngolaRoad record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gis_angola_road_l
     *
     * @mbg.generated Wed Jan 03 16:27:38 CST 2018
     */
    GisAngolaRoad selectByPrimaryKey(Integer gid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gis_angola_road_l
     *
     * @mbg.generated Wed Jan 03 16:27:38 CST 2018
     */
    List<GisAngolaRoad> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gis_angola_road_l
     *
     * @mbg.generated Wed Jan 03 16:27:38 CST 2018
     */
    int updateByPrimaryKey(GisAngolaRoad record);
}
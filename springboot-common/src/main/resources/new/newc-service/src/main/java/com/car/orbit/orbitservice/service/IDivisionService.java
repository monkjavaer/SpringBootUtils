package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.entity.OrbitResArea;
import com.car.orbit.orbitservice.entity.OrbitResCity;
import com.car.orbit.orbitservice.entity.OrbitResRoadcrossPoint;
import com.car.orbit.orbitservice.exception.RelationshipException;
import com.car.orbit.orbitservice.util.DeviceTreeNode;
import com.car.orbit.orbitservice.vo.AreaVO;
import com.car.orbit.orbitservice.vo.CityVO;
import com.car.orbit.orbitservice.vo.DivisionSimpleVO;
import com.car.orbit.orbitservice.vo.RoadVO;

import java.util.List;
import java.util.Map;

/**
 * @Title: IDivisionService
 * @Package: com.car.orbit.orbitservice.service
 * @Description: 行政区划接口
 * @Author: monkjavaer
 * @Data: 2019/3/14 11:25
 * @Version: V1.0
 */
public interface IDivisionService {

    /**
     * 添加城市
     *
     * @param orbitResCity
     */
    void addCity(OrbitResCity orbitResCity);

    /**
     * 添加区域
     *
     * @param orbitResArea
     */
    void addArea(OrbitResArea orbitResArea);

    /**
     * 添加路口
     *
     * @param orbitResRoadcrossPoint
     */
    void addRoad(OrbitResRoadcrossPoint orbitResRoadcrossPoint);

    /**
     * 查询所有未删除城市
     *
     * @return
     */
    List<CityVO> quaryCityList();

    /**
     * 查询城市下面所有区域
     *
     * @param cityId 城市主键
     * @return
     */
    List<AreaVO> quaryAreaList(String cityId);

    /**
     * 查询区域下面所有路段
     *
     * @param areaId 区域ID
     * @return
     */
    List<RoadVO> queryRoadList(String areaId);

    /**
     * 根据路口查询行政区划信息
     * @param roadId
     * @return
     */
    List<DivisionSimpleVO> queryDivisionSimpleVO(String roadId);

    /**
     * 修改城市
     *
     * @param city
     */
    void updateCity(OrbitResCity city);

    /**
     * 修改区域
     *
     * @param area
     */
    void updateArea(OrbitResArea area);

    /**
     * 修改路口
     *
     * @param road
     */
    void updateRoad(OrbitResRoadcrossPoint road);

    /**
     * 删除城市
     * @param city
     */
    void deleteCity(OrbitResCity city) throws RelationshipException;

    /**
     * 删除区域
     * @param area
     * @return
     */
    Map<String,Object> deleteArea(OrbitResArea area) throws RelationshipException;

    /**
     * 删除路口
     * @param road
     */
    Map<String,Object> deleteRoad(OrbitResRoadcrossPoint road) throws RelationshipException;

    /**
     * 城市名查重
     *
     * @param name
     * @return true 已经存在
     */
    Boolean hasCityName(String name, String id);

    /**
     * 城市行政区域编码查重
     *
     * @param code
     * @param id
     * @return
     */
    Boolean hasCityAdminRegionCode(String code, String id);

    /**
     * 区域名查重
     *
     * @param name
     * @param id
     * @param cityId
     * @return
     */
    Boolean hasAreaName(String name, String id, String cityId);

    /**
     * 区域行政区域编码查重
     *
     * @param code
     * @param id
     * @return
     */
    Boolean hasAreaAdminRegionCode(String code, String id);

    /**
     * 路口名查重
     *
     * @param name
     * @param id
     * @return
     */
    Boolean hasRoadName(String name, String id, String areaId);

    /**
     * 获取树形组织机构
     * @return
     */
    List<DeviceTreeNode> getDeviceTree();
}

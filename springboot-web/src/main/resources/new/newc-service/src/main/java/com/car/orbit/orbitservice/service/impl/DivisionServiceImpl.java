package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.annotation.LogAnnotation;
import com.car.orbit.orbitservice.constant.OrbitServiceConstant;
import com.car.orbit.orbitservice.entity.*;
import com.car.orbit.orbitservice.enums.HasDeleteEnum;
import com.car.orbit.orbitservice.exception.RelationshipException;
import com.car.orbit.orbitservice.mapper.*;
import com.car.orbit.orbitservice.service.IDivisionService;
import com.car.orbit.orbitservice.service.IUserService;
import com.car.orbit.orbitservice.util.DeviceTreeNode;
import com.car.orbit.orbitservice.vo.*;
import com.car.orbit.orbitutil.tools.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @Title: DivisionServiceImpl
 * @Package: com.car.orbit.orbitservice.service.impl
 * @Description: 行政区划接口实现
 * @Author: monkjavaer
 * @Data: 2019/3/14 11:33
 * @Version: V1.0
 */
@Service
////@Transactional(rollbackFor = Exception.class)
public class DivisionServiceImpl implements IDivisionService {
    /**
     * 城市mapper
     */
    @Autowired
    private OrbitResCityMapper cityMapper;
    /**
     * 区域mapper
     */
    @Autowired
    private OrbitResAreaMapper areaMapper;
    /**
     * 路口mapper
     */
    @Autowired
    private OrbitResRoadcrossPointMapper roadMapper;

    @Autowired
    private OrbitResDeviceMapper deviceMapper;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private OrbitSysMonitorCenterMapper monitorCenterMapper;

    /**
     * 添加城市
     *
     * @param orbitResCity
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_CITY, actionType = OrbitServiceConstant.ACTION_ADD)
    @Override
    public void addCity(OrbitResCity orbitResCity) {
        orbitResCity.setId(UUIDUtils.generate());
        orbitResCity.setDeleted(HasDeleteEnum.NO.getValue());
        cityMapper.insert(orbitResCity);
    }

    /**
     * 添加区域
     *
     * @param orbitResArea
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_AREA, actionType = OrbitServiceConstant.ACTION_ADD)
    @Override
    public void addArea(OrbitResArea orbitResArea) {
        orbitResArea.setId(UUIDUtils.generate());
        orbitResArea.setDeleted(HasDeleteEnum.NO.getValue());
        areaMapper.insert(orbitResArea);
    }

    /**
     * 添加路口
     *
     * @param orbitResRoadcrossPoint
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_ROAD, actionType = OrbitServiceConstant.ACTION_ADD)
    @Override
    public void addRoad(OrbitResRoadcrossPoint orbitResRoadcrossPoint) {
        orbitResRoadcrossPoint.setId(UUIDUtils.generate());
        orbitResRoadcrossPoint.setDeleted(HasDeleteEnum.NO.getValue());
        roadMapper.insert(orbitResRoadcrossPoint);
    }

    /**
     * 查询所有未删除城市
     *
     * @return
     */
    @Override
    public List<CityVO> quaryCityList() {
        Example example = new Example(OrbitResCity.class);
        example.orderBy("name");
        example.createCriteria().andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        List<OrbitResCity> list = cityMapper.selectByExample(example);
        List<CityVO> cityVOS = new ArrayList<>();
        for (OrbitResCity orbitResCity : list) {
            CityVO cityVO = new CityVO();
            BeanUtils.copyProperties(orbitResCity, cityVO);
            cityVO.setCanDelete(true);
            //城市下有区域，不能删除
            List<AreaVO> areaList = this.quaryAreaList(orbitResCity.getId());
            if (areaList.size() > 0) {
                cityVO.setCanDelete(false);
            }
            cityVOS.add(cityVO);
        }
        return cityVOS;
    }

    /**
     * 查询城市下面所有区域
     *
     * @param cityId 城市主键
     * @return
     */
    @Override
    public List<AreaVO> quaryAreaList(String cityId) {
        Example example = new Example(OrbitResArea.class);
        example.orderBy("name");
        if (StringUtils.isNotBlank(cityId)) {
            example.createCriteria().andEqualTo("cityId", cityId).andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        }
        List<OrbitResArea> list = areaMapper.selectByExample(example);
        List<AreaVO> areaVOS = new ArrayList<>();
        for (OrbitResArea orbitResArea : list) {
            AreaVO areaVO = new AreaVO();
            BeanUtils.copyProperties(orbitResArea, areaVO);
            areaVO.setCanDelete(true);
            List<RoadVO> roadList = this.queryRoadList(orbitResArea.getId());
            if (roadList.size() > 0) {
                areaVO.setCanDelete(false);
            }
            areaVOS.add(areaVO);
        }
        return areaVOS;
    }

    /**
     * 查询区域下面所有路段
     *
     * @param areaId 区域ID
     * @return
     */
    @Override
    public List<RoadVO> queryRoadList(String areaId) {
        Example example = new Example(OrbitResRoadcrossPoint.class);
        example.orderBy("name");
        if (StringUtils.isNotBlank(areaId)) {
            example.createCriteria().andEqualTo("areaId", areaId).andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        }
        List<OrbitResRoadcrossPoint> list = roadMapper.selectByExample(example);
        List<RoadVO> roadVOS = new ArrayList<>();
        for (OrbitResRoadcrossPoint orbitResRoadcrossPoint : list) {
            RoadVO roadVO = new RoadVO();
            BeanUtils.copyProperties(orbitResRoadcrossPoint, roadVO);
            roadVO.setCanDelete(true);
            OrbitResDeviceExtends device = new OrbitResDeviceExtends();
            device.setRoadCrossPointId(orbitResRoadcrossPoint.getId());
            List<DeviceSimpleVO> devices = deviceMapper.queryAllList(device);
            if (devices.size() > 0) {
                roadVO.setCanDelete(false);
            }
            roadVOS.add(roadVO);
        }
        return roadVOS;
    }

    /**
     * 根据路口查询行政区划信息
     *
     * @param roadId
     * @return
     */
    @Override
    public List<DivisionSimpleVO> queryDivisionSimpleVO(String roadId) {
        return roadMapper.queryDivisionSimpleVO(roadId);
    }

    /**
     * 修改城市
     *
     * @param city
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_CITY, actionType = OrbitServiceConstant.ACTION_UPDATE)
    @Override
    public void updateCity(OrbitResCity city) {
        cityMapper.updateByPrimaryKeySelective(city);
    }

    /**
     * 修改区域
     *
     * @param area
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_AREA, actionType = OrbitServiceConstant.ACTION_UPDATE)
    @Override
    public void updateArea(OrbitResArea area) {
        areaMapper.updateByPrimaryKeySelective(area);
    }

    /**
     * 修改路口
     *
     * @param road
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_ROAD, actionType = OrbitServiceConstant.ACTION_UPDATE)
    @Override
    public void updateRoad(OrbitResRoadcrossPoint road) {
        roadMapper.updateByPrimaryKeySelective(road);
    }

    /**
     * 删除城市
     *
     * @param city
     */
    //@Transactional
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_CITY, actionType = OrbitServiceConstant.ACTION_DELETE, mapperType = OrbitResCityMapper.class)
    @Override
    public void deleteCity(OrbitResCity city) throws RelationshipException {
        Example example = new Example(OrbitSysMonitorCenter.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("levelId", "%" + city.getId())
                .andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        if (monitorCenterMapper.selectCountByExample(example) > 0) {
            throw new RelationshipException("Bind with monitor center");
        }

        criteria = example.createCriteria();
        criteria.andLike("cityId", "%" + city.getId() + "%")
                .andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        List<OrbitSysMonitorCenter> monitorCenterList = monitorCenterMapper.selectByExample(example);
        monitorCenterList.stream().forEach(orbitSysMonitorCenter -> {
            List<String> idList = new ArrayList<>();
            List<String> nameList = new ArrayList<>();

            String []ids = orbitSysMonitorCenter.getCityId().split(",");
            String []names = orbitSysMonitorCenter.getCityName().split(",");
            for (int i = 0; i < ids.length; i++) {
                if (!ids[i].equals(city.getId())) {
                    idList.add(ids[i]);
                    nameList.add(names[i]);
                }
            }

            orbitSysMonitorCenter.setCityId(convert(idList));
            orbitSysMonitorCenter.setCityName(convert(nameList));
            monitorCenterMapper.updateByPrimaryKey(orbitSysMonitorCenter);
        });

        city.setDeleted(HasDeleteEnum.YES.getValue());
        cityMapper.updateByPrimaryKeySelective(city);
    }

    private String convert(List<String> list) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i != (list.size() - 1)) {
                sb.append(",");
            }
        }

        return sb.toString();
    }

    /**
     * 删除区域
     *
     * @param area
     */
    //@Transactional
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_AREA, actionType = OrbitServiceConstant.ACTION_DELETE, mapperType = OrbitResAreaMapper.class)
    @Override
    public Map<String, Object> deleteArea(OrbitResArea area) throws RelationshipException {
        //判断区域下是否有路口
        List<RoadVO> roadVOS = this.queryRoadList(area.getId());
        if (roadVOS.size() > 0) {
            throw new RelationshipException("There are roads under the area");
        }

        /** 是否关联机构 **/
        Example example = new Example(OrbitSysMonitorCenter.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("levelId", "%" + area.getId())
                .andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        if (monitorCenterMapper.selectCountByExample(example) > 0) {
            throw new RelationshipException("Bind with monitor center");
        }

        criteria = example.createCriteria();
        criteria.andLike("areaId", "%" + area.getId() + "%")
                .andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        List<OrbitSysMonitorCenter> monitorCenterList = monitorCenterMapper.selectByExample(example);
        monitorCenterList.stream().forEach(orbitSysMonitorCenter -> {
            List<String> idList = new ArrayList<>();
            List<String> nameList = new ArrayList<>();

            String []ids = orbitSysMonitorCenter.getAreaId().split(",");
            String []names = orbitSysMonitorCenter.getAreaName().split(",");
            for (int i = 0; i < ids.length; i++) {
                if (!ids[i].equals(area.getId())) {
                    idList.add(ids[i]);
                    nameList.add(names[i]);
                }
            }

            orbitSysMonitorCenter.setAreaId(convert(idList));
            orbitSysMonitorCenter.setAreaName(convert(nameList));
            monitorCenterMapper.updateByPrimaryKey(orbitSysMonitorCenter);
        });

        area.setDeleted(HasDeleteEnum.YES.getValue());
        areaMapper.updateByPrimaryKeySelective(area);

        //删除后，包装前段所需数据
        OrbitResArea orbitResArea = areaMapper.selectByPrimaryKey(area.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("id", orbitResArea.getCityId());
        map.put("canDelete", false);
        example = new Example(OrbitResArea.class);
        example.createCriteria().andEqualTo("cityId", orbitResArea.getCityId()).andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        List<OrbitResArea> checkList = areaMapper.selectByExample(example);
        if (checkList.size() == 0) {
            map.put("id", orbitResArea.getCityId());
            map.put("canDelete", true);
        }
        return map;
    }

    /**
     * 删除路口
     *
     * @param road
     */
    //@Transactional
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_ROAD, actionType = OrbitServiceConstant.ACTION_DELETE, mapperType = OrbitResRoadcrossPointMapper.class)
    @Override
    public Map<String, Object> deleteRoad(OrbitResRoadcrossPoint road) throws RelationshipException {
        //判断路口下是否有设备
        OrbitResDeviceExtends device = new OrbitResDeviceExtends();
        device.setRoadCrossPointId(road.getId());
        List<DeviceSimpleVO> devices = deviceMapper.queryAllList(device);
        if (devices.size() > 0) {
            throw new RelationshipException("There are devices under this road");
        }

        /** 是否关联机构 **/
        Example example = new Example(OrbitSysMonitorCenter.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("levelId", "%" + road.getId())
                .andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        if (monitorCenterMapper.selectCountByExample(example) > 0) {
            throw new RelationshipException("Bind with monitor center");
        }

        example = new Example(OrbitSysMonitorCenter.class);
        criteria = example.createCriteria();
        criteria.andLike("roadId", "%" + road.getId() + "%")
                .andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        List<OrbitSysMonitorCenter> monitorCenterList = monitorCenterMapper.selectByExample(example);
        monitorCenterList.stream().forEach(orbitSysMonitorCenter -> {
            List<String> idList = new ArrayList<>();
            List<String> nameList = new ArrayList<>();

            String []ids = orbitSysMonitorCenter.getRoadId().split(",");
            String []names = orbitSysMonitorCenter.getRoadName().split(",");
            for (int i = 0; i < ids.length; i++) {
                if (!ids[i].equals(road.getId())) {
                    idList.add(ids[i]);
                    nameList.add(names[i]);
                }
            }

            orbitSysMonitorCenter.setRoadId(convert(idList));
            orbitSysMonitorCenter.setRoadName(convert(nameList));
            monitorCenterMapper.updateByPrimaryKey(orbitSysMonitorCenter);
        });

        //删除
        road.setDeleted(HasDeleteEnum.YES.getValue());
        roadMapper.updateByPrimaryKeySelective(road);

        //删除后，包装前段所需数据
        OrbitResRoadcrossPoint roadcrossPoint = roadMapper.selectByPrimaryKey(road.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("id", roadcrossPoint.getAreaId());
        map.put("canDelete", false);
        example = new Example(OrbitResRoadcrossPoint.class);
        example.createCriteria().andEqualTo("areaId", roadcrossPoint.getAreaId()).andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        List<OrbitResRoadcrossPoint> checkList = roadMapper.selectByExample(example);
        if (checkList.size() == 0) {
            map.put("id", roadcrossPoint.getAreaId());
            map.put("canDelete", true);
        }
        return map;
    }

    /**
     * 城市名查重
     *
     * @param name
     * @return true 已经存在
     */
    @Override
    public Boolean hasCityName(String name, String id) {
        Example example = new Example(OrbitResCity.class);
        if (StringUtils.isNotBlank(id)) {
            example.createCriteria().andEqualTo("name", name).andNotEqualTo("id", id).andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        } else {
            example.createCriteria().andEqualTo("name", name).andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        }
        int count = cityMapper.selectCountByExample(example);
        return count > 0;
    }

    /**
     * 城市行政区域编码查重
     *
     * @param code
     * @param id
     * @return
     */
    @Override
    public Boolean hasCityAdminRegionCode(String code, String id) {
        Example example = new Example(OrbitResCity.class);
        if (StringUtils.isNotBlank(id)) {
            example.createCriteria().andEqualTo("adminRegionCode", code).andNotEqualTo("id", id).andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        } else {
            example.createCriteria().andEqualTo("adminRegionCode", code).andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        }
        int count = cityMapper.selectCountByExample(example);
        return count > 0;
    }

    /**
     * 路口名查重
     *
     * @param name
     * @param id
     * @param areaId
     * @return
     */
    @Override
    public Boolean hasRoadName(String name, String id, String areaId) {
        Example example = new Example(OrbitResRoadcrossPoint.class);
        if (StringUtils.isNotBlank(id)) {
            example.createCriteria().andEqualTo("name", name)
                    .andNotEqualTo("id", id)
                    .andEqualTo("areaId", areaId)
                    .andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        } else {
            example.createCriteria().andEqualTo("name", name)
                    .andEqualTo("areaId", areaId)
                    .andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        }
        int count = roadMapper.selectCountByExample(example);
        return count > 0;
    }

    /**
     * 区域名查重
     *
     * @param name
     * @return true 已经存在
     */
    @Override
    public Boolean hasAreaName(String name, String id, String cityId) {
        Example example = new Example(OrbitResArea.class);
        if (StringUtils.isNotBlank(id)) {
            example.createCriteria()
                    .andEqualTo("name", name)
                    .andNotEqualTo("id", id)
                    .andEqualTo("cityId", cityId)
                    .andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        } else {
            example.createCriteria()
                    .andEqualTo("name", name)
                    .andEqualTo("cityId", cityId)
                    .andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        }
        int count = areaMapper.selectCountByExample(example);
        return count > 0;
    }

    /**
     * 区域行政区域编码查重
     *
     * @param code
     * @param id
     * @return
     */
    @Override
    public Boolean hasAreaAdminRegionCode(String code, String id) {
        Example example = new Example(OrbitResArea.class);
        if (StringUtils.isNotBlank(id)) {
            example.createCriteria()
                    .andEqualTo("adminRegionCode", code)
                    .andNotEqualTo("id", id)
                    .andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        } else {
            example.createCriteria()
                    .andEqualTo("adminRegionCode", code)
                    .andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        }
        int count = areaMapper.selectCountByExample(example);
        return count > 0;
    }

    /**
     * 获取树形组织机构
     *
     * @return
     */
    @Override
    public List<DeviceTreeNode> getDeviceTree() {
        //加入用户权限限制
        OrbitResDeviceExtends orbitResDeviceExtends = iUserService.getUserAuthority();

        //连表查询出所有设备和地址信息
        List<DeviceTreeVO> datas = deviceMapper.queryTreeData(orbitResDeviceExtends);

        List<DeviceTreeResponseVO> devices = new ArrayList<>();

        List<OrbitResCity> citys = new ArrayList<>();
        List<OrbitResArea> areas = new ArrayList<>();
        List<OrbitResRoadcrossPoint> roads = new ArrayList<>();

        for (DeviceTreeVO data : datas) {
            //组装数据，用于遍历
            citys.add(constructCity(data));
            areas.add(constructArea(data));
            roads.add(constructRoad(data));
            devices.add(fillDeviceInfo(data));
        }

        //去重
        citys = distinctCity(citys);
        areas = distinctArea(areas);
        roads = distinctRoad(roads);

        //遍历构造树
        List<DeviceTreeNode> firstNods = new ArrayList<>();
        for (OrbitResCity city : citys) {
            int firstCount = 0;
            DeviceTreeNode firstNode = new DeviceTreeNode();
            firstNode.setParentId(null);
            firstNode.setId(city.getId());
            firstNode.setLabel(city.getName());

            List<DeviceTreeNode> secondNodes = new ArrayList<>();
            for (OrbitResArea orbitResArea : areas) {
                if (orbitResArea.getCityId().equals(city.getId())) {
                    int secontCount = 0;
                    DeviceTreeNode secondNode = new DeviceTreeNode();
                    secondNode.setParentId(city.getId());
                    secondNode.setId(orbitResArea.getId());
                    secondNode.setLabel(orbitResArea.getName());

                    List<DeviceTreeNode> thirdNodes = new ArrayList<>();
                    for (OrbitResRoadcrossPoint roadcrossPoint : roads) {
                        if (roadcrossPoint.getAreaId().equals(orbitResArea.getId())) {
                            DeviceTreeNode thirdNode = new DeviceTreeNode();
                            thirdNode.setParentId(orbitResArea.getId());
                            thirdNode.setId(roadcrossPoint.getId());
                            thirdNode.setLabel(roadcrossPoint.getName());

                            List<DeviceTreeResponseVO> returnDevices = new ArrayList<>();
                            for (DeviceTreeResponseVO device : devices) {
                                if (device.getRoadId().equals(roadcrossPoint.getId())) {
                                    returnDevices.add(device);
                                }
                            }
                            thirdNode.setCount(returnDevices.size());
                            thirdNode.setChildren(returnDevices);
                            secontCount += returnDevices.size();
                            thirdNodes.add(thirdNode);
                        }
                    }
                    secondNode.setCount(secontCount);
                    secondNode.setChildren(thirdNodes);
                    firstCount += secontCount;
                    secondNodes.add(secondNode);
                }
            }
            firstNode.setCount(firstCount);
            firstNode.setChildren(secondNodes);
            firstNods.add(firstNode);
        }

        return firstNods;
    }


    /**
     * 组装城市
     *
     * @param data
     * @return
     */
    public OrbitResCity constructCity(DeviceTreeVO data) {
        OrbitResCity orbitResCity = new OrbitResCity();
        orbitResCity.setId(data.getCityId());
        orbitResCity.setName(data.getCityName());
        return orbitResCity;
    }

    /**
     * 组装区域
     *
     * @param data
     * @return
     */
    public OrbitResArea constructArea(DeviceTreeVO data) {
        OrbitResArea orbitResArea = new OrbitResArea();
        orbitResArea.setId(data.getAreaId());
        orbitResArea.setName(data.getAreaName());
        orbitResArea.setCityId(data.getCityId());
        return orbitResArea;
    }

    /**
     * 组装路口
     *
     * @param data
     * @return
     */
    public OrbitResRoadcrossPoint constructRoad(DeviceTreeVO data) {
        OrbitResRoadcrossPoint roadcrossPoint = new OrbitResRoadcrossPoint();
        roadcrossPoint.setId(data.getRoadCrossPointId());
        roadcrossPoint.setName(data.getRoadName());
        roadcrossPoint.setAreaId(data.getAreaId());
        return roadcrossPoint;
    }

    /**
     * 城市去重
     *
     * @param list
     * @return
     */
    public static List<OrbitResCity> distinctCity(List<OrbitResCity> list) {
        List<String> ids = new ArrayList<>();//用来临时存储id
        return list.stream().filter(v -> {
            boolean flag = !ids.contains(v.getId());
            ids.add(v.getId());
            return flag;
        }).collect(Collectors.toList());
    }

    /**
     * 区域去重
     *
     * @param list
     * @return
     */
    public static List<OrbitResArea> distinctArea(List<OrbitResArea> list) {
        List<String> ids = new ArrayList<>();//用来临时存储id
        return list.stream().filter(v -> {
            boolean flag = !ids.contains(v.getId());
            ids.add(v.getId());
            return flag;
        }).collect(Collectors.toList());
    }

    /**
     * 路口去重
     *
     * @param list
     * @return
     */
    public static List<OrbitResRoadcrossPoint> distinctRoad(List<OrbitResRoadcrossPoint> list) {
        List<String> ids = new ArrayList<>();//用来临时存储id
        return list.stream().filter(v -> {
            boolean flag = !ids.contains(v.getId());
            ids.add(v.getId());
            return flag;
        }).collect(Collectors.toList());
    }

    /**
     * 组装设备信息
     *
     * @param data
     * @return
     */
    private DeviceTreeResponseVO fillDeviceInfo(DeviceTreeVO data) {
        DeviceTreeResponseVO deviceTreeResponseVO = new DeviceTreeResponseVO();
        deviceTreeResponseVO.setId(data.getDeviceId());
        deviceTreeResponseVO.setLabel(data.getDeviceName());
        deviceTreeResponseVO.setLatitude(data.getLatitude());
        deviceTreeResponseVO.setLongitude(data.getLongitude());
        deviceTreeResponseVO.setOnline(data.getOnline());
        deviceTreeResponseVO.setRoadId(data.getRoadCrossPointId());
        return deviceTreeResponseVO;
    }
}

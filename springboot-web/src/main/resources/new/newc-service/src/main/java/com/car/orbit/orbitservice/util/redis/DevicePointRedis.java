package com.car.orbit.orbitservice.util.redis;

import com.car.orbit.orbitservice.entity.OrbitResArea;
import com.car.orbit.orbitservice.entity.OrbitResCity;
import com.car.orbit.orbitservice.entity.OrbitResDevice;
import com.car.orbit.orbitservice.entity.OrbitResRoadcrossPoint;
import com.car.orbit.orbitutil.tools.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 设备点位信息缓存
 * @author zks
 */
public class DevicePointRedis extends BaseBusinessRedis {

    /**
     * 获取所有
     * @return
     */
    public static List<OrbitResDevice> getAll(){
        return redisClient.getValues(DIC_DEVICE_KEY,OrbitResDevice.class);
    }

    /**
     * 根据code获取对象
     * @param code
     * @return
     */
    public static OrbitResDevice getDevicePointByCode(String code) {
        String value = redisClient.get(DIC_DEVICE_KEY).get(code);
        if (!StringUtils.isEmpty(value)) {
            OrbitResDevice vo = JsonUtils.toBean(value, OrbitResDevice.class);
            return vo;
        }else{
            return null;
        }
    }

    /**
     * 获取设备全路径的名称   城市=区域=路口
     * @param code
     * @return
     */
    public static String getFullPositionName(String code) {
        String fullPositionName = "";
        OrbitResDevice device = getDevicePointByCode(code);
        if(device!=null){
            OrbitResRoadcrossPoint road = RoadcrossPointRedis.getRoadcrossPointByCode(device.getRoadCrossPointId());
            if(road!=null){
                OrbitResArea area = AreaRedis.getAreaByCode(road.getAreaId());
                if(area!=null){
                    OrbitResCity city = CityRedis.getByCode(area.getCityId());
                    fullPositionName = city.getName()+"-"+area.getName()+"-"+road.getName();
                }
            }
        }
        return fullPositionName;
    }

    /**
     * 根据deviceIds获取对应的路口Id列表
     * @param deviceIds
     * @return
     */
    public static List<String> getRoadCrossPointIdListByDeviceIds(List<String> deviceIds) {
        List<OrbitResDevice> list = getAll();
        if(list != null ){
            Map<String,List<OrbitResDevice>> recordMap = list.stream().filter((OrbitResDevice d) -> deviceIds.contains(d.getId())).collect(Collectors.groupingBy(OrbitResDevice::getRoadCrossPointId));
            if(recordMap != null){
                return new ArrayList<>(recordMap.keySet());
            }
        }
        return null;
    }

    /**
     * 根据逗号切割的id获取设备对象
     * @param devices
     */
    public static List<OrbitResDevice> getDevicesByIds(String devices) {
        List<OrbitResDevice> result = new ArrayList<>();
        if(StringUtils.isNotEmpty(devices)){
            String[] array = devices.split(",");
            result = getDevicesByIds(Arrays.asList(array));
        }
        return result;
    }

    /**
     * 根据列表id获取设备对象
     * @param ids
     */
    public static List<OrbitResDevice> getDevicesByIds(List<String> ids) {
        List<OrbitResDevice> result = new ArrayList<>();
        if(ids != null && ids.size() > 0){
            for(String code : ids){
                OrbitResDevice device = getDevicePointByCode(code);
                result.add(device);
            }
        }
        return result;
    }

    /**
     * 根据code获取名称
     * @param code
     * @return
     */
    public static String getNameByCode(String code) {
        OrbitResDevice point = getDevicePointByCode(code);
        if(point==null){
            return null;
        }
        return point.getName();
    }

    /**
     * 保存对象
     * @param vo
     */
    public static void saveVo(OrbitResDevice vo) {
        redisClient.save(DIC_DEVICE_KEY, vo.getId(), JsonUtils.toJSONString(vo));
    }

    /**
     * 保存列表
     * @param list
     */
    public static void saveVoList(List<OrbitResDevice> list) {
        if(list == null ){
            return;
        }
        for(OrbitResDevice vo : list){
            saveVo(vo);
        }
    }

}

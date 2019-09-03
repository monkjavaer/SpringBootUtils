package com.base.springboot.car.CenterService.src.main.java.com.car.trunk.center.device.service;

import com.alibaba.fastjson.JSON;
import com.car.base.common.constant.AppConstant;
import com.car.base.redis.RedisHelper;
import com.car.trunk.dal.device.vo.DeviceRedisVO;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * @Title: DeviceInitUtils
 * @Package: com.car.trunk.center.device.service
 * @Description: 将所有电子警察放入rides
 * @Author: monkjavaer
 * @Date: 2019/6/12 17:56
 * @Version: V1.0
 */
public class DeviceInitUtils {

    /**
     * 将所有电子警察放入rides
     *
     * @param deviceService DeviceService接口
     */
    public static void initDeviceToRedis(DeviceService deviceService) throws Exception{
        List<DeviceRedisVO> deviceList = deviceService.queryDevicetoRedis();
        for (DeviceRedisVO deviceRedisVO : deviceList) {
            if (StringUtils.isNotBlank(deviceRedisVO.getBayonetNo())) {
                RedisHelper.save(AppConstant.DEVICE_REDIS_KEY, deviceRedisVO.getBayonetNo(), JSON.toJSONString(deviceRedisVO));
            }
        }
    }

}

package com.car.orbit.orbitservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.car.orbit.orbitservice.entity.OrbitResDevice;
import com.car.orbit.orbitservice.mapper.OrbitResDeviceMapper;
import com.car.orbit.orbitutil.tools.PropertyReaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @Title: ScheduledTask
 * @Package: com.car.orbit.orbitservice
 * @Description: 定时任务，用于更新设备状态
 * @Author: monkjavaer
 * @Date: 2019/05/15 14:48
 * @Version: V1.0
 */
@Component
public class ScheduledTask {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    @Autowired
    private RestTemplate restTemplate;

//    @Autowired
//    private IDeviceService deviceService;

    @Autowired
    private OrbitResDeviceMapper deviceMapper;

    /**
     * 从结构化服务器获取设备状态并更新到MYSQL数据库
     */
    @Scheduled(fixedRate = 60000)
    public void queryDeviceStatus() {
        String url = new StringBuilder()
                .append("http://")
                .append(PropertyReaderUtils.getProValue("admin.server.ip"))
                .append(":")
                .append(PropertyReaderUtils.getProValue("admin.server.port"))
                .append("/algo/analyse/status").toString();
        JSONObject postData = new JSONObject();
        JSONObject json = restTemplate.postForEntity(url, postData, JSONObject.class).getBody();
        JSONArray jsonArray = json.getJSONArray("data");

        for(int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            String deviceId = jsonObject.getString("deviceId");
            int deviceStatus = jsonObject.getIntValue("deviceStatus");

            logger.info("设备[" + deviceId + "] 状态:" + deviceStatus);

            /** 更新MYSQL数据库 **/
            OrbitResDevice device = new OrbitResDevice();
            device.setId(deviceId);
            device.setOnline(deviceStatus);
            deviceMapper.updateByPrimaryKeySelective(device);
        }
    }
}

package com.car.orbit.orbitservice.util.redis;

import com.car.orbit.orbitservice.bo.ControlTaskBO;
import com.car.orbit.orbitservice.entity.OrbitControlTask;
import com.car.orbit.orbitservice.entity.OrbitControlTaskDetail;
import com.car.orbit.orbitservice.util.ControlTaskDetailMatcher;
import com.car.orbit.orbitservice.util.ControlTaskMatcher;
import com.car.orbit.orbitutil.tools.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 当前布控中任务列表缓存
 * @author zks
 */
public class OnControlRedis extends BaseBusinessRedis {
    protected final static String LOCK = "OnControlRedis";
    protected final static int EXPIRETIME = 10*1000;
    /**
     * 根据id获取对应布控中任务
     * @param taskId
     * @return
     */
    public static ControlTaskBO getBoByVid(String taskId) {
        String value = redisClient.get(ON_CONTROL_KEY).get(taskId);
        if (!StringUtils.isEmpty(value)) {
            ControlTaskBO bo = JsonUtils.toBean(value, ControlTaskBO.class);
            return bo;
        }else{
            return null;
        }
    }
    /**
     * 根据id获取对应匹配对象
     * @param taskId
     * @return
     */
    public static ControlTaskMatcher getMatcherByVid(String taskId) {
        String value = redisClient.get(ON_CONTROL_MATCHER_KEY).get(taskId);
        if (!StringUtils.isEmpty(value)) {
            ControlTaskMatcher matcher = JsonUtils.toBean(value, ControlTaskMatcher.class);
            return matcher;
        }else{
            return null;
        }
    }
    /**
     * 获取所有匹配对象
     * @return
     */
    public static List<ControlTaskMatcher> getAllMatcher(){
        List<ControlTaskMatcher> list = new ArrayList<>();
        list = redisClient.getValues(ON_CONTROL_MATCHER_KEY,ControlTaskMatcher.class);
        return list;
    }

    /**
     * 移除布控中任务
     */
    public static boolean removeOnControlTask(String taskId){
//        String requestId = getLock(LOCK,EXPIRETIME);
        redisClient.delete(ON_CONTROL_KEY,taskId);
        return redisClient.delete(ON_CONTROL_MATCHER_KEY,taskId);
//        releaseLock(LOCK,requestId);
    }

    /**
     * 布控中任务
     */
    public static void save(ControlTaskBO bo) {
        if(bo != null && bo.getTask() !=null && bo.getTaskDetails() != null){
            String taskId = bo.getTask().getId();
            saveBo(taskId, bo);

            ControlTaskMatcher matcher = new ControlTaskMatcher(taskId);
            OrbitControlTask task = new OrbitControlTask();
            BeanUtils.copyProperties(bo.getTask(),task);
            String taskDeviceList = task.getDevices();
            List<String> deviceIdList = Arrays.asList(taskDeviceList.split(","));
            for(OrbitControlTaskDetail detail : bo.getTaskDetails()){
                ControlTaskDetailMatcher detailMatcher = new ControlTaskDetailMatcher(detail.getFuzzy(), deviceIdList);
                detailMatcher.addPlateNumber(detail.getPlateNumber());
                detailMatcher.setPlateColor(detail.getPlateColor());
                detailMatcher.setBrand(detail.getVehicleBrand());
                detailMatcher.setChildBrand(detail.getVehicleBrandChild());
                detailMatcher.setVehicleColor(detail.getVehicleColor());
                detailMatcher.setVehicleType(detail.getVehicleType());
                matcher.add(detailMatcher);
            }
            saveMatcher(taskId, matcher);
        }
    }

    private static void saveBo(String taskId, ControlTaskBO bo){
        redisClient.save(ON_CONTROL_KEY, taskId, JsonUtils.toJSONString(bo));
    }

    private static void saveMatcher(String taskId, ControlTaskMatcher matcher){
        redisClient.save(ON_CONTROL_MATCHER_KEY, taskId, JsonUtils.toJSONString(matcher));
    }
}

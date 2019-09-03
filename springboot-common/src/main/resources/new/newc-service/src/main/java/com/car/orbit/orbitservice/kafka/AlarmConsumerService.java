package com.car.orbit.orbitservice.kafka;

import com.car.orbit.orbitservice.entity.*;
import com.car.orbit.orbitservice.enums.AlarmStatusEnum;
import com.car.orbit.orbitservice.enums.HasDeleteEnum;
import com.car.orbit.orbitservice.kafka.message.AlarmMessage;
import com.car.orbit.orbitservice.mapper.OrbitControlBlacklistMapper;
import com.car.orbit.orbitservice.mapper.OrbitControlBlacklistTypeMapper;
import com.car.orbit.orbitservice.mapper.OrbitControlTaskMapper;
import com.car.orbit.orbitservice.mapper.OrbitSysUserMapper;
import com.car.orbit.orbitservice.service.IAlarmService;
import com.car.orbit.orbitservice.service.ISysVariableService;
import com.car.orbit.orbitservice.util.EmailUtil;
import com.car.orbit.orbitservice.util.redis.*;
import com.car.orbit.orbitservice.websocket.UserInfo;
import com.car.orbit.orbitservice.websocket.WebSocketManager;
import com.car.orbit.orbitservice.websocket.message.AlarmMessageBody;
import com.car.orbit.orbitservice.websocket.message.MessageType;
import com.car.orbit.orbitservice.websocket.message.WebSocketMsg;
import com.car.orbit.orbitutil.kafka.ConsumerInterface;
import com.car.orbit.orbitutil.tools.DateUtils;
import com.car.orbit.orbitutil.tools.JsonUtils;
import com.car.orbit.orbitutil.tools.RedisClient;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @Title: AlarmConsumerService
 * @Package: com.car.orbit.orbitutil.kafka
 * @Description: 获取kafaka报警数据
 * @Author: monkjavaer
 * @Data: 2019/4/2 19:45
 * @Version: V1.0
 */
@Service
public class AlarmConsumerService implements ConsumerInterface {

    public static final String KEY = "AlarmEmail";
    @Autowired
    private IAlarmService alarmService;

    /**
     * Webscoket发送消息服务
     */
    @Autowired
    private WebSocketManager webSocketManager;

    @Autowired
    private OrbitControlTaskMapper taskMapper;

    @Autowired
    private OrbitControlBlacklistMapper blacklistMapper;

    @Autowired
    private OrbitControlBlacklistTypeMapper blacklistTypeMapper;

    @Autowired
    private OrbitSysUserMapper userMapper;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private ThreadPoolTaskExecutor asyncServiceExecutor;

    @Autowired
    private ISysVariableService sysVariableService;

    /**
     * 获取kafaka报警数据
     *
     * @param msg 消费者接收到的来自生产者的String数据
     */
    @Override
    public void consumer(String msg) {
        System.out.println("get" + msg);
        AlarmMessage message = JsonUtils.toBean(msg, AlarmMessage.class);
        //预警入库
        OrbitControlAlarm alarm = getOrbitControlAlarm(message);
        alarmService.insertAlarm(alarm);

        //websocket连接的用户
        Collection<UserInfo> userinfos = WebSocketManager.USERS.values();

        List<OrbitSysUser> userList;
        if (!StringUtils.isEmpty(message.getBlacklistId())) {
            //预警信息通过websocket推送至前端
            for (UserInfo userinfo : userinfos) {
                List<String> usernames = new ArrayList<>();
                usernames.add(userinfo.getUsername());
                WebSocketMsg wesocketMessage = getWebSocketMsg(alarm, message, userinfo, true);
                webSocketManager.sendMessageToUsers(wesocketMessage, usernames);
            }
            /** 黑名单预警-需要向所有用户发送邮件提醒 **/
            Example example = new Example(OrbitSysUser.class);
            example.createCriteria()
                    .andEqualTo("deleted", HasDeleteEnum.NO.getValue());
            userList = userMapper.selectByExample(example);

            sendAlarmEmail(userList, alarm.getId(), generateEmailContent(message));
        } else {
            /** 布控任务预警-需要向任务的接收用户发送邮件提醒 **/
            OrbitControlTask task = taskMapper.selectByPrimaryKey(message.getTaskId());
            List<String> userIdList = Arrays.asList(task.getReceivorId().split(","));
            Example example = new Example(OrbitSysUser.class);
            example.createCriteria()
                    .andIn("id", userIdList);
            userList = userMapper.selectByExample(example);

            Integer messageType = task.getMessageType();

            /** 不管用户设没设置弹窗，都推送消息通知,如果用户配置弹窗消息通知，则增加铃声 **/
            boolean isBell = false;
            if ((messageType & 0b0010) > 0) {
                isBell = true;
            }
            for (OrbitSysUser user : userList) {
                for (UserInfo userinfo : userinfos) {
                    if (user.getUsername().equals(userinfo.getUsername())) {
                        List<String> usernames = new ArrayList<>();
                        usernames.add(userinfo.getUsername());
                        WebSocketMsg wesocketMessage = getWebSocketMsg(alarm, message, userinfo, isBell);
                        webSocketManager.sendMessageToUsers(wesocketMessage, usernames);
                        break;
                    }
                }
            }

            /** 用户配置了邮件通知 **/
            if ((messageType & 0b0001) > 0) {
                sendAlarmEmail(userList, alarm.getId(), generateEmailContent(message));
            }
        }
    }


    /**
     * 组装websocket消息
     *
     * @param message AlarmMessage
     * @return WebSocketMsg
     */
    private WebSocketMsg getWebSocketMsg(OrbitControlAlarm alarm, AlarmMessage message, UserInfo userinfo, boolean isBell) {
        AlarmMessageBody alarmMessageBody = new AlarmMessageBody();
        if (StringUtils.isNotBlank(message.getTaskId())) {
            OrbitControlTask task = taskMapper.selectByPrimaryKey(message.getTaskId());
            alarmMessageBody.setLevel(task.getTaskLevel());
            alarmMessageBody.setTaskName(task.getTaskName());
        }
        if (StringUtils.isNotBlank(message.getBlacklistId())) {
            //黑名单的主键即vid
            OrbitControlBlacklist blacklist = BlacklistRedis.getByVid(message.getBlacklistId());
            OrbitControlBlacklistType blacklistType = blacklistTypeMapper.selectByPrimaryKey(blacklist.getType());
            alarmMessageBody.setLevel(blacklistType.getLevel());
        }

        OrbitControlAlarmVoice voice = sysVariableService.queryVoiceByLevel(alarmMessageBody.getLevel());
        if (isBell) {
            alarmMessageBody.setBell(voice.getBell());
        }else{
            alarmMessageBody.setBell(9999);
        }
        alarmMessageBody.setVehicleColor(message.getVehicleColor());
        alarmMessageBody.setVehicleType(message.getVehicleType());

        OrbitResDevice device = DevicePointRedis.getDevicePointByCode(alarm.getDeviceId());
        alarmMessageBody.setStatus(String.valueOf(alarm.getStatus()));
        alarmMessageBody.setAlarmId(alarm.getId());
        alarmMessageBody.setCityName(alarm.getCityName());
        alarmMessageBody.setAreaName(alarm.getAreaName());
        alarmMessageBody.setRoadName(alarm.getRoadName());
        alarmMessageBody.setDeviceId(device.getId());
        alarmMessageBody.setDeviceType(device.getType());
        alarmMessageBody.setDeviceName(device.getName());
        alarmMessageBody.setLongitude(device.getLongitude());
        alarmMessageBody.setLatitude(device.getLatitude());
        alarmMessageBody.setPlateNumber(message.getPlateNumber());
        alarmMessageBody.setCaptureTime(message.getCaptureTime());
        alarmMessageBody.setPassVehicleId(message.getPassVehicleId());
        alarmMessageBody.setUrl(message.getPhotoFastdfsUrl());
        String token = userinfo.getToken();
        alarmMessageBody.setFullBrand(BrandRedis.getBrandNameByCodeAndToken(message.getVehicleBrand(), token)
                + "-" + BrandRedis.getChildBrandNameByCodeAndToken(message.getVehicleBrandChild(), token));
        WebSocketMsg webSocketMsg = new WebSocketMsg();
        webSocketMsg.setType(MessageType.alarm);
        webSocketMsg.setBody(alarmMessageBody);
        return webSocketMsg;
    }

    /**
     * 组装 OrbitControlAlarm
     *
     * @param message AlarmMessage
     * @return OrbitControlAlarm
     */
    private OrbitControlAlarm getOrbitControlAlarm(AlarmMessage message) {
        OrbitControlAlarm alarm = new OrbitControlAlarm();
        alarm.setId(message.getAlarmId());
        alarm.setTaskId(message.getTaskId());
        alarm.setDeviceId(message.getDeviceId());
        alarm.setRoadName(message.getRoadwayName());
        alarm.setRoadwayNo(message.getRoadwayNo());
        alarm.setCaptureTime(DateUtils.getDate(message.getCaptureTime()));
        alarm.setPlateNumber(message.getPlateNumber());
        alarm.setAlarmTime(DateUtils.getDate(message.getAlarmTime()));
        alarm.setPassVehicleId(message.getPassVehicleId());
        alarm.setPhotoFastdfsUrl(message.getPhotoFastdfsUrl());
        alarm.setBlacklistId(message.getBlacklistId());
        alarm.setCityId(message.getCityId());
        if (StringUtils.isNotBlank(message.getCityId())) {
            alarm.setCityName(CityRedis.getNameByCode(message.getCityId()));
        }
        alarm.setAreaId(message.getAreaId());
        if (StringUtils.isNotBlank(message.getAreaId())) {
            alarm.setAreaName(AreaRedis.getNameByCode(message.getAreaId()));
        }
        alarm.setRoadCrossPointId(message.getRoadCrossPointId());
        if (StringUtils.isNotBlank(message.getRoadCrossPointId())) {
            alarm.setRoadName(RoadcrossPointRedis.getNameByCode(message.getRoadCrossPointId()));
        }
        //新收到的预警都是：未确认
        alarm.setStatus(AlarmStatusEnum.NO_ACK.getValue());
        return alarm;
    }

    /**
     * 检查邮件是否已经发送过
     *
     * @param alarmId
     * @return
     */
    private boolean checkEmailSendDuplicate(String alarmId) {
        int expireTime = 30 * 24 * 60 * 60;
        return !redisClient.tryGetLockEx(alarmId, alarmId, expireTime);
    }

    /**
     * 发送邮件
     *
     * @param userList
     * @param alarmId
     */
    private void sendAlarmEmail(List<OrbitSysUser> userList, String alarmId, String content) {
        //userList.stream().forEach(orbitSysUser -> sendAlarmEmail(orbitSysUser, alarmId, content));
        /** 检查Redis,判断是否已经发送过邮件 **/
        if (checkEmailSendDuplicate(alarmId)) {
            return;
        }

        List<OrbitSysUser> validUsers = new ArrayList<>();
        userList.stream().forEach(orbitSysUser -> {
            if (StringUtils.isNotEmpty(orbitSysUser.getEmail())) {
                validUsers.add(orbitSysUser);
            }
        });

        List<List<OrbitSysUser>> parts = Lists.partition(validUsers, 10);
        parts.stream().forEach(list -> sendEmail(list, content));
    }

    /**
     * 发送邮件
     *
     * @param user
     */
    private void sendEmail(List<OrbitSysUser> user, String content) {
        List<String> receivers = new ArrayList<>();
        user.stream().forEach(orbitSysUser -> receivers.add(orbitSysUser.getEmail()));

        asyncServiceExecutor.execute(() -> {
            try {
                EmailUtil.sendHtmlMail(receivers, "车辆布控预警", content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 生成邮件的内容（Html内嵌图片）
     *
     * @param message
     * @return
     */
    private String generateEmailContent(AlarmMessage message) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h3>收到车辆布控预警</h3>");
        sb.append("预警时间:").append(message.getAlarmTime()).append("<br/>");
        if (StringUtils.isEmpty(message.getBlacklistId())) {
            OrbitControlTask task = taskMapper.selectByPrimaryKey(message.getTaskId());
            sb.append("任务名称:").append(task.getTaskName()).append("<br/>");
            sb.append("级别:").append(task.getTaskLevel()).append("<br/>");
        } else {
            OrbitControlBlacklist blacklist = blacklistMapper.selectByPrimaryKey(message.getBlacklistId());
            OrbitControlBlacklistType blacklistType = blacklistTypeMapper.selectByPrimaryKey(blacklist.getType());
            sb.append("任务名称:").append(blacklistType.getName()).append("<br/>");
            sb.append("级别:1\r\n");
        }
        sb.append("车牌号:").append(message.getPlateNumber()).append("<br/>");

        sb.append("<p><img src=\"");
        sb.append(message.getPhotoFastdfsUrl());
        sb.append("\"></p>");

        return sb.toString();
    }
}

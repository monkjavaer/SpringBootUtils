package com.car.orbit.orbitservice.util;

import com.alibaba.fastjson.JSONObject;
import com.car.orbit.orbitservice.vo.DeviceStructuralVO;
import com.car.orbit.orbitutil.tools.HttpUtil;
import com.car.orbit.orbitutil.tools.JsonUtils;
import com.car.orbit.orbitutil.tools.PropertyReaderUtils;
import io.netty.util.internal.StringUtil;

import java.io.IOException;

/**
 * @Title: 请求结构化服务器的工具类
 * @Package: com.car.orbit.orbitservice.util
 * @Description:
 * @Author: @author zks
 * @Date: 2019/03/19 16:51
 * @Version: V1.0
 * 该类主要封装了访问结构化服务器的HTTP请求接口
 */
public class AdminServerUtil {

    private static final String IP_MODE = "ip";
    private static final String DOMAIN_MODE = "domain";

    private static String ip =  PropertyReaderUtils.getProValue("admin.server.ip");
    private static String port =  PropertyReaderUtils.getProValue("admin.server.port");
    private static String domain =  StringUtil.isNullOrEmpty(PropertyReaderUtils.getProValue("admin.server.domain"))?"":PropertyReaderUtils.getProValue("structural.server.domain");
    private static String mode =  StringUtil.isNullOrEmpty(PropertyReaderUtils.getProValue("admin.server.model"))?IP_MODE:PropertyReaderUtils.getProValue("structural.server.model");

    private static String getRealUrl(String url){
        if(mode.equals(IP_MODE)){
            url = "http://"+ip+":"+port+"/algo"+"/"+url;
        }else if(mode.equals(DOMAIN_MODE)){
            url = "http://"+domain+"/algo"+"/"+url;
        }
        return url;
    }


    /**
     * 调用结构化服务器，接收视频流任务
     * @param structuralVO
     * @return
     */
    public static String sendRtsp(DeviceStructuralVO structuralVO){
//        String url = "receive/task/video";
        String url = "analyse/add-task";
        url = AdminServerUtil.getRealUrl(url);
        String result = null;
        try {
            result = HttpUtil.sendPost(url, JsonUtils.toJSONObject(structuralVO));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("send rtsp error");
        }
        System.out.println("send rtsp result:"+result);
        return result;
    }

    /**
     * 调用结构化服务器，接收卡口结构化任务
     * @param structuralVO
     * @return
     */
    public static String sendIPC(DeviceStructuralVO structuralVO){
        String url = "analyse/add-task-ipc";
        url = AdminServerUtil.getRealUrl(url);
        String result = null;
        try {
            result = HttpUtil.sendPost(url, JsonUtils.toJSONObject(structuralVO));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("send ipc error");
        }
        System.out.println("send ipc result:"+result);
        return result;
    }

    /**
     * 删除某个具体的设备任务
     * @param deviceId 设备id
     * @return
     */
    public static String remove(String deviceId){
//        String url = "receive/task/remove";
        String url = "analyse/finish-task";
        url = AdminServerUtil.getRealUrl(url);
        String result = null;
        try {
            JSONObject param = new JSONObject();
            param.put("deviceId",deviceId);
            result = HttpUtil.sendGet(url+"?deviceId="+deviceId);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("remove task error");
        }
        System.out.println("remove task result:"+result);
        return result;
    }

}

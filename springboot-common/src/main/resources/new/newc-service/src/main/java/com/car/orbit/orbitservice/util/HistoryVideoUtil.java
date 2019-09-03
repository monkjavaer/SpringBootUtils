package com.car.orbit.orbitservice.util;

import com.car.orbit.orbitservice.bo.HistoryVideoBO;
import com.car.orbit.orbitservice.constant.OrbitServiceConstant;
import com.car.orbit.orbitutil.tools.DateUtils;
import com.car.orbit.orbitutil.tools.UUIDUtils;
import com.car.vision.result.VideoInfo;
import com.car.vision.result.VideoResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:获取历史视频rtsp
 * Created by monkjavaer on 2018/1/22 0022.
 */
public class HistoryVideoUtil {

    /**
     * @param startTime yyyy-MM-dd HH:mm:ss格式字符串;查找开始时间
     * @param endTime   yyyy-MM-dd HH:mm:ss格式字符串;查找结束时间
     * @param deviceId  设备id
     * @return 历史视频的rtsp地址集合
     * @throws Exception
     */
    public static HistoryVideoBO getHistoryVideoRTSPs(String startTime, String endTime, String deviceId) throws Exception {
        HistoryVideoBO historyVideoBO = new HistoryVideoBO();
        historyVideoBO.setVideoServerIP(OrbitServiceConstant.videoServerIP);
        historyVideoBO.setRelayIP(OrbitServiceConstant.relayIP);
        historyVideoBO.setCredential(OrbitServiceConstant.credential);
        historyVideoBO.setRelayUsername(OrbitServiceConstant.relayUsername);
        historyVideoBO.setRelayPassword(OrbitServiceConstant.relayPassword);

        //(目前默认是查找这个时间段的前5条记录,抓拍时间前后5分钟最多两条记录)
        int pageSize = 5;
        if (OrbitServiceConstant.visionSDK != null) {
            VideoResult videoResult = new VideoResult();
            //目前默认是查找这个时间段的前十条记录(pageNo:1,pageSize:5)
            boolean ruselt = OrbitServiceConstant.visionSDK.historyVideoListQuery(
                    UUIDUtils.generate(), deviceId, DateUtils.getyyyyMMddHHmmss(startTime),
                    DateUtils.getyyyyMMddHHmmss(endTime), 1, pageSize, videoResult);
            if (ruselt) {
                //得到历史视频id
                List<VideoInfo> videoInfos = videoResult.getVideos();
                List<String> rtsps = new ArrayList<>();
                for (VideoInfo videoInfo : videoInfos) {
                    String rtspUrl = OrbitServiceConstant.visionSDK.historyVideoRTSPRequest(UUIDUtils.generate(), videoInfo.getFileId(), deviceId);
                    rtsps.add(rtspUrl.substring(0, rtspUrl.indexOf("#")));
                    historyVideoBO.setVideoServerIP(rtspUrl.substring(rtspUrl.indexOf("#") + 2));
                }
                historyVideoBO.setRtsps(rtsps);
            }
        }
        return historyVideoBO;
    }

}

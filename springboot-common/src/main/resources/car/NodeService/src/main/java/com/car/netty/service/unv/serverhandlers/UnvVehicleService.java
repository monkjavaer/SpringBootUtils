package com.base.springboot.car.NodeService.src.main.java.com.car.netty.service.unv.serverhandlers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.car.base.common.utilities.DateTimeUtils;
import com.car.base.common.utilities.SnowflakeIdWorkerUtil;
import com.car.base.message.MQMessage;
import com.car.base.message.MessageType;
import com.car.base.message.VehicleStructureMsg;
import com.car.base.message.vo.PhotoInfoVo;
import com.car.base.rabbit.MQSender;
import com.car.base.rabbit.QueueUtil;
import com.car.netty.enums.ErrorCode;
import com.car.netty.enums.UnvEnum;
import com.base.springboot.car.NodeService.src.main.java.com.car.netty.iservice.protocol.AnalyzeXmlIfc;
import com.base.springboot.car.NodeService.src.main.java.com.car.netty.service.unv.UnvBlockQueue;
import com.car.netty.service.unv.protocol.SendUnvCallBack;
import com.car.netty.struct.unv.bean.UnvProtocol;
import com.car.netty.struct.unv.bean.UnvVehicle;
import com.car.netty.struct.unv.bean.UnvVehicleAlarm;
import com.car.netty.struct.unv.config.UnvServerConfig;
import com.car.netty.utils.TimerHelp;
import io.netty.channel.ChannelHandlerContext;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

/**
 * Created by z00562 on 2016/7/28.
 * 多实例提高性能
 */
@Scope("prototype")
@Component
public class UnvVehicleService {

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(UnvVehicleService.class);

    /**
     * 解析XML类
     */
    @Resource
    private AnalyzeXmlIfc analyzeVehicleXML;

    /**
     * 宇视协议回包类
     */
    @Resource
    private SendUnvCallBack sendUnvCallBack;

    /**
     * 配置文件
     */
    @Resource
    private UnvServerConfig unvServerConfig;

    /**
     * 批量导入列表
     */
    @Resource
    private UnvBlockQueue unvBlockQueue;

    /**
     * 过车队列
     */
    private BlockingQueue<UnvVehicle> oVehicleQueue;

    /**
     * 违法队列
     */
    private BlockingQueue<UnvVehicleAlarm> oAalrmQueue;


    /**
     * 开始处理数据
     *
     * @param oUpl
     * @return boolean
     */
    public boolean stratDealData(UnvProtocol oUpl) {
        boolean isSuccess = true;
        try
        {
            int iCmd = oUpl.getiCmdType();
            int iErrorCode = oUpl.getiErrorCode();
            int iBackCmd = oUpl.getiBackCmdType();
            ChannelHandlerContext ctx = oUpl.getCtx();
            ArrayList<byte[]> oArrayList = oUpl.getoArrayList();
            //获取过车信息的Map对象
            HashMap<String, String> oMap = analyzeVehicleXML.getXmlData(oUpl.getStrXml());
            //设置TMS转发过来的命令码
            oMap.put("ICmd", new Integer(iCmd).toString());
            //获取tms转发过来的RecordID
            String strRecordId = oMap.get("RecordID");
            LOGGER.debug(UnvEnum.UNV_DEBUG + "TMS转发过来的RecordID:" + strRecordId);
            Long lDBId = 0L;
            //数据类型
            String strDataType = unvServerConfig.getStrDataType();

            /******************** 获取过车字段看这里  ************************************/


            //具体过车的字段参见对象 com.car.netty.struct.base.VehicleInfo
            //oMap.get("XXXX");
            System.out.println("车牌号码:" + oMap.get("CarPlate") + ", 过车时间:" + oMap.get("PassTime"));
/*            for(String key : oMap.keySet()){
                System.out.print("\t"+key+":"+oMap.get(key));
            }*/
            System.out.println("==============++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++===========");

            /***************************************************************************/

            //未识别车牌为英文 "-",暂时丢弃
            if ("-".equals(oMap.get("CarPlate")) && unvServerConfig.getFilterFlag() == 1) {
                //回成功报文，丢弃该数据
                sendUnvCallBack.sendBack(oMap, ctx, iBackCmd, iCmd, iErrorCode);
                LOGGER.debug(UnvEnum.UNV_DEBUG + "丢弃未识别车牌。车牌号码:" + oMap.get("CarPlate") + ", 过车时间:" + oMap.get("PassTime"));
                return isSuccess;
            }

            //判断数据类型 不为0是违法, 0是过车
            String strDriveStatus = oMap.get("DriveStatus");

            //暂时只处理过车数据，不处理违法数据
            if ("1".equals(strDataType) && !"0".equals(strDriveStatus)) {
                //回成功报文，丢弃该违法数据
                sendUnvCallBack.sendBack(oMap, ctx, iBackCmd, iCmd, iErrorCode);
                LOGGER.debug(UnvEnum.UNV_DEBUG + "丢弃违法数据。车牌号码:" + oMap.get("CarPlate") + ", 过车时间:" + oMap.get("PassTime"));
                return isSuccess;
            }

            //需要写入图片
            boolean isSendMSG = true;

            //发送MQ消息
            JSONObject jsonObject = getXmlJsonobject(oUpl.getStrXml());
            try {
                this.sendMessage(jsonObject,oUpl.getoArrayList());
            }catch (IOException e){
                isSendMSG = false;
                LOGGER.error(UnvEnum.UNV_ERROR + "rabbitMQ 发送失败！ error message is : {}" , e.getMessage());
            }

            //发MQ消息成功，回成功报文
            if (!isSendMSG) {
                //回失败报文
                oUpl.setiErrorCode(ErrorCode.DB_INSERT_FAIL);
            }

            if (iBackCmd != 0) {
                //回包处理
                sendUnvCallBack.sendBack(oMap, ctx, iBackCmd, iCmd, oUpl.getiErrorCode());
            } else {
                LOGGER.error(UnvEnum.UNV_ERROR + "该命令码无需回报文。命令码:" + iCmd);
            }
        } catch (Exception e) {
            LOGGER.error("异常信息:" + e.getMessage(), e);
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * 根据报文XML字符串构造JSON格式数据
     * @return
     */
    public JSONObject getXmlJsonobject(String strXml){
        JSONObject result = new JSONObject();
        Document doc = null;
        int imageOp = 0;
        String[] urlArr = null;

        try{
            doc = DocumentHelper.parseText(strXml);
            Element rootElt = doc.getRootElement();

            Iterator iter = rootElt.elementIterator();

            while(iter.hasNext()){

                Element itemEle = (Element) iter.next();

                if ("Image".equals(itemEle.getName())){

                    String index = "Image"+(++imageOp);
                    Iterator iterTemp = itemEle.elementIterator();
                    JSONObject resultTemp = new JSONObject();

                    while (iterTemp.hasNext()){
                        Element itemTemp = (Element)iterTemp.next();
                        resultTemp.put(itemTemp.getName(),itemTemp.getText());
                        if ("ImageURL".equals(itemTemp.getName())){
                            urlArr = itemTemp.getText().split("[?]");
                            String realUrl = urlArr[0];
                            resultTemp.put(itemTemp.getName(),realUrl);
                        }
                    }
                    result.put(index,resultTemp);

                }else {
                    result.put(itemEle.getName(),itemEle.getText());
                }

            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }


    public void sendMessage(JSONObject data,ArrayList<byte[]> photoDataList) throws IOException, ParseException {
        PhotoInfoVo photoInfoVo = new PhotoInfoVo();
        VehicleStructureMsg msg = new VehicleStructureMsg();
        msg.setId(SnowflakeIdWorkerUtil.generateId());
        msg.setCaptureTime(DateTimeUtils.timeStampFromString(data.getString("PassTime")));
        msg.setPlateNumber(data.getString("CarPlate"));
        msg.setVehicleColor(data.getString("VehicleColor"));
        msg.setPlateColor(Integer.valueOf(data.getString("PlateColor")));
        if (data.getString("VehicleBrand") == null || "".equals(data.getString("VehicleBrand"))){
            msg.setVehicleBrand(0);
        }else {
            msg.setVehicleBrand(Integer.valueOf(data.getString("VehicleBrand")));
        }
        msg.setSpeed(Integer.valueOf(data.getString("VehicleSpeed")));
        msg.setRoadwayNo(Integer.valueOf(data.getString("LaneID")));
        msg.setRoadwayName(data.getString("LaneType"));
        //卡口编码
        msg.setBayonetNo(data.getString("TollgateID"));
        msg.setDirectionCode(Integer.valueOf(data.getString("Direction")));
        msg.setVehicleType(data.getString("VehicleType"));
        if (data.getString("DriveStatus") == null || "".equals(data.getString("DriveStatus"))){
            msg.setDriveStatus(0);
        }else {
            msg.setDriveStatus(Integer.valueOf(data.getString("DriveStatus")));
        }

        //根据协议，若地点编号为空，用卡口编号代替
        if (data.getString("PlaceCode") == null || "".equals(data.getString("PlaceCode"))) {
            msg.setRoadCrossPointId(new BigInteger(data.getString("TollgateID")));
        } else {
            msg.setRoadCrossPointId(new BigInteger(data.getString("PlaceCode")));
        }

        Integer picNum = Integer.valueOf(data.getString("PicNumber"));
        String imageCompleteUrl = null;

        for (int i = 1; i<= picNum;i++){
            switch (i){
                case 1:
                    photoInfoVo = JSON.parseObject(data.getJSONObject("Image1").toString(), PhotoInfoVo.class);
                    break;
                case 2:
                    photoInfoVo = JSON.parseObject(data.getJSONObject("Image2").toString(), PhotoInfoVo.class);
                    break;
                case 3:
                    photoInfoVo = JSON.parseObject(data.getJSONObject("Image3").toString(), PhotoInfoVo.class);
                    break;
                case 4:
                    photoInfoVo = JSON.parseObject(data.getJSONObject("Image4").toString(), PhotoInfoVo.class);
                    break;
                default:
            }

            imageCompleteUrl = photoInfoVo.getImageUrl();

            photoInfoVo.setImageUrl(imageCompleteUrl);
        }

        msg.setPhotoDataList(photoDataList);

        MQMessage message = new MQMessage();
        message.setBody(msg);
        message.setMessageType(MessageType.VEHICLE_STRUCTURE);

        //发消息
        MQSender mqSender = QueueUtil.getNodePassMqSender();
        mqSender.sendMessage(message);
    }


    /**
     * 批量插入数据库（收URL尽量批量插入）
     *
     * @param oMap
     * @param lDBId
     * @param strDriveStatus
     * @param iCmd
     * @return boolean
     */
    private boolean batchInsertDB(HashMap<String, String> oMap, Long lDBId, String strDriveStatus, int iCmd) {
        boolean isSuccess = true;
        try {
            if (151 == iCmd) {
                strDriveStatus = "0";
            }

            //0认为是过车
            if ("0".equals(strDriveStatus)) {
                UnvVehicle unv = new UnvVehicle();
                unv.setRecordId(lDBId);
                unv.setDeviceCode(oMap.get("TollgateID"));
                unv.setPlateCode(oMap.get("CarPlate"));
                String strTime = oMap.get("PassTime");
                unv.setPassTime(TimerHelp.transUnvTimeToTimestamp(strTime));
                unv.setRecordData(JSON.toJSONString(oMap));
                unv.setTransFlag(0);
                this.oVehicleQueue = unvBlockQueue.getoVehicleQueue();
                int iSize = this.oVehicleQueue.size();
                if (iSize == UnvEnum.UNV_QUEUE) {
                    ArrayList<UnvVehicle> oTempVehicleList = new ArrayList<UnvVehicle>(iSize);
                    this.oVehicleQueue.drainTo(oTempVehicleList);
                } else {
                    this.oVehicleQueue.put(unv);
                }
            } else {
                UnvVehicleAlarm una = new UnvVehicleAlarm();
                una.setAlarmId(lDBId);
                una.setDeviceCode(oMap.get("TollgateID"));
                una.setPlateCode(oMap.get("CarPlate"));
                String strTime = oMap.get("PassTime");
                una.setPassTime(TimerHelp.transUnvTimeToTimestamp(strTime));
                una.setAlarmData(JSON.toJSONString(oMap));
                una.setTransFlag(0);
                una.setDriveStatus(strDriveStatus);
                this.oAalrmQueue = unvBlockQueue.getoAlarmQueue();
                int iSize = this.oAalrmQueue.size();
                if (iSize >= UnvEnum.UNV_QUEUE) {
                    ArrayList<UnvVehicleAlarm> oTempAlarmList = new ArrayList<UnvVehicleAlarm>(iSize);
                    // 获取队列数据
                    this.oAalrmQueue.drainTo(oTempAlarmList);
                } else {
                    this.oAalrmQueue.put(una);
                }
            }
        } catch (Exception e) {
            LOGGER.error(UnvEnum.UNV_ERROR + e.getMessage(), e);
            isSuccess = false;
        }
        return isSuccess;
    }
}
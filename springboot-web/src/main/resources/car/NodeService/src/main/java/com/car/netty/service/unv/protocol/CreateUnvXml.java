package com.car.netty.service.unv.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;


public class CreateUnvXml {

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(CreateUnvXml.class);

    /**
     * 组装心跳包XML
     * @param oMap
     * @param iErrorCode
     * @param iReqCmdID
     * @return String
     */
    public static String buildHeartResponseXml(HashMap<String,String> oMap, int iErrorCode, int iReqCmdID)
    {
        if (oMap == null)
        {
            return null;
        }
        String strXml = "<?xml version=\"1.0\"?>\r\n" + "<Response>\r\n" + "<CamID>" + oMap.get("CamID")
                + "</CamID>\r\n" + "<RecordID>" + oMap.get("RecordID") + "</RecordID>\r\n" + "<Result>"
                + iErrorCode + "</Result>\r\n" + "<ReqCmdID>" + iReqCmdID + "</ReqCmdID>\r\n"
                + "<DBRecordID>0</DBRecordID>\r\n" + "</Response>\r\n";
        return strXml;
    }
}

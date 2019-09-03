package com.base.springboot.car.NodeService.src.main.java.com.car.netty.service.unv.protocol;

import com.car.netty.enums.UnvEnum;
import com.car.netty.utils.BytesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


@Service
public class CreateUnvProtocol {
    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(CreateUnvProtocol.class);


    /**
     * 卡口和IP的对应关系
     */
    private static Map<String, Object> oMap = null;

    /**
     * 获取宇视协议心跳（发送）
     *
     * @return
     */
    public static byte[] getHeartBeat(String strCode, int iCmdType, int iPackgeLen)
    {
        // 设备编码
        byte[] bDevCode;
        //发送数据报文长度
        byte[] bSendData = new byte[12 + iPackgeLen];
        try
        {
            // 包内容
            bDevCode = strCode.getBytes("UTF-8");
            int iCodeLen = bDevCode.length;
            // 获取包头信息 + 包长 + 版本号 + 命令码
            byte[] bHeader = CreateUnvProtocol.getHeader(iCmdType, iPackgeLen);
            byte[] bEnd = getEnd();

            System.arraycopy(bHeader, 0, bSendData, 0, bHeader.length);
            System.arraycopy(bDevCode, 0, bSendData, bHeader.length, iCodeLen);
            System.arraycopy(bEnd, 0, bSendData, bHeader.length + iCodeLen, 4);
        }
        catch (Exception e)
        {
            LOGGER.error("异常信息:" + e.getMessage());
        }
        return bSendData;
    }

    /**
     * 获取宇视报文头 包含： 包头 + 包长 + 版本号 + 命令码
     *
     * @param iCommandId
     * @param iPackageLenth
     * @return byte[].
     */
    public static byte[] getHeader(int iCommandId, int iPackageLenth)
    {
        // 定义包头（因为C中Ulong类型占四个字节，所以 java使用同为四个字节的int）
        int iMsgBeginFlag = UnvEnum.UNV_HEADER;
        // 定义包长
        int iPackageLen = iPackageLenth;
        // 定义版本号
        int iVersion = 2;
        // 定义命令码
        int iCmdID = iCommandId;

        // 包头(存入byte数组中)
        byte[] bBegin = BytesUtils.inttobytes(iMsgBeginFlag);
        // 包长
        byte[] bPackageLen = BytesUtils.inttobytes(iPackageLen);
        // 协议版本
        byte[] bVersion = BytesUtils.inttobytes(iVersion);
        // 命令码
        byte[] bCommandId = BytesUtils.inttobytes(iCmdID);

        byte[] bHeader = new byte[16];
        System.arraycopy(bBegin, 0, bHeader, 0, 4);
        System.arraycopy(bPackageLen, 0, bHeader, 4, 4);
        System.arraycopy(bVersion, 0, bHeader, 8, 4);
        System.arraycopy(bCommandId, 0, bHeader, 12, 4);

        return bHeader;
    }

    /**
     * 获取报文尾
     *
     * @return
     */
    public static byte[] getEnd()
    {
        int iEnd = UnvEnum.UNV_LAST;
        byte[] bEnd = BytesUtils.inttobytes(iEnd);
        return bEnd;
    }


    /**
     * 回宇视协议响应
     * @return byte[]
     */
    public static byte[] unvResponseInfo(HashMap<String,String> oMap, int iBackCmdType, int iCmdType, int strErrorCode)
    {
        byte[] bBackByte = null;

        if (oMap == null)
        {
            LOGGER.error("异常信息: HashMap为空不合法。");
            return bBackByte;
        }
        // 组装回包字节
        String strXml = CreateUnvXml.buildHeartResponseXml(oMap, strErrorCode, iCmdType);
        if (null == strXml)
        {
            LOGGER.error("异常信息: 组装回包XML出错。");
            return bBackByte;
        }
        try
        {
            byte[] bXmlData = strXml.getBytes("UTF-8");
            // 包长 = XML长度 + 命令码 + 协议版本号 + 实际xml长度
            int iPackageLenth = bXmlData.length + 12;
            bBackByte = new byte[iPackageLenth + 12];
            byte[] bHeader = CreateUnvProtocol.getHeader(iBackCmdType, iPackageLenth);
            byte[] bEnd = CreateUnvProtocol.getEnd();
            byte[] bXmlLen = BytesUtils.int2Bytes(bXmlData.length);

            System.arraycopy(bHeader, 0, bBackByte, 0, bHeader.length);
            System.arraycopy(bXmlLen, 0, bBackByte, bHeader.length, 4);
            System.arraycopy(bXmlData, 0, bBackByte, bHeader.length + 4, bXmlData.length);
            System.arraycopy(bEnd, 0, bBackByte, bHeader.length + bXmlData.length + 4, bEnd.length);

        }
        catch (UnsupportedEncodingException e)
        {
            LOGGER.error("异常信息:" + e.getMessage(), e);
            bBackByte = null;
        }
        return bBackByte;
    }

}

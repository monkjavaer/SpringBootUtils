package com.car.netty.service.unv.protocol;

import com.car.netty.iservice.protocol.AnalyzeProtocolIfc;
import com.car.netty.struct.unv.bean.UnvProtocol;
import com.car.netty.utils.BytesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


@Scope("prototype")
@Service
public class AnalyzeUnvProtocol implements AnalyzeProtocolIfc {

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(AnalyzeUnvProtocol.class);

    /**
     * 心跳命令码
     */
    private int iCmd = 101;

    /**
     * @param bInBytes 输入宇视协议包
     * @return protocol
     */
    @Override
    public UnvProtocol analyze(byte[] bInBytes)
    {

        //创建协议对象
        UnvProtocol oUpl = new UnvProtocol();

        try {

            /**
             * 协议版本号
             */
            byte[] bVersion = new byte[4];
            /**
             * 报文长
             */
            byte[] bPackageLen = new byte[4];
            /**
             * 命令码
             */
            byte[] bCmdType = new byte[4];
            /**
             * XML
             */
            byte[] bXml;
            /**
             * xml的长度字节
             */
            byte[] bXmlLen;
            /**
             * 图片数量
             */
            byte[] bImageNum;
            /**
             * 图片大小
             */
            byte[] bImageSize;
            /**
             * 图片内容
             */
            byte[] bImageData;

            /**
             * 设备编码
             */
            byte[] bDevCode;

            // 获取包长
            bPackageLen = BytesUtils.copyOfRange(bInBytes, 4, 8);
            int iPackageLen = BytesUtils.bytes2Int(bPackageLen);
            LOGGER.debug("调试信息:" + "数据包长 " + iPackageLen);
            oUpl.setiPackageLen(iPackageLen);

            // 获取协议版本
            bVersion = BytesUtils.copyOfRange(bInBytes, 8, 12);
            int iVersion = BytesUtils.bytes2Int(bVersion);
            LOGGER.debug("调试信息:" + "协议版本 " + iVersion);
            oUpl.setiVersion(iVersion);

            // 获取命令码
            bCmdType = BytesUtils.copyOfRange(bInBytes, 12, 16);
            int iCmdType = BytesUtils.bytes2Int(bCmdType);
            LOGGER.debug("调试信息:" + "命令码 " + iCmdType);
            oUpl.setiCmdType(iCmdType);

            // 如果是心跳报文
            if(iCmdType == iCmd)
            {
                //bDevCode = BytesUtils.copyOfRange(bInBytes, 16, 48);
                bDevCode = BytesUtils.copyOfRange(bInBytes, 16, iPackageLen + 8);
                String strDevCode = new String(bDevCode, "UTF-8");
                oUpl.setStrCode(strDevCode);
            }
            else
            {
                // 获取XML长度
                bXmlLen = BytesUtils.copyOfRange(bInBytes, 16, 20);
                int iXmlLen = BytesUtils.bytes2Int(bXmlLen);
                bXml = BytesUtils.copyOfRange(bInBytes, 20, iXmlLen + 20);
                String strXmlData = new String(bXml, "UTF-8");
                strXmlData = strXmlData.trim();
                oUpl.setStrXml(strXmlData);

                //获取图片张数
                bImageNum = BytesUtils.copyOfRange(bInBytes, iXmlLen + 20, iXmlLen + 24);
                int iImageNum = BytesUtils.bytes2Int(bImageNum);

                //默认最多5张图片
                ArrayList<byte[]> oArrayList = new ArrayList<byte[]>(5);

                //图片开始读取位置
                int iStart = iXmlLen + 24;
                SaveStart oSatrt = new SaveStart();
                //存在图片数据
                switch (iImageNum)
                {
                    case 0:
                        break;
                    case 1:
                        //第一张图片
                        oSatrt.setiStart(iStart);
                        bImageData = this.getPicBytes(bInBytes, oSatrt);
                        oArrayList.add(bImageData);
                        break;
                    case 2:
                        //第一张图片
                        oSatrt.setiStart(iStart);
                        bImageData = this.getPicBytes(bInBytes, oSatrt);
                        oArrayList.add(bImageData);

                        //第二张图片
                        bImageData = this.getPicBytes(bInBytes, oSatrt);
                        oArrayList.add(bImageData);
                        break;
                    case 3:
                        //第一张图片
                        oSatrt.setiStart(iStart);
                        bImageData = this.getPicBytes(bInBytes, oSatrt);
                        oArrayList.add(bImageData);

                        //第二张图片
                        bImageData = this.getPicBytes(bInBytes, oSatrt);
                        oArrayList.add(bImageData);

                        //第三张图片
                        bImageData = this.getPicBytes(bInBytes, oSatrt);
                        oArrayList.add(bImageData);
                        break;
                    case 4:
                        //第一张图片
                        oSatrt.setiStart(iStart);
                        bImageData = this.getPicBytes(bInBytes, oSatrt);
                        oArrayList.add(bImageData);

                        //第二张图片
                        bImageData = this.getPicBytes(bInBytes, oSatrt);
                        oArrayList.add(bImageData);

                        //第三张图片
                        bImageData = this.getPicBytes(bInBytes, oSatrt);
                        oArrayList.add(bImageData);

                        //第四张图片
                        bImageData = this.getPicBytes(bInBytes, oSatrt);
                        oArrayList.add(bImageData);
                        break;
                    case 5:
                        //第一张图片
                        oSatrt.setiStart(iStart);
                        bImageData = this.getPicBytes(bInBytes, oSatrt);
                        oArrayList.add(bImageData);

                        //第二张图片
                        bImageData = this.getPicBytes(bInBytes, oSatrt);
                        oArrayList.add(bImageData);

                        //第三张图片
                        bImageData = this.getPicBytes(bInBytes, oSatrt);
                        oArrayList.add(bImageData);

                        //第四张图片
                        bImageData = this.getPicBytes(bInBytes, oSatrt);
                        oArrayList.add(bImageData);

                        //第五张图片
                        bImageData = this.getPicBytes(bInBytes, oSatrt);
                        oArrayList.add(bImageData);
                        break;
                    default:
                }
                oUpl.setoArrayList(oArrayList);
            }
        }
        catch(UnsupportedEncodingException ue)
        {
            LOGGER.error("异常信息:" + ue.getMessage(), ue);
            oUpl = null;

        }
        catch (Exception e)
        {
            LOGGER.error("异常信息:" + e.getMessage(), e);
            oUpl = null;
        }

        return oUpl;
    }

    /**
     * 获取字节流图片字节内容
     * @param bInBytes
     * @param oSatrt
     * @return byte[]
     */
    public byte[] getPicBytes(byte[] bInBytes, SaveStart oSatrt)
    {
        /**
         * 图片大小
         */
        byte[] bImageSize;
        /**
         * 图片内容
         */
        byte[] bImageData;

        int iStart = oSatrt.getiStart();
        bImageSize = BytesUtils.copyOfRange(bInBytes, iStart, iStart + 4);
        int iImageSize = BytesUtils.bytes2Int(bImageSize);
        bImageData = BytesUtils.copyOfRange(bInBytes, iStart + 4, iStart + 4 + iImageSize);
        //设置下一张的开始查询起点
        oSatrt.setiStart(iStart + 4 + iImageSize);
        return bImageData;
    }

    private class SaveStart
    {
        private int iStart = 0;

        public int getiStart()
        {
            return iStart;
        }

        public void setiStart(int iStart)
        {
            this.iStart = iStart;
        }
    }


}
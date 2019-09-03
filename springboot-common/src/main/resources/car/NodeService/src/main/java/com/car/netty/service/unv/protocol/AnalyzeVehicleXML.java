package com.base.springboot.car.NodeService.src.main.java.com.car.netty.service.unv.protocol;

import com.car.netty.enums.UnvEnum;
import com.base.springboot.car.NodeService.src.main.java.com.car.netty.iservice.protocol.AnalyzeXmlIfc;
import com.car.netty.struct.unv.config.UnvServerConfig;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


@Service
public class AnalyzeVehicleXML implements AnalyzeXmlIfc {

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(AnalyzeVehicleXML.class);

    /**
     * 宇视协议配置文件
     */
    @Resource
    private UnvServerConfig unvServerConfig;


    /**
     * 获取XML
     * @param strXml
     * @return HashMap<String, String> 这个HashMap不要去修改和处理
     */



    @Override
    public HashMap<String,String> getXmlData(String strXml)
    {
        //返回的XML结果值
        HashMap<String,String> oMap = new HashMap<String,String>();
        //待转换的IP和端口
        HashMap<String,String> oIpMap = unvServerConfig.getoIpMap();
        if (null == strXml)
        {
            LOGGER.error("异常信息: strXmlData 入参为空。");
            oMap = null;
            return oMap;
        }
        try
        {
            Document document = DocumentHelper.parseText(strXml);
            Element root = document.getRootElement();
/*            *
             * 循环遍历所有节点
             */
            for (Iterator<?> it = root.elementIterator(); it.hasNext();)
            {
                Element element = (Element) it.next();
                String tempName = element.getName();
                List<Element> list = null;
                switch(tempName)
                {
                    //解析过车图片部分
                    case "Image":
                        String strIndex = "1";
                        list = element.elements();
                        for (Element e : list)
                        {
                            if ("ImageIndex".equals(e.getName()))
                            {
                                strIndex = e.getStringValue();
                            }
                            if ("ImageData".equals(e.getName()))
                            {
                                continue;
                            }
                            if ("ImageURL".equals(e.getName()))
                            {
                                String strUrl = e.getStringValue();
                                String strChageUrl = e.getStringValue();
                                String strNewUrl;
                                // 有配置的话是需要转换
                                if(oIpMap.size() > 0)
                                {
                                    //转换ip和端口
                                    int iIpStart = strUrl.indexOf("http://");
                                    if (iIpStart > -1)
                                    {
                                        strUrl = strUrl.substring(7, strUrl.length());
                                        int iIpEnd = strUrl.indexOf("/");
                                        //查询第一个斜杠
                                        if (iIpEnd > -1)
                                        {
                                            //获取到IP和端口
                                            strUrl = strUrl.substring(0, iIpEnd);
                                            //获取到老IP端口对应的新IP端口
                                            strNewUrl = oIpMap.get(strUrl);
                                            if (null != strNewUrl)
                                            {
                                                //URL更新新的IP和端口
                                                strChageUrl = strChageUrl.replace(strUrl, strNewUrl);
                                            } else {
                                                LOGGER.error(UnvEnum.UNV_ERROR + "配置文件中" + strUrl + "地址不存在。");
                                            }
                                        }
                                    }
                                }
                                oMap.put("ImageURL" + strIndex, strChageUrl);
                                continue;
                            }
                            oMap.put(e.getName() + strIndex, e.getStringValue());
                        }
                        break;
                    //解析车牌识别部分
                    case "VehicleFace":
                        list = element.elements();
                        for (Element e : list)
                        {
                            //车头识别部分数据
                            oMap.put("Face" + e.getName(), e.getStringValue());
                        }
                        break;
                    //解析GpsInfo部分
                    case "GpsInfo":
                        list = element.elements();
                        for (Element e : list)
                        {
                            oMap.put(e.getName(), e.getStringValue());
                        }
                        break;
                    default:
                        oMap.put(tempName, element.getStringValue());
                        break;
                }
            }

            if (!oMap.containsKey("ImageURL1"))
            {
                oMap.put("ImageURL1", "");
                oMap.put("ImageIndex1", "");
                oMap.put("ImageType1", "");
            }
            if (!oMap.containsKey("ImageURL2"))
            {
                oMap.put("ImageURL2", "");
                oMap.put("ImageIndex2", "");
                oMap.put("ImageType2", "");
            }
            if (!oMap.containsKey("ImageURL3"))
            {
                oMap.put("ImageURL3", "");
                oMap.put("ImageIndex3", "");
                oMap.put("ImageType3", "");
            }
            if (!oMap.containsKey("ImageURL4"))
            {
                oMap.put("ImageURL4", "");
                oMap.put("ImageIndex4", "");
                oMap.put("ImageType4", "");
            }
            if (!oMap.containsKey("ImageURL5"))
            {
                oMap.put("ImageURL5", "");
                oMap.put("ImageIndex5", "");
                oMap.put("ImageType5", "");
            }
            if (!oMap.containsKey("ImageURL6"))
            {
                oMap.put("ImageURL6", "");
                oMap.put("ImageIndex6", "");
                oMap.put("ImageType6", "");
            }
            if (!oMap.containsKey("ImageURL7"))
            {
                oMap.put("ImageURL7", "");
                oMap.put("ImageIndex7", "");
                oMap.put("ImageType7", "");
            }
            if (!oMap.containsKey("ImageURL8"))
            {
                oMap.put("ImageURL8", "");
                oMap.put("ImageIndex8", "");
                oMap.put("ImageType8", "");
            }
            if (!oMap.containsKey("ImageURL9"))
            {
                oMap.put("ImageURL9", "");
                oMap.put("ImageIndex9", "");
                oMap.put("ImageType9", "");
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
            oMap = null;
        }

        return oMap;
    }

    /**
     * @Description:  解析TMS回报文
     */
    public HashMap<String,String> praseResponseXml(String strXml)
    {
        //返回的XML结果值
        HashMap<String,String> oResultMap = new HashMap<String,String>();

        if (null == strXml)
        {
            LOGGER.error("异常信息: strXml 入参为空。");
            oResultMap = null;
            return oResultMap;
        }
        try
        {
            Document document = DocumentHelper.parseText(strXml);
            Element root = document.getRootElement();
            /**
             * 循环遍历所有节点
             */
            for (Iterator<?> it = root.elementIterator(); it.hasNext();)
            {
                Element element = (Element) it.next();
                String strElementName = element.getName();
                oResultMap.put(strElementName, element.getStringValue());
            }
            LOGGER.debug("调试信息：TMS返回XML信息　 " + strXml);
            LOGGER.debug("调试信息：TMS返回XML信息，解析结果 " + oResultMap.toString());
        }
        catch(Exception e)
        {
             LOGGER.error("异常信息：" + e.getMessage());
            oResultMap = null;
        }

        return oResultMap;
    }
}

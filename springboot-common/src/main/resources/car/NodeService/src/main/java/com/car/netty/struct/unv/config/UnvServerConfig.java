package com.base.springboot.car.NodeService.src.main.java.com.car.netty.struct.unv.config;

import com.car.base.common.utilities.PropertiesUtil;
import com.car.netty.enums.UnvEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;


@Component
public class UnvServerConfig
{
    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(UnvServerConfig.class);
    /**
     * 数据库配置文件路径
     */
    private String strPath = "/server.properties";

    /**
     * 是否批量写入(仅写URL有效)
     */
    private String isBatchUrl;

    /**
     * 默认为json格式的数据
     * 0表示json
     * 1表示detail
     */
    private String strDataType = "0";

    /**
     * TCP收包端口
     */
    private int iPort = 5196;

    /**
     * 图片硬盘路径
     */
    private String strDiskPath;

    /**
     * 是否接收图片 0不接收 1接收
     */
    private int iRecivePic = 0;

    /**
     * 是否开启过滤无车牌的过车信息，1为过滤掉没车牌的，0为不过滤。
     */
    private int filterFlag = 0;

    /**
     * 待转换的地址和端口
     */
    private HashMap<String,String> oIpMap = new HashMap<String,String>();

    /**
     * 构造函数
     */
    public UnvServerConfig()
    {
        PropertiesUtil propertiesUtil = new PropertiesUtil(strPath);
        this.isBatchUrl = propertiesUtil.getPropertieValue("server.isbatch");
        this.strDataType = propertiesUtil.getPropertieValue("server.datatype");
        this.iPort = new Integer(propertiesUtil.getPropertieValue("server.port"));
        this.strDiskPath = propertiesUtil.getPropertieValue("server.picpath");
        this.iRecivePic = new Integer(propertiesUtil.getPropertieValue("server.recivepic"));
        this.filterFlag = new Integer(propertiesUtil.getPropertieValue("server.filterFlag"));
        String strIpChange = propertiesUtil.getPropertieValue("server.ipchange");
        if(null != strIpChange && !"".equals(strIpChange))
        {
            String[] arrStr = strIpChange.split(",");
            int iSize = arrStr.length;
            for (int i = 0; i < iSize; i++) {
                String[] arrStrIp = arrStr[i].split("\\|");
                oIpMap.put(arrStrIp[0], arrStrIp[1]);
            }
        }
        else
        {
            LOGGER.info(UnvEnum.UNV_INFO + "ip地址和端口不需要转换。");
        }
    }

    public String getStrDataType() {
        return strDataType;
    }
    public void setStrDataType(String strDataType) {
        this.strDataType = strDataType;
    }
    public int getiPort() {
        return iPort;
    }
    public void setiPort(int iPort) {
        this.iPort = iPort;
    }
    public HashMap<String, String> getoIpMap() {
        return oIpMap;
    }
    public void setoIpMap(HashMap<String, String> oIpMap) {
        this.oIpMap = oIpMap;
    }
    public String getIsBatchUrl() {
        return isBatchUrl;
    }
    public void setIsBatchUrl(String isBatchUrl) {
        this.isBatchUrl = isBatchUrl;
    }
    public String getStrDiskPath() {
        return strDiskPath;
    }
    public void setStrDiskPath(String strDiskPath) {
        this.strDiskPath = strDiskPath;
    }
    public int getiRecivePic() {
        return iRecivePic;
    }
    public void setiRecivePic(int iRecivePic) {
        this.iRecivePic = iRecivePic;
    }

    public int getFilterFlag() {
        return filterFlag;
    }

    public void setFilterFlag(int filterFlag) {
        this.filterFlag = filterFlag;
    }
}

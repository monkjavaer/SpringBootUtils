package com.base.springboot.car.NodeService.src.main.java.com.car.netty.struct.base;

public class InterProtocolFrame
{
    /**
     * 获取类名
     */
    private String strDllLib = "";
    /**
     * 使能状态-为true时，开启对接服务
     */
    private String strEnableStatus = "";
    /**
     * 初始化参数
     */
    private String strInitParam = "";
    /**
     * 子模块参数配置
     */
    private String strConfPath = "";
    /**
     * 模块名
     */
    private String strModuleName = "";
    /**
     * 接收模块
     */
    private String strReceiveMsg = "";
    /**
     * 标志位
     */
    private String strTransFlag = "";
    /**
     * 子模块运行模式
     */
    private String strRunType = "";

    public String getStrInitParam()
    {
        return strInitParam;
    }

    public void setStrInitParam(String strInitParam)
    {
        this.strInitParam = strInitParam;
    }

    public String getStrConfPath()
    {
        return strConfPath;
    }

    public void setStrConfPath(String strConfPath)
    {
        this.strConfPath = strConfPath;
    }

    public String getStrModuleName()
    {
        return strModuleName;
    }

    public void setStrModuleName(String strModuleName)
    {
        this.strModuleName = strModuleName;
    }

    public String getStrReceiveMsg()
    {
        return strReceiveMsg;
    }

    public void setStrReceiveMsg(String strReceiveMsg)
    {
        this.strReceiveMsg = strReceiveMsg;
    }

    public String getStrTransFlag()
    {
        return strTransFlag;
    }

    public void setStrTransFlag(String strTransFlag)
    {
        this.strTransFlag = strTransFlag;
    }

    public String getStrRunType()
    {
        return strRunType;
    }

    public void setStrRunType(String strRunType)
    {
        this.strRunType = strRunType;
    }

    public String getstrDllLib()
    {
        return strDllLib;
    }

    public String getstrEnableStatus()
    {
        return strEnableStatus;
    }

    public void setstrDllLib(String strDllLib)
    {
        this.strDllLib = strDllLib;
    }

    public void setstrEnableStatus(String strEnableStatus)
    {
        this.strEnableStatus = strEnableStatus;
    }

}

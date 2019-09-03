/**
 * 常量
 * @author monkjavaer
 * @date 2019/7/15 13:42
 */
public class ClientConstant{
    /**服务器端口*/
    public final static int PORT = 5196;

    /**服务器ip*/
    public final static String IP = "10.11.14.3";

    /**包头*/
    public static final int UNV_HEADER = 0x77aa77aa;

    /**包尾*/
    public static final int UNV_LAST = 0x77ab77ab;

    /**发送数据命令码*/
    public static final int CODE = 211;

    /**卡口相机编码,对应设备表的BAYONET_NO*/
    public static final String NUMBER = "10001";

}

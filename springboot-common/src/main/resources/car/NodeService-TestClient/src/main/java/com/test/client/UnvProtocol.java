import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;

/**
 * 宇视报文协议
 * @author monkjavaer
 * @date 2019/7/15 13:42
 */
public class UnvProtocol {

    /**
     * 数据包类型 1 过车 2 RFID 3.人脸 4.MAC
     */
    private int iPackageType;
    /**
     * 数据包长
     */
    private int iPackageLen = 0;
    /**
     * 数据版本号
     */
    private int iVersion = 0;
    /**
     * 数据命令码
     */
    private int iCmdType = 0;
    /**
     * 数据XML
     */
    private String strXml = "";
    /**
     * 图片数量
     */
    private int iImageNum = 0;
    /**
     * 大图图片
     */
    private ArrayList<byte[]> oArrayList;
    /**
     * 车牌小图
     */
    private byte[] bPicImage;

    /**
     * netty通道上下文
     */
    private ChannelHandlerContext ctx;

    /**
     * 编码
     */
    private String strCode;

    /**
     * 回包命令码
     */
    private int  iBackCmdType = 0;
    /**
     * 回包错误码
     */
    private int iErrorCode = 0;

    public int getiErrorCode() {
        return iErrorCode;
    }

    public void setiErrorCode(int iErrorCode) {
        this.iErrorCode = iErrorCode;
    }

    public int getiBackCmdType() {
        return iBackCmdType;
    }

    public void setiBackCmdType(int iBackCmdType) {
        this.iBackCmdType = iBackCmdType;
    }

    public String getStrCode() {
        return strCode;
    }

    public void setStrCode(String strCode) {
        this.strCode = strCode;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public byte[] getbPicImage() {
        return bPicImage;
    }

    public void setbPicImage(byte[] bPicImage) {
        this.bPicImage = bPicImage;
    }

    public ArrayList<byte[]> getoArrayList() {
        return oArrayList;
    }

    public void setoArrayList(ArrayList<byte[]> oArrayList) {
        this.oArrayList = oArrayList;
    }

    public int getiImageNum() {
        return iImageNum;
    }

    public void setiImageNum(int iImageNum) {
        this.iImageNum = iImageNum;
    }

    public String getStrXml() {
        return strXml;
    }

    public void setStrXml(String strXml) {
        this.strXml = strXml;
    }

    public int getiCmdType() {
        return iCmdType;
    }

    public void setiCmdType(int iCmdType) {
        this.iCmdType = iCmdType;
    }

    public int getiPackageLen() {
        return iPackageLen;
    }

    public void setiPackageLen(int iPackageLen) {
        this.iPackageLen = iPackageLen;
    }

    public int getiVersion() {
        return iVersion;
    }

    public void setiVersion(int iVersion) {
        this.iVersion = iVersion;
    }

    public int getiPackageType() {
        return iPackageType;
    }

    public void setiPackageType(int iPackageType) {
        this.iPackageType = iPackageType;
    }
}

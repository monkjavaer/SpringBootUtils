import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Random;

/**
 * 报文拼接
 *
 * @author monkjavaer
 * @date 2019/7/15 13:42
 */
public class CreateUnvXml {

    private static Logger logger = LoggerFactory.getLogger(CreateUnvXml.class);
    /**
     * 组装心跳包XML
     *
     * @param oMap
     * @param iErrorCode
     * @param iReqCmdID
     * @return String
     */
    public static String buildHeartResponseXml(HashMap<String, String> oMap, int iErrorCode, int iReqCmdID) {
        if (oMap == null) {
            return null;
        }
        String strXml = "<?xml version=\"1.0\"?>\r\n" + "<Response>\r\n" + "<CamID>" + oMap.get("CamID")
                + "</CamID>\r\n" + "<RecordID>" + oMap.get("RecordID") + "</RecordID>\r\n" + "<Result>"
                + iErrorCode + "</Result>\r\n" + "<ReqCmdID>" + iReqCmdID + "</ReqCmdID>\r\n"
                + "<DBRecordID>0</DBRecordID>\r\n" + "</Response>\r\n";
        return strXml;
    }

    /**
     * 构造TCP报文
     *
     * @return
     */
    public static String buildBodyXml() {

        String plateNumber = getPlateNumber();
        String passTime = ClientTimeUtils.getNowTimeyyyyMMddHHmmssS();

        logger.info("发送车牌：" + plateNumber + "抓拍时间：" + passTime);

        String strXml = "<?xml version=\"1.0\" ?>" +
                "<Vehicle>" +
                "<CarPlate>" + plateNumber + "</CarPlate>" +
                "<PlateColor>0</PlateColor>" +
                "<VehicleSpeed>35</VehicleSpeed>" +
                "<PlateType>99</PlateType>" +
                "<IdentifyStatus>1</IdentifyStatus>" +
                "<VehicleColor>Z</VehicleColor>" +
                "<VehicleType>0</VehicleType>" +
                "<DriveStatus>0</DriveStatus>" +
                "<TollgateID>" + ClientConstant.NUMBER + "</TollgateID>" +
                "<PassTime>" + passTime + "</PassTime>" +
                "<RecordID>3390286093</RecordID>" +
                "<DBRecordID>0</DBRecordID>" +
                "<Image>" +
                "    <ImageIndex>1</ImageIndex>" +
                "    <ImageURL></ImageURL>" +
                "    <ImageType>1</ImageType>" +
                "    <ImageData></ImageData>" +
                "</Image>" +
                "<PicNumber>1</PicNumber>" +
                "<LaneID>1</LaneID>" +
                "<LaneType>1</LaneType>" +
                "<Direction>1</Direction>" +
                "<VehicleBrand>98</VehicleBrand>" +
                "<PlateNumber>1</PlateNumber>" +
                "<VehicleBody></VehicleBody>" +
                "<VehicleLength>0</VehicleLength>" +
                "<DealTag/>" +
                "<PlaceCode></PlaceCode>" +
                "<EquipmentType></EquipmentType>" +
                "<PlateConfidence>0</PlateConfidence>" +
                "<RearPlateConfidence>0</RearPlateConfidence>" +
                "<GlobalComposeFlag>0</GlobalComposeFlag>" +
                "<RedLightStartTime></RedLightStartTime>" +
                "<RedLightEndTime></RedLightEndTime>" +
                "<RedLightTime>0</RedLightTime>" +
                "<LimitedSpeed>70</LimitedSpeed>" +
                "<MarkedSpeed>70</MarkedSpeed>" +
                "<VideoURL></VideoURL>" +
                "<VideoURL2></VideoURL2>" +
                "<VehicleTopX>1180</VehicleTopX><VehicleTopY>0549</VehicleTopY><VehicleBotX>1208</VehicleBotX><VehicleBotY>1738</VehicleBotY><LPRRectTopX>0169</LPRRectTopX><LPRRectTopY>0444</LPRRectTopY><LPRRectBotX>0000</LPRRectBotX><LPRRectBotY>1738</LPRRectBotY><VehicleFace>" +
                "    <VehicleBrand>99</VehicleBrand>" +
                "    <VehicleBrandType></VehicleBrandType>" +
                "    <VehicleBrandYear></VehicleBrandYear>" +
                "    <VehicleBrandModel></VehicleBrandModel>" +
                "    <IsVehicleHead></IsVehicleHead>" +
                "</VehicleFace>" +
                "<PlaceName></PlaceName>" +
                "<PoliceCode></PoliceCode>" +
                "<ReservedField1></ReservedField1>" +
                "<ReservedField2></ReservedField2>" +
                "<DressColor/>" +
                "<ApplicationType/>" +
                "<RearPlateColor/>" +
                "<RearPlateType/>" +
                "<RearVehiclePlateID/>" +
                "<PlateCoincide/>" +
                "<DirectionName/>" +
                "<CamID>HC161-1_1</CamID>" +
                "<ImageURL2/>" +
                "<ImageURL3/>" +
                "<ImageURL4/>" +
                "<TollgateName/>" +
                "</Vehicle>";


        return strXml;
    }

    /**
     * 获取随机车牌
     *
     * @return
     */
    public static String getPlateNumber() {
        Random random = new Random();
        String palteString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String palteArea = "AK";
        //车牌
        String plateNumber = String.valueOf(palteArea.charAt(Math.abs(random.nextInt()) % 2))
                + String.valueOf(palteString.charAt(Math.abs(random.nextInt()) % 10))
                + String.valueOf(palteString.charAt(Math.abs(random.nextInt()) % 36))
                + String.valueOf(palteString.charAt(Math.abs(random.nextInt()) % 36))
                + String.valueOf(palteString.charAt(Math.abs(random.nextInt()) % 36))
                + String.valueOf(palteString.charAt(Math.abs(random.nextInt()) % 36))
                + String.valueOf(palteString.charAt(Math.abs(random.nextInt()) % 10 + 26));
        return plateNumber;
    }
}

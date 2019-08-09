package com.car.base.common.utilities;

/**
 * @author monkjavaer
 * @date 2018/03/08.
 */
public class PathUtil {

//    public static String toFullURLByFullURL(String fullURL) {
//        PropertiesUtil propertiesUtil = new PropertiesUtil("/system.properties");
//        String downloadFilePath = propertiesUtil.getPropertieValue("downloadFilePath");
//
//        return downloadFilePath + "/" + fullURL.substring(fullURL.lastIndexOf("files/")+6, fullURL.length());
//    }

    public static String toFullURLByFastDFSURL(String FastDFSURL) {
        PropertiesUtil propertiesUtil = new PropertiesUtil("/system.properties");
        String photoNginxVip = propertiesUtil.getPropertieValue("photoNginxVip");

        return photoNginxVip + "/" + FastDFSURL;
    }

}

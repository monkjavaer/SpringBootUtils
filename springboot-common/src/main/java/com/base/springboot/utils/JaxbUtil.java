package com.base.springboot.utils;

import com.base.springboot.exception.BaseException;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.net.URL;

/**
 * @Title: XmlUtil
 * @Package: com.m.gis.springboot.common.utils
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/7/2
 * @Version: V1.0
 */
public class JaxbUtil {
    private final static Logger logger = LoggerFactory.getLogger(JaxbUtil.class);

 /*   public static void convertToXml(Object obj, File file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            //格式化输出，即按标签自动换行，否则就是一行输出
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //设置编码（默认编码就是utf-8）
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            //是否省略xml头信息，默认不省略（false）
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
            marshaller.marshal(obj, file);
            //控制台输出
            marshaller.marshal(obj,System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }*/

    public static <T> T convertToJavaBean(Class<T> clz, String resourcePath) {
        Preconditions.checkArgument(StringUtils.isNotBlank(resourcePath),"JaxbUtil convertToJavaBean errors, param resourcePath is null or empty.");

        URL resourceUrl = JaxbUtil.class.getResource(resourcePath);
        Preconditions.checkNotNull(resourceUrl,String.format("JaxbUtil convertToJavaBean errors, param resourcePath = {%s} is not a valid URL.", resourcePath));

        logger.info(String.format("JaxbUtil convertToJavaBean with configuration file {%s}", resourcePath));
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            T t = (T) unmarshaller.unmarshal(resourceUrl);
            return t;
        } catch (JAXBException e) {
            BaseException ex = new BaseException("JaxbUtil convertToJavaBean errors, " + e.toString());
            logger.error(ex.toString());
            throw ex;
        }
    }

}
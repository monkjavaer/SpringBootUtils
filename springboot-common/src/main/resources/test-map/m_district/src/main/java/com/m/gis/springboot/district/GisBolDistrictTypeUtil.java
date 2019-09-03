package com.m.gis.springboot.district;

import com.m.gis.springboot.district.common.GisAbstractDistrictTypeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: GisBolDistrictTypeUtil
 * @Package: com.m.gis.springboot.model
 * @Description: 继承GisAbstractDistrictTypeUtil类，实现getConfigPath方法，返回行政区域资源文件路径，默认为"/district.xml"
 * @Author: monkjavaer
 * @Data: 2018/7/2
 * @Version: V1.0
 */
public class GisBolDistrictTypeUtil extends GisAbstractDistrictTypeUtil {
    private final static Logger logger = LoggerFactory.getLogger(GisBolDistrictTypeUtil.class);

    private GisBolDistrictTypeUtil(){
        super();
    }

    private static class HolderClass {
        private final static GisBolDistrictTypeUtil instance = new GisBolDistrictTypeUtil();
    }

    public static GisBolDistrictTypeUtil getInstance(){
        return HolderClass.instance;
    }

    @Override
    protected String getConfigPath() {
        return getDefalutConfigPath();
    }
}

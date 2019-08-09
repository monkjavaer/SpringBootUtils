package com.m.gis.springboot.district.common;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: GisDistrictTypeCollection
 * @Package: com.m.gis.springboot.bo
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/28
 * @Version: V1.0
 */
@XmlRootElement(name = "DistrictTypeCollection")
@XmlAccessorType(XmlAccessType.FIELD)
public class GisDistrictTypeCollection {
    /**
     * 缓存行政区域类型信息
     */
    @XmlElements(value = { @XmlElement(name = "DistrictType", type = GisDistrictType.class) })
    private List<GisDistrictType> gisDistrictTypes = new ArrayList<>();

    public void setGisDistrictTypes(List<GisDistrictType> gisDistrictTypes) {
        this.gisDistrictTypes = gisDistrictTypes;
    }

    public List<GisDistrictType> getGisDistrictTypes() {
        return gisDistrictTypes;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(GisDistrictType item:gisDistrictTypes){
            builder.append("{").append(item.toString()).append("} ");
        }
        return "GisDistrictTypeCollection{[" + builder.toString() + "]}";
    }
}

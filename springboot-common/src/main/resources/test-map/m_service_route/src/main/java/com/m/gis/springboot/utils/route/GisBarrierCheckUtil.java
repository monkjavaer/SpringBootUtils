package com.m.gis.springboot.utils.route;

import com.m.gis.springboot.exception.GisRouteProcessorException;
import com.m.gis.springboot.geo.base.exception.GisParseGeometryBaseThrowableException;
import com.m.gis.springboot.geo.base.utils.GisGeometryFactoryUtil;
import com.vividsolutions.jts.geom.Geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: GisBarrierCheckUtil
 * @Package: com.m.gis.springboot.utils.route
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/8/9
 * @Version: V1.0
 */
public class GisBarrierCheckUtil {
    /**
     * 障碍区域
     */
    private List<Geometry> barries;

    public GisBarrierCheckUtil(List<String> barries){
        this.barries = new ArrayList<>();
        if (barries != null) {
            for (String item : barries) {
                try {
                    this.barries.add(GisGeometryFactoryUtil.createGeometryByWKT(item));
                } catch (GisParseGeometryBaseThrowableException e) {
                    throw new GisRouteProcessorException(String.format("GisNodeFilter constructor errors, illegal wkt {%s}in barries.", item));
                }
            }
        }
    }

    public List<Geometry> getBarries() {
        return barries;
    }

    public void setBarries(List<Geometry> barries) {
        this.barries = barries;
    }

    /***
     * @Description: 判断几何是否与barries障碍集合相交
     * @Author: monkjavaer
     * @Data: 21:22 2018/8/2
     * @Param: [geom,barries]
     * @Throws
     * @Return boolean
     */
    public boolean isInBarries(Geometry geom){
        for(Geometry item:this.barries){
            if(item.intersects(geom))
                return true;
        }
        return false;
    }

}

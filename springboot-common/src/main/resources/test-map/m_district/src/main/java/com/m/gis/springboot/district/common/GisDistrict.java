package com.m.gis.springboot.district.common;

import com.m.gis.springboot.geo.base.common.GisCoordinate;
import com.m.gis.springboot.geo.base.common.GisPreconditions;
import com.m.gis.springboot.geo.base.exception.GisParseGeometryBaseThrowableException;
import com.m.gis.springboot.geo.base.utils.GisGeometryFactoryUtil;
import com.m.gis.springboot.geo.base.utils.GisSimplifyUtil;
import com.vividsolutions.jts.geom.Geometry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: GisDistrict
 * @Package: com.m.gis.springboot.po
 * @Description:
 * @Author: monkjavaer
 * @Data: 2018/6/22
 * @Version: V1.0
 */
public class GisDistrict {
    private static final Logger logger = LoggerFactory.getLogger(GisDistrict.class);

    /**
     * 默认行政轮廓的抽稀容差值
     */
    private static final Double SIMPLIFIER_TOLERENCE = 0.0001;

    private Integer id;
    private String name;
    private String districtCode;
    private String wkt;
    private Geometry geom;

    public GisDistrict() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getWkt() {
        return wkt;
    }

    public void setWkt(String wkt) {
        try {
            this.geom = GisGeometryFactoryUtil.createGeometryByWKT(wkt,true);
            //抽稀行政轮廓，抽稀容差值设置为10米
            this.geom = GisSimplifyUtil.simplifier(this.geom,SIMPLIFIER_TOLERENCE);
            this.wkt = this.geom.toString();
        } catch (GisParseGeometryBaseThrowableException e) {
            logger.error(e.toString());
        }
    }

    public Geometry getGeom() {
        return geom;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GisDistrict) {
            GisDistrict other = (GisDistrict) obj;
            return other.getDistrictCode().equals(districtCode);
        }
        return false;
    }

    /***
     * @Description: 判断经纬度是否在该行政区域内
     * @Author: monkjavaer
     * @Data: 16:55 2018/6/28
     * @Param: [coordinate]
     * @Throws
     * @Return boolean
     */
    public boolean contains(GisCoordinate coordinate){
        GisPreconditions.checkNotNull(coordinate,"GisDistrict.within funtion errors, param {coordinate} must not be null.");
        GisPreconditions.checkNotNull(this.geom,"GisDistrict.within funtion errors, geom is null.");
        return this.geom.contains(GisGeometryFactoryUtil.createPoint(coordinate));
    }

}

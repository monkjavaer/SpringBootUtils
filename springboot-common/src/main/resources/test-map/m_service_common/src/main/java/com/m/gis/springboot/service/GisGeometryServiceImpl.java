package com.m.gis.springboot.service;

import com.m.gis.springboot.exception.GisGeometryServiceException;
import com.m.gis.springboot.geo.base.common.GisPreconditions;
import com.m.gis.springboot.geo.base.exception.GisParseGeometryBaseThrowableException;
import com.m.gis.springboot.geo.base.utils.GisGeometryFactoryUtil;
import com.m.gis.springboot.mapper.GisPGMapper;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Title: GisGeometryServiceImpl
 * @Package: com.m.gis.springboot.service
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/15
 * @Version: V1.0
 */
@Service
public class GisGeometryServiceImpl implements GisGeometryService{
    @Autowired
    private GisPGMapper gisPGMapper;

    @Override
    public Geometry getGeodesyBuffer(Geometry geom, double distanceInMeters) {
        GisPreconditions.checkGeometry(geom,"getGeodesyBuffer errors, geom is no legal geometry.");
        //GisPreconditions.checkArgument(distanceInMeters>0,"parameter distanceInMeters must be positive.");

        String geomWkt = gisPGMapper.getGeodesyBuffer(geom.toString(),distanceInMeters);
        try {
            geom = GisGeometryFactoryUtil.createGeometryByWKT(geomWkt);
            return geom;
        } catch (GisParseGeometryBaseThrowableException e) {
            throw new GisGeometryServiceException("geometry create buffer errors, because geodesy buffer returned can not be cast to geometry.");
        }
    }
}

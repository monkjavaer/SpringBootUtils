package com.m.gis.springboot.typehandler;

import org.apache.ibatis.type.MappedTypes;
import org.postgis.Polygon;

/**
 * Created by mei on 31/08/2017.
 */
@MappedTypes(Polygon.class)
public class PolygonTypeHandler extends AbstractGeometryTypeHandler<Polygon> {
}

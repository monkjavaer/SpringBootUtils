package com.m.gis.springboot.typehandler;

import org.apache.ibatis.type.MappedTypes;
import org.postgis.Point;

/**
 * Created by mei on 31/08/2017.
 */
@MappedTypes(Point.class)
public class PointTypeHandler extends AbstractGeometryTypeHandler<Point> {
}

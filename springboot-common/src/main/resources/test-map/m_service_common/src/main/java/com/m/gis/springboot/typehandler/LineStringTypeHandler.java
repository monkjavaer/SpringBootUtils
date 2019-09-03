package com.m.gis.springboot.typehandler;

import org.apache.ibatis.type.MappedTypes;
import org.postgis.LineString;

/**
 * Created by mei on 04/09/2017.
 */
@MappedTypes(LineString.class)
public class LineStringTypeHandler extends AbstractGeometryTypeHandler<LineString>{
}

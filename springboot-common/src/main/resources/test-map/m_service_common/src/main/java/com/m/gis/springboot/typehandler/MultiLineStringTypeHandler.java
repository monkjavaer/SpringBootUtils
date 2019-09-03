package com.m.gis.springboot.typehandler;

import org.apache.ibatis.type.MappedTypes;
import org.postgis.MultiLineString;

/**
 * Created by mei on 31/08/2017.
 */
@MappedTypes(MultiLineString.class)
public class MultiLineStringTypeHandler extends AbstractGeometryTypeHandler<MultiLineString> {
}

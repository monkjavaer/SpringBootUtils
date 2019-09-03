package com.m.gis.springboot.typehandler;

import org.apache.ibatis.type.MappedTypes;
import org.postgis.MultiPoint;

/**
 * Created by mei on 04/09/2017.
 */
@MappedTypes(MultiPoint.class)
public class MultiPointTypeHandler extends AbstractGeometryTypeHandler<MultiPoint>{
}

package com.m.gis.springboot.utils.route;

import com.m.gis.springboot.geo.base.common.GisCoordinate;
import com.m.gis.springboot.geo.base.common.GisPreconditions;
import com.m.gis.springboot.geo.base.utils.GisGeometryFactoryUtil;
import com.m.gis.springboot.geo.base.utils.GisOperationsUtil;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Title: GisLinearRouteFilter
 * @Package: com.m.gis.springboot.utils
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/7/31
 * @Version: V1.0
 */
public class GisLinearRouteFilter extends GisAbstractRouteFilter{
    private static final Logger logger = LoggerFactory.getLogger(GisLinearRouteFilter.class);

    /**
     * 缓冲半径因子，默认取线段长度的一半
     */
    private double DEFAULT_BUFFER_FACTOR = 0.5;

    /**
     * 如果两点长度小于0.01度（约合1000米），则固定其缓冲半径为0.01度，否则按照缓冲半径因子来计算
     */
    private double DEFAULT_BUFFER_THRESHOLD = 0.01;

    /*
    思路：给点A-B-C-D四个路径规划的参数点，找出A-B线段的缓冲区，半径为A-B线段的factor*长度，依次找出A-B、B-C、C-D三个缓冲区，合并，得到筛选的路径空间范围
     */
    @Override
    public String getRouteFilterWkt(List<GisCoordinate> points) {
        GisPreconditions.checkArgument((CollectionUtils.isNotEmpty(points))&&(points.size()>1),"GisLinearRouteFilter getRouteFilterWkt errors, points param can not be null and element size should be more than 1.");
        List<Geometry> geomes = new ArrayList<>();
        //两两点组成线段，然后生成缓冲区
        for(int i =0;i<points.size()-1;i++){
            LineString line = GisGeometryFactoryUtil.createLine(new ArrayList<GisCoordinate>(Arrays.asList(points.get(i),points.get(i+1))));
            double bufferRadius = line.getLength()<DEFAULT_BUFFER_THRESHOLD ? DEFAULT_BUFFER_THRESHOLD:line.getLength()*DEFAULT_BUFFER_FACTOR;
            geomes.add(line.buffer(bufferRadius));
        }
        String filterWkt = GisOperationsUtil.union(geomes).toString();
        logger.debug(String.format("filter wkt: %s",filterWkt));
        return filterWkt;
    }
}

package com.m.gis.springboot.geo.base.utils;

import com.m.gis.springboot.geo.base.common.GisCoordinate;
import com.m.gis.springboot.geo.base.common.GisPreconditions;
import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.operation.linemerge.LineMerger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * @author monkjavaer
 * @version V1.0
 * @Title: LineStringUtil.java
 * @Package com.m.gis.geo.base.utils
 * @Description: 线段的计算工具
 * @date 2018年1月4日 上午10:47:50
 */
public class GisLineStringUtil {
    private static Logger logger = LoggerFactory.getLogger(GisLineStringUtil.class);


    /***
     * @Description: 按照ls数组的顺序合并一条线段，ls中相邻的线段必须首尾相连，tolerance为首尾的容差值
     * @Author: monkjavaer
     * @Data: 12:54 2018/9/6
     * @Param: [ls, tolerance]
     * @Throws
     * @Return java.util.List<com.vividsolutions.jts.geom.LineString>
     */
    public static List<LineString> lineMerge(List<LineString> ls, double tolerance){
        GisPreconditions.checkNotNull(ls,"lineMerge function errors, ls param must not be null.");
        GisPreconditions.checkArgument(tolerance>0,"lineMerge function errors, tolerance param must be positive.");

        List<List<LineString>> groups = new ArrayList<>();

        int groupsIndex = 0;
        for(int i=0;i<ls.size()-1;i++){
            LineString current=ls.get(i);
            LineString next=ls.get(i+1);

            if(groupsIndex==groups.size())
                groups.add(new ArrayList<>());
            groups.get(groupsIndex).add(current);

            GisCoordinate endPt=new GisCoordinate(current.getEndPoint().getX(),current.getEndPoint().getY());
            GisCoordinate startPt=new GisCoordinate(next.getStartPoint().getX(),next.getStartPoint().getY());

            double distance = endPt.distance(startPt);
            if(distance>tolerance){
                groupsIndex++;
            }
        }

        if(ls.size()>0){
            if(groupsIndex==groups.size())
                groups.add(new ArrayList<>());
            groups.get(groupsIndex).add(ls.get(ls.size()-1));
        }

        List<LineString> mergeLines = new ArrayList<>();
        for(List<LineString> item:groups){
            List<GisCoordinate> coordinates = new ArrayList<>();
            for(LineString line:item){
                Coordinate[] coords = line.getCoordinates();
                for(Coordinate coord:coords)
                    coordinates.add(new GisCoordinate(coord.x,coord.y));
            }
            mergeLines.add(GisGeometryFactoryUtil.createLine(coordinates));
        }

        return mergeLines;
    }


    /***
     * @Description: 合并若干线段
     * @Author: monkjavaer
     * @Data: 18:45 2018/7/24
     * @Param: [ls]
     * @Throws
     * @Return java.util.Collection<com.vividsolutions.jts.geom.Geometry>
     */
    public static Collection<Geometry> lineMerge(List<LineString> ls){
        GisPreconditions.checkNotNull(ls,"lineMerge function errors, ls param must not be null.");

        LineMerger lineMerger = new LineMerger();
        List<Geometry> list = new ArrayList<Geometry>();
        for(LineString item:ls)
            list.add(item);

        lineMerger.add(list);
        Collection<Geometry> mergerLineStrings = lineMerger.getMergedLineStrings();
        return mergerLineStrings;
    }


    /**
     * @Description: 截取线段的一部分生成子线段，fraction表示比例（0-1）
     * @Author: monkjavaer
     * @Data: 18:21 2018/6/10
     * @Param: [ls, fraction]
     * @Throws
     * @Return com.vividsolutions.jts.geom.LineString
     */
    public static LineString subLineString(LineString ls, double fraction) {
        return subLineString(ls,0,fraction);
    }


    public static LineString subLineString(LineString ls, double begin, double end) {
        GisPreconditions.checkArgument(begin <= end, "GisLineStringUtil subLineString function errors, paramter begin must not be <= end.");
        GisPreconditions.checkArgument(begin >= 0 && end <= 1, "GisLineStringUtil subLineString function errors, paramter begin and end must be between 0 and 1.");

        List<GisCoordinate> coords = new ArrayList<>();

        ArrayList<LineSegment> lineSegmentList = new ArrayList();

        for (int i = 1; i < ls.getCoordinates().length; i++) {
            lineSegmentList.add(new LineSegment(ls.getCoordinates()[i - 1], ls.getCoordinates()[i]));
        }

        double startLength = ls.getLength() * begin;
        double endtLength = ls.getLength() * end - startLength;
        boolean start = false;
        for (LineSegment s : lineSegmentList) {
            if(start){
                coords.add(new GisCoordinate(s.p0.x, s.p0.y));
                //当end=1时，这里double计算有损失，可能导致最后一个终点没有取到
                if(endtLength <= s.getLength()){
                    Coordinate endPoint = s.pointAlong(endtLength / s.getLength());
                    coords.add(new GisCoordinate(endPoint.x, endPoint.y));
                    endtLength -= s.getLength();
                    break;
                }
                else{
                    endtLength -= s.getLength();
                }
            }
            else{
                if(startLength < s.getLength()){
                    Coordinate endPoint = s.pointAlong(startLength / s.getLength());
                    coords.add(new GisCoordinate(endPoint.x, endPoint.y));
                    start = true;
                    if(endtLength <= s.getLength()-startLength){
                        endPoint = s.pointAlong((startLength+endtLength) / s.getLength());
                        coords.add(new GisCoordinate(endPoint.x, endPoint.y));
                        endtLength -= (s.getLength()-startLength);
                        break;
                    }
                    else{
                        endtLength -= (s.getLength()-startLength);
                    }
                }
                else{
                    startLength -= s.getLength();
                }
            }
        }

        if(end==1){
            if(begin==end){
                coords.add(new GisCoordinate(ls.getEndPoint().getX(),ls.getEndPoint().getY()));
                coords.add(new GisCoordinate(ls.getEndPoint().getX(),ls.getEndPoint().getY()));
            }
            else{
                if(endtLength>0){
                    //补充因为double精度计算损失造成的最后一个点未加入
                    coords.add(new GisCoordinate(ls.getEndPoint().getX(),ls.getEndPoint().getY()));
                }
            }
        }

        return GisGeometryFactoryUtil.createLine(coords);
    }




    /**
     * @param ls
     * @param length 单位为lineString的坐标单位
     * @return
     * @name splitLineStringIntoPoints
     * @description 将线段按照length长度分割成多段，返回分割点的list
     * @author monkjavaer
     * @date 2018年1月8日
     */
    public static List<GisCoordinate> splitLineStringIntoPoints(LineString ls, double length) {
        GisPreconditions.checkGeometry(ls, "paramter linestring must not be null or empty.");
        GisPreconditions.checkArgument(length > 0, "parameter length can not be negative.");

        ArrayList<GisCoordinate> resultList = new ArrayList();
        ArrayList<LineSegment> lineSegmentList = new ArrayList();

        for (int i = 1; i < ls.getCoordinates().length; i++) {
            lineSegmentList.add(new LineSegment(ls.getCoordinates()[i - 1], ls.getCoordinates()[i]));
        }

        if (ls.getStartPoint() != null) {
            resultList.add(new GisCoordinate(ls.getStartPoint().getX(), ls.getStartPoint().getY()));
        }

        double neededLength = length;
        for (LineSegment s : lineSegmentList) {
            LineSegment currentSeg = new LineSegment((Coordinate) s.p0.clone(), (Coordinate) s.p1.clone());
            while (currentSeg.getLength() > 0) {
                //如果当前线段长度小于所需长度
                if (currentSeg.getLength() <= neededLength) {
                    neededLength -= currentSeg.getLength();

                    if (neededLength == 0) {
                        resultList.add(new GisCoordinate(currentSeg.p1.x, currentSeg.p1.y));
                        neededLength = length;
                    }
                    currentSeg.setCoordinates(currentSeg.p0, currentSeg.p0);
                } else {
                    Coordinate endPoint = currentSeg.pointAlong(neededLength / currentSeg.getLength());
                    resultList.add(new GisCoordinate(endPoint.x, endPoint.y));
                    neededLength = length;
                    currentSeg.setCoordinates(endPoint, currentSeg.p1);
                }
            }
        }
        if (neededLength < length) {
            if (ls.getEndPoint() != null) {
                resultList.add(new GisCoordinate(ls.getEndPoint().getX(), ls.getEndPoint().getY()));
            }
        }

        return resultList;
    }

    /**
     * @Description: 将线段按照length长度分割成多段，返回分割的线段集合
     * @Author: monkjavaer
     * @Data: 18:38 2018/6/10
     * @Param: [ls, length]
     * @Throws
     * @Return java.util.List<com.vividsolutions.jts.geom.LineString>
     */
    public static List<LineString> splitLineStringIntoParts(LineString ls, double length) {
        GisPreconditions.checkGeometry(ls, "paramter linestring must not be null or empty.");
        GisPreconditions.checkArgument(length > 0, "parameter length can not be negative.");

        // result list for linestrings
        ArrayList<LineString> resultList = new ArrayList();
        // list for linesegments from input linestring
        ArrayList<LineSegment> lineSegmentList = new ArrayList();
        // create LineSegment objects from input linestring and add them to list
        for (int i = 1; i < ls.getCoordinates().length; i++) {
            lineSegmentList.add(new LineSegment(ls.getCoordinates()[i - 1], ls.getCoordinates()[i]));
        }
        LineString currentLineString = null;
        double neededLength = length;
        for (LineSegment s : lineSegmentList) {
            while (s.getLength() > 0) {
                // case: current segment is small enough to be added to the linestring
                if (s.getLength() <= neededLength) {
                    // create linestring if it does not exist
                    if (currentLineString == null) {
                        currentLineString = new GeometryFactory().createLineString(new Coordinate[]{new Coordinate(s.p0), new Coordinate(s.p1)});
                        // just add the new endpoint otherwise
                    } else {
                        Coordinate[] coords = new Coordinate[currentLineString.getCoordinates().length + 1];
                        // copy old coordinates
                        System.arraycopy(currentLineString.getCoordinates(), 0, coords, 0, currentLineString.getCoordinates().length);
                        // add new coordinate at the end
                        coords[coords.length - 1] = new Coordinate(s.p1);
                        // create new linestring
                        currentLineString = new GeometryFactory().createLineString(coords);
                    }
                    neededLength -= s.getLength();
                    s.setCoordinates(s.p1, s.p1);
                    // add linestring to result list if needed length is 0
                    if (neededLength == 0) {
                        resultList.add(currentLineString);
                        currentLineString = null;
                        neededLength = length;
                    }
                    // current segment needs to be cut and added to the linestring
                } else {
                    // get coordinate at desired distance (endpoint of linestring)
                    Coordinate endPoint = s.pointAlong(neededLength / s.getLength());
                    // create linestring if it does not exist
                    if (currentLineString == null) {
                        currentLineString = new GeometryFactory().createLineString(new Coordinate[]{new Coordinate(s.p0), endPoint});
                        // just add the new endpoint otherwise
                    } else {
                        // add new coordinate to linestring
                        Coordinate[] coords = new Coordinate[currentLineString.getCoordinates().length + 1];
                        // copy old coordinates
                        System.arraycopy(currentLineString.getCoordinates(), 0, coords, 0, currentLineString.getCoordinates().length);
                        // add new coordinate at the end
                        coords[coords.length - 1] = endPoint;
                        currentLineString = new GeometryFactory().createLineString(coords);
                    }
                    // add linestring to result list
                    resultList.add(currentLineString);
                    // reset needed length
                    neededLength = length;
                    // reset current linestring
                    currentLineString = null;
                    // adjust segment (calculated endpoint is the new startpoint)
                    s.setCoordinates(endPoint, s.p1);
                }
            }
        }
        // add last linestring if there is a rest
        if (neededLength < length) {
            resultList.add(currentLineString);
        }
        return resultList;
    }

    /***
     * @Description: 计算线段的起始方位角
     * @Author: monkjavaer
     * @Data: 18:50 2018/8/2
     * @Param: [lineString]
     * @Throws
     * @Return double
     */
    public static double startAngle(LineString lineString){
        GisPreconditions.checkArgument(lineString.getCoordinates().length>1,"GisLineStringUtil startAngle errors, lineString param coordinates must have more than 1 points.");
        GisCoordinate start = new GisCoordinate(lineString.getCoordinates()[0].x,lineString.getCoordinates()[0].y);
        GisCoordinate end = new GisCoordinate(lineString.getCoordinates()[1].x,lineString.getCoordinates()[1].y);
        return GisGeodesyUtil.angle(start,end);
    }

    /***
     * @Description: 计算线段的终止方位角
     * @Author: monkjavaer
     * @Data: 18:55 2018/8/2
     * @Param: [lineString]
     * @Throws
     * @Return double
     */
    public static double endAngle(LineString lineString){
        GisPreconditions.checkArgument(lineString.getCoordinates().length>1,"GisLineStringUtil startAngle errors, lineString param coordinates must have more than 1 points.");
        GisCoordinate start = new GisCoordinate(lineString.getCoordinates()[lineString.getCoordinates().length-2].x,lineString.getCoordinates()[lineString.getCoordinates().length-2].y);
        GisCoordinate end = new GisCoordinate(lineString.getCoordinates()[lineString.getCoordinates().length-1].x,lineString.getCoordinates()[lineString.getCoordinates().length-1].y);
        return GisGeodesyUtil.angle(start,end);
    }


    /***
     * @Description: 计算线段地理长度（单位米）
     * @Author: monkjavaer
     * @Data: 21:38 2018/8/2
     * @Param: [lineString]
     * @Throws
     * @Return double
     */
    public static double geographyLength(LineString lineString){
        GisPreconditions.checkGeometry(lineString, "paramter linestring must not be null or empty.");
        Coordinate[] coordinates = lineString.getCoordinates();
        double distance = 0;

        for(int i=0;i<coordinates.length-1;i++){
            distance+=GisGeodesyUtil.distance(new GisCoordinate(coordinates[i].x,coordinates[i].y),new GisCoordinate(coordinates[i+1].x,coordinates[i+1].y));
        }
        return distance;
    }
}


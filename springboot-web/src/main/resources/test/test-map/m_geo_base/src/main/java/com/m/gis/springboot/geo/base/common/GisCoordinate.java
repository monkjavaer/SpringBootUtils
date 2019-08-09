package com.m.gis.springboot.geo.base.common;

import com.m.gis.springboot.geo.base.utils.GisFormatUtil;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: GisCoordinate
 * @Package: springboot.geo.base.common
 * @Description: defined coordinate including longitude and latitude.
 * @Author: monkjavaer
 * @Data: 2018/6/7
 * @Version: V1.0
 */
public class GisCoordinate implements CoordinateInterface, Serializable {
    private static Logger logger = LoggerFactory.getLogger(GisCoordinate.class);

    private static final long serialVersionUID = 7457963026513014856L;
    private double longitude;
    private double latitude;

    /**
     * if truncate double data.
     */
    private Boolean truncate = false;

    public GisCoordinate() {
    }

    public GisCoordinate(boolean truncate) {
        this.truncate = truncate;
    }

    public static List<GisCoordinate> fromGeometry(Geometry geom) {
        GisPreconditions.checkGeometry(geom);
        List<GisCoordinate> gisCoords = new ArrayList<GisCoordinate>();

        Coordinate[] coords = geom.getCoordinates();
        for (Coordinate item : coords) {
            gisCoords.add(new GisCoordinate(item.x, item.y));
        }
        return gisCoords;
    }

    public GisCoordinate(double longitude, double latitude) {
        this(longitude, latitude, false);
    }

    public GisCoordinate(double longitude, double latitude, boolean truncate) {
        GisPreconditions.checkLonLat(longitude, latitude);
        this.latitude = truncate ? GisFormatUtil.formatCoordinate(latitude) : latitude;
        this.longitude = truncate ? GisFormatUtil.formatCoordinate(longitude) : longitude;
        this.truncate = truncate;
    }

    public GisCoordinate(CoordinateInterface other) {
        this(other, false);
    }

    public GisCoordinate(CoordinateInterface other, boolean truncate) {
        this(other.getLongitude(), other.getLatitude(), truncate);
    }

    public GisCoordinate(GisCoordinate other) {
        this(other.longitude, other.latitude, other.truncate);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        GisPreconditions.checkLonLat(longitude, 0.0);
        this.longitude = truncate ? GisFormatUtil.formatCoordinate(longitude) : longitude;
    }

    public void setLatitude(Double latitude) {
        GisPreconditions.checkLonLat(0.0, latitude);
        this.latitude = truncate ? GisFormatUtil.formatCoordinate(latitude) : latitude;
    }

    public double distance(GisCoordinate pt) {
        return Math.sqrt(Math.abs(pt.getLongitude() - this.longitude) * Math.abs(pt.getLongitude() - this.longitude) +
                Math.abs(pt.getLatitude() - this.latitude) * Math.abs(pt.getLatitude() - this.latitude));
    }

    public Coordinate toJTSCoordinate() {
        return new Coordinate(this.longitude, this.latitude);
    }

    @Override
    public String toString() {
        return String.format("(" + longitude + "," + latitude + ")");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GisCoordinate) {
            GisCoordinate other = (GisCoordinate) obj;
            return distance(other)<GisBaseConstants.DEFAULT_COORDINATE_TOLERANCE_IN_DEGRESS ? true:false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 42;
        long latBits = Double.doubleToLongBits(latitude);
        long lonBits = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (latBits ^ (latBits >>> 32));
        result = 31 * result + (int) (lonBits ^ (lonBits >>> 32));
        return result;
    }
}

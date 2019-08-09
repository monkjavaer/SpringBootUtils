package com.m.gis.springboot.geo.base.common;

import com.m.gis.springboot.geo.base.utils.GisGeometryFactoryUtil;
import com.vividsolutions.jts.geom.Geometry;

import java.io.Serializable;

public class GisBoundingBox implements Serializable {
	private static final long serialVersionUID = -7145192134410261076L;
	private double minLat;
	private double maxLat;
	private double minLon;
	private double maxLon;

	/**
	 * create a bounding box defined by two coordinates
	 */
	public GisBoundingBox(GisCoordinate p1, GisCoordinate p2) {
		this(p1.getLatitude(), p2.getLatitude(), p1.getLongitude(), p2.getLongitude());
	}

	/**
	 *
	 * @param y1 纬度
	 * @param y2 纬度
	 * @param x1 经度
	 * @param x2 经度
	 */
	public GisBoundingBox(double y1, double y2, double x1, double x2) {
		minLon = Math.min(x1, x2);
		maxLon = Math.max(x1, x2);
		minLat = Math.min(y1, y2);
		maxLat = Math.max(y1, y2);
	}

	/**
	 *
	 * @param geom 空间几何
	 */
	public GisBoundingBox(Geometry geom){
		Geometry envelope = geom.getEnvelope();
		com.vividsolutions.jts.geom.Coordinate[] coords = envelope.getCoordinates();
		minLon = Math.min(coords[1].x, coords[3].x);
		maxLon = Math.max(coords[1].x, coords[3].x);
		minLat = Math.min(coords[1].y, coords[3].y);
		maxLat = Math.max(coords[1].y, coords[3].y);
	}

	public GisBoundingBox(GisBoundingBox that) {
		this(that.minLat, that.maxLat, that.minLon, that.maxLon);
	}

	public GisCoordinate getUpperLeft() {
		return new GisCoordinate(minLon, maxLat);
	}

	public GisCoordinate getLowerRight() {
		return new GisCoordinate(maxLon, minLat);
	}

	public double getLatitudeSize() {
		return maxLat - minLat;
	}

	public double getLongitudeSize() {
		return maxLon - minLon;
	}
	
	public GisBoundingBox clone(){
		return new GisBoundingBox(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof GisBoundingBox) {
			GisBoundingBox that = (GisBoundingBox) obj;
			return minLat == that.minLat && minLon == that.minLon && maxLat == that.maxLat && maxLon == that.maxLon;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + hashCode(minLat);
		result = 37 * result + hashCode(maxLat);
		result = 37 * result + hashCode(minLon);
		result = 37 * result + hashCode(maxLon);
		return result;
	}

	private static int hashCode(double x) {
		long f = Double.doubleToLongBits(x);
		return (int) (f ^ (f >>> 32));
	}

	public boolean contains(GisCoordinate point) {
		return (point.getLatitude() >= minLat) && (point.getLongitude() >= minLon) && (point.getLatitude() <= maxLat)
				&& (point.getLongitude() <= maxLon);
	}
	
	public boolean contains(double longitude,double latitude) {
		return (latitude >= minLat) && (longitude >= minLon) && (latitude <= maxLat)
				&& (longitude <= maxLon);
	}

	public boolean intersects(GisBoundingBox other) {
		return !(other.minLon > maxLon || other.maxLon < minLon || other.minLat > maxLat || other.maxLat < minLat);
	}

	public Geometry toGeometry(){
		return GisGeometryFactoryUtil.createEnvelop(minLat, minLon, maxLat, maxLon);
	}
	
	
	@Override
	public String toString() {
		return getUpperLeft() + " -> " + getLowerRight();
	}

	public GisCoordinate getCenterPoint() {
		double centerLatitude = (minLat + maxLat) / 2;
		double centerLongitude = (minLon + maxLon) / 2;
		return new GisCoordinate(centerLongitude,centerLatitude);
	}

	public void expandToInclude(GisBoundingBox other) {
		if (other.minLon < minLon) {
			minLon = other.minLon;
		}
		if (other.maxLon > maxLon) {
			maxLon = other.maxLon;
		}
		if (other.minLat < minLat) {
			minLat = other.minLat;
		}
		if (other.maxLat > maxLat) {
			maxLat = other.maxLat;
		}
	}

	public double getMinLon() {
		return minLon;
	}

	public double getMinLat() {
		return minLat;
	}

	public double getMaxLat() {
		return maxLat;
	}

	public double getMaxLon() {
		return maxLon;
	}
}

package com.m.gis.springboot.geo.base.utils;

import com.m.gis.springboot.common.exception.GisIllegalParameterCommonException;
import com.m.gis.springboot.geo.base.common.CoordinateInterface;
import com.m.gis.springboot.geo.base.common.GisBoundingBox;
import com.m.gis.springboot.geo.base.common.GisPreconditions;
import com.m.gis.springboot.geo.base.exception.GisParseGeometryBaseThrowableException;
import com.vividsolutions.jts.geom.Geometry;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

public class GisFilterUtil {
	private static Logger logger = LoggerFactory.getLogger(GisFilterUtil.class);

	private static long fastThreshold = 10000;
	
	/**
	 * 
	 * @name within 
	 * @description  返回在geom空间几何包含的点
	 * @param ptList
	 * @param geom
	 * @return 
	 * @author monkjavaer
	 * @date 2017年8月26日
	 */
	public static <T extends CoordinateInterface> List<T> within(List<T> ptList, Geometry geom){
		GisPreconditions.checkArgument(CollectionUtils.isNotEmpty(ptList),"within functions errors, param {ptList} is null or empty.");
		GisPreconditions.checkGeometry(geom,"within functions errors, param {geom} is not a legal geometry object.");

		long withinCount=0L;  
		long start = System.currentTimeMillis();
		
		Iterator<T> iter = ptList.iterator();	
        List<T> resultLst = new ArrayList<T>();
		while(iter.hasNext()){
			T obj = iter.next();
			if(GisPreconditions.checkLon(obj.getLongitude())&&GisPreconditions.checkLat(obj.getLatitude())){
				Geometry pt = GisGeometryFactoryUtil.createPoint(obj.getLongitude(), obj.getLatitude());
				if(pt.within(geom)){
					withinCount++;
					resultLst.add(obj);
				}
			}
		}
		long end = System.currentTimeMillis();
		logger.debug(Thread.currentThread().getName()+" List size:"+withinCount+",Time:"+(end-start)/1000.0);  
		logger.debug("filter size:"+resultLst.size());
        return resultLst;  	
	}
	
	/**
	 * 
	 * @name within
	 * @description  返回在空间几何中包含的点
	 * @param ptList
	 * @param wkt 空间几何的wkt字符串
	 * @return 
	 * @author monkjavaer
	 * @date 2017年8月26日
	 */
	public static <T extends CoordinateInterface> List<T> within(List<T> ptList, String wkt) {
		GisPreconditions.checkArgument(CollectionUtils.isNotEmpty(ptList),"within functions errors, param {ptList} is null or empty.");
		GisPreconditions.checkArgument(StringUtils.isNotBlank(wkt),"within functions errors, param {wkt} is not a legal geometry object.");

		Geometry geom = null;
		try {
			geom = GisGeometryFactoryUtil.createGeometryByWKT(wkt);
		} catch (GisParseGeometryBaseThrowableException e) {
			throw new GisIllegalParameterCommonException("within function errors, param {wkt} can not be parsed as a legal geometry object.");
		}
		return within(ptList,geom);		
	}
	
	
	public static <T extends CoordinateInterface> List<T> within(List<T> ptList, GisBoundingBox bbox){
		GisPreconditions.checkArgument(CollectionUtils.isNotEmpty(ptList),"within functions errors, param {ptList} is null or empty.");
		GisPreconditions.checkNotNull(bbox,"within functions errors, param {bbox} is null.");

		long withinCount=0L;  
		long start = System.currentTimeMillis();
		
		Iterator<T> iter = ptList.iterator();	
        List<T> resultLst = new ArrayList<T>();
		while(iter.hasNext()){
			T obj = iter.next();
			if(bbox.contains(obj.getLongitude(),obj.getLatitude())){
				withinCount++;
				resultLst.add(obj);
			}
		}
		long end = System.currentTimeMillis();
		logger.debug(Thread.currentThread().getName()+" List size:"+withinCount+",Time:"+(end-start)/1000.0);  
        return resultLst;  			
	}
	
	
	
	/**
	 * 根据cpu处理器数目进行并行加速计算，比within要更快
	 * @param ptList 待筛选点列表，保留ptList源数据的完整性
	 * @param geom 空间几何
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static <T extends CoordinateInterface> List<T> withinFast(List<T> ptList, Geometry geom) throws InterruptedException, ExecutionException{
		if(ptList.size()<fastThreshold){
			return within(ptList,geom);
		}

		GisPreconditions.checkArgument(CollectionUtils.isNotEmpty(ptList),"withinFast functions errors, param {ptList} is null or empty.");
		GisPreconditions.checkGeometry(geom,"within functions errors, param {geom} is not a legal geometry object.");
		
		int threadNums = Runtime.getRuntime().availableProcessors();
		logger.debug("availableProcessors:"+threadNums);
			
		int size = ptList.size()/threadNums;
		ExecutorService exec=Executors.newFixedThreadPool(threadNums);  
		List<Callable<List<T>>> callList=new ArrayList<Callable<List<T>>>();
		
		for(int i = 0;i<threadNums;i++){
			int end = 0;
			if(i==threadNums-1)
				end = ptList.size();
			else
				end=size*(i+1);  

			List<T> subLst = ptList.subList(i*size, end);
			 //采用匿名内部类实现  
	        callList.add(new Callable<List<T>>(){  
	            public List<T> call() throws Exception {  
	                long withinCount=0L;  
	                List<T> resultLst = new ArrayList<T>();
	    			long start = System.currentTimeMillis();
	                for(T item:subLst){
						if(!GisPreconditions.checkLon(item.getLongitude()) || !GisPreconditions.checkLat(item.getLatitude()))
							continue;
	        			Geometry pt = GisGeometryFactoryUtil.createPoint(item.getLongitude(), item.getLatitude());
	                	if(pt.within(geom)){
	    					withinCount++;
	    					resultLst.add(item);
	    				} 
	                } 
	    			long end = System.currentTimeMillis();
	                logger.debug(Thread.currentThread().getName()+" List size:"+withinCount+",time:"+(end-start)/1000.0);  
	                return resultLst;  
	            }  
	        });  
		}
		
		List<Future<List<T>>> futureList = exec.invokeAll(callList);
		List<T> totalLst = new ArrayList<T>();
		for (Future<List<T>> future : futureList) {
			totalLst.addAll(future.get());
		}
		exec.shutdown();
		logger.debug("filter size:"+totalLst.size());
		return totalLst;
	}
	
	
	
	/**
	 * 根据cpu处理器数目进行并行加速计算，比within要更快
	 * @param ptList 待筛选点列表，保留ptList源数据的完整性
	 * @param wkt 几何范围的wkt字符串
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static <T extends CoordinateInterface> List<T> withinFast(List<T> ptList, String wkt) throws InterruptedException, ExecutionException{
		if(ptList.size()<fastThreshold){
			return within(ptList,wkt);
		}

		GisPreconditions.checkArgument(CollectionUtils.isNotEmpty(ptList),"within functions errors, param {ptList} is null or empty.");
		GisPreconditions.checkArgument(StringUtils.isNotBlank(wkt),"within functions errors, param {wkt} is not a legal geometry object.");

		Geometry geom;
		try {
			geom = GisGeometryFactoryUtil.createGeometryByWKT(wkt);
		} catch (GisParseGeometryBaseThrowableException e) {
			throw new GisIllegalParameterCommonException("within function errors, param {wkt} can not be parsed as a legal geometry object.");
		}
		return withinFast(ptList,geom);
	}
}

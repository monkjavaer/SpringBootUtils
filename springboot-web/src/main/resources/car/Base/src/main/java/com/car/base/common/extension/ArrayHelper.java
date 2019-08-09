package com.car.base.common.extension;

import java.util.ArrayList;
import java.util.List;

/**
 * Array  
 * @author monkjavaer
 *
 */
public class ArrayHelper {
	
	/**
	 * 从IntegerArray中删除item元素	 
	 * @param array
	 * @param item
	 * @return
	 */
	public final static Integer[] removeArrayItem(Integer[] array, Integer item){
		List<Integer> list=new ArrayList<Integer>();
		for(Integer arrayItem : array){
			if(!arrayItem.equals(item))
			{list.add(arrayItem);}
		}
		
		Integer[] ret=new Integer[list.size()];
		list.toArray(ret);
		return ret;
	}

	
	/**
	 * 从IntegerArray中删除item元素	 
	 * @param array
	 * @param item
	 * @return
	 */
	public final static Short[] removeArrayItem(Short[] array, Short item){
		List<Short> list=new ArrayList<Short>();
		for(Short arrayItem : array){
			if(!arrayItem.equals(item))
			{list.add(arrayItem);}
		}
		
		Short[] ret=new Short[list.size()];
		list.toArray(ret);
		return ret;
	}
	
	/**
	 * 从IntegerArray中删除item元素	 
	 * @param array
	 * @param item
	 * @return
	 */
	public final static String[] removeArrayItem(String[] array, String item){
		List<String> list=new ArrayList<String>();
		for(String arrayItem : array){
			if(!arrayItem.equals(item))
			{list.add(arrayItem);}
		}
		
		String[] ret=new String[list.size()];
		list.toArray(ret);
		return ret;
	}
	
	/**
	 * 将IntegerArray中的元素连接成字符串，元素之间用split分隔
	 * @param array
	 * @param split
	 * @return
	 */
	public final static String toString(Integer[] array, String split){
		StringBuilder sb=new StringBuilder();
		for(Integer item : array){
			sb.append(item.toString());
			sb.append(split);
		}
		String ret=sb.toString();
		if(ret.endsWith(split))
		{ret = ret.substring(0, ret.length()-split.length());}
		return ret;
	}
	
	/**
	 * 将IntegerArray中的元素连接成字符串，元素之间用split分隔
	 * @param array
	 * @param split
	 * @return
	 */
	public final static String toString(Short[] array, String split){
		StringBuilder sb=new StringBuilder();
		for(Short item : array){
			sb.append(item.toString());
			sb.append(split);
		}
		String ret=sb.toString();
		if(ret.endsWith(split))
		{ret = ret.substring(0, ret.length()-split.length());}
		return ret;
	}
	
}

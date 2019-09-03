package com.base.springboot.car.Base.src.main.java.com.car.base.model.models;

import java.util.List;

/**
 * 实体基类
 * @author monkjavaer
 * 
 */
public class ResultTuple<T> {
	public List<T> items;
	public int count;
	
	public ResultTuple(List<T> items, int count) {
		this.items = items;
		this.count = count;
	}

	
	
	
}

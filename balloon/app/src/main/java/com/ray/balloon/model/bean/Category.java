package com.ray.balloon.model.bean;

import java.io.Serializable;

/**
 * 品类
 * @Copyright Copyright © 2014 蓝色互动. All rights reserved.
 *
 * @author ZhuFan
 * @Date: 2015-4-3
 */
public class Category implements Serializable{

	private static final long serialVersionUID = -1729978113958393881L;
	
	public long id;
	public String name;
	public int areaId;
	
	public Category(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Category(){}

}

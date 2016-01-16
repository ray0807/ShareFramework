package com.ryan.corelibstest.model.bean;

import java.io.Serializable;

/**
 * 父类数据结构 map里面无限添加对象
 */
public class GenericBaseData<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/* 标志位 : 1成功, 0失败 */
	public int status;

	/* 返回的信息 */
	public String msg;

	/* 总数据结构 */
	public GenericMapData<T> data;

}
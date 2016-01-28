package com.ryan.corelibstest.model.bean;

import java.io.Serializable;

public class BaseData implements Serializable {

	private static final long serialVersionUID = 1L;

	public int status;
	public String msg;
	public MapData data;
	public Page page;

}
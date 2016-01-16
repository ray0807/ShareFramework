package com.ryan.corelibstest.model.bean;

import java.util.List;

public class TCity implements Cloneable{
	
	public int id;
	public String first;
	public String fullName;
	public String full;
	public String name;
	public List<TCity> nextArea;
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	
}

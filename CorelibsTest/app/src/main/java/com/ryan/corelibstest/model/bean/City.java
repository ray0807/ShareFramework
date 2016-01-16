package com.ryan.corelibstest.model.bean;

import java.io.Serializable;

public class City implements Serializable{

	private static final long serialVersionUID = -7884283842711412592L;
	
	public String id;
	public String name;
	public String first;
	public String full;
	
	public City(String id, String name, String pinyin) {
		super();
		this.id = id;
		this.name = name;
		this.first = pinyin;
	}
	
	public City(){}

}

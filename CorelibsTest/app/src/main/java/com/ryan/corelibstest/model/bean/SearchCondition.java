package com.ryan.corelibstest.model.bean;

import java.io.Serializable;

public class SearchCondition implements Serializable {

	private static final long serialVersionUID = -1991793649493333688L;
	public Type type;
	public long id;
	public String name;
	public String title = "";

	public SearchCondition(Type type, long id, String name) {
		this.type = type;
		this.id = id;
		this.name = name;
	}

	public SearchCondition(Type type, long id, String name, String title) {
		this.type = type;
		this.id = id;
		this.name = name;
		this.title = title;
	}

	public enum Type {
		Category(0x1), Brand(0x2);

		private int type;

		private Type(int type) {
			this.type = type;
		}
		
		public int getType() {
			return type;
		}
	}
}

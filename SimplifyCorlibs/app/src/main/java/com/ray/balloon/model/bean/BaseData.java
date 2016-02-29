package com.ray.balloon.model.bean;

import java.io.Serializable;

public class BaseData implements Serializable {

	private static final long serialVersionUID = 1L;

	public int ErrorCode;
	public String Msg;
	public MapData Data;
	public Page CurrentPage;

	@Override
	public String toString() {
		return "BaseData{" +
				"ErrorCode=" + ErrorCode +
				", Msg='" + Msg + '\'' +
				", Data=" + Data +
				", CurrentPage=" + CurrentPage +
				'}';
	}
}
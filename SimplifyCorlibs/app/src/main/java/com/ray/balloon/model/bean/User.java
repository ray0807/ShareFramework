package com.ray.balloon.model.bean;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = -5675772181862475249L;
	public long id;
	public String MemberId;
	public String Account;
	public String phone;
	public String Password;
	public String ImageUrl;
	public String Address;

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", MemberId=" + MemberId +
				", Account='" + Account + '\'' +
				", phone='" + phone + '\'' +
				", Password='" + Password + '\'' +
				", ImageUrl='" + ImageUrl + '\'' +
				", Address='" + Address + '\'' +
				'}';
	}
}

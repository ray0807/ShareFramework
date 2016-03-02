package com.ray.balloon.model.bean;

import java.io.Serializable;

/**
 * 收货地址
 * @Copyright Copyright © 2014 蓝色互动. All rights reserved.
 *
 * @author ZhuFan
 * @Date: 2015-4-3
 */
public class ReceiveAddress implements Serializable {
	
	private static final long serialVersionUID = -324448301081448635L;
	
	public int id;
	public int areaId;
	public String areaName;
	/**
	 * 收货地址
	 */
	public String receiveAddress;
	/**
	 * 收件人电话
	 */
	public String receivePhone;
	/**
	 * 收件人姓名
	 */
	public String receiveName;
	/**
	 * 邮政编码
	 */
	public String postalCode;
	/**
	 * 次要 0, 主要 1
	 */
	public int isDefault;
	
	public ReceiveAddress(int id, String receive_address, String receive_phone, String receive_name, String postal_code) {
		super();
		this.id = id;
		this.receiveAddress = receive_address;
		this.receivePhone = receive_phone;
		this.receiveName = receive_name;
		this.postalCode = postal_code;
	}
	
	public ReceiveAddress(){}
}

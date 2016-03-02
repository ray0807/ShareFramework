package com.ray.balloon.model.bean;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable{

	private static final long serialVersionUID = -3150381568633439430L;
	
	public long id;
	public String orderNumber;
	public List<OrderItem> orderItemList;
	/** (0下单)(1制作)(2运送)(3到达) **/
	public int payStatus;
	public double sumPrice;
	public int count;
	public long receiveId;
	public ReceiveAddress shopAddress;
}

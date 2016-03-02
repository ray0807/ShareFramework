package com.ray.balloon.model.bean;

import java.io.Serializable;

public class OrderItem implements Serializable{

	private static final long serialVersionUID = -6128653220164188345L;
	public Product product;
	public int count;
	public long productId;
	public String productTitle;
	public double price;
	public double itemPrice;
	public String logo;
}

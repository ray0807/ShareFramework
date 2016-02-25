package com.ray.balloon.model.bean;

import java.io.Serializable;
import java.util.List;

public class MapData implements Serializable {
	private static final long serialVersionUID = 1013978257732562783L;
	
	public List<Product> list;
	
	public Product product;
	
	public List<OrderItem> cartItems;
	
	public List<Category> categories;
	
	public List<Order> orderList;
	
	public Order order;
	
	public User member;

	public String code;
	
	public List<RecommendAd> adverList;
	
	public List<Category> hotCategoryList;
	
	public List<Category> allCategories;
	
	public List<Brand> brandList;
	
	public String icon;
	
	public List<ReceiveAddress> shopAddressList;
	
	public SinglePage regAgreement, form;
}

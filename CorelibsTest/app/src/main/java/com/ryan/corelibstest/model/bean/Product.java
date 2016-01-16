package com.ryan.corelibstest.model.bean;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable{

	private static final long serialVersionUID = -9131831209668505397L;
	public long id;
	public String title; // 产品名称
	public long categoryId; // 商品分类Id
	public String category; // 商品分类
	public long brandId; // 商标Id
	public String brand; // 商标名称
	public String description; // 商品描述
	public String descSrc; // 描述文件链接
	public double price; // 此产品所有型号正品茂平台最低价

	public String logo;
	public String img1;
	public String img2;
	public List<String> imgs;
	public String module;// 型号规格
	public String storege;// 供应库存
	public String alkaliResistant;// 耐碱性描述
	public String zheGaiLi;// 遮盖力描述
	public String measurementUnit; // 计量单位
	public String color;// 颜色
	public String xiCaResistant;// 耐洗擦性
	public String waterResistant;// 耐水性
	public String storagePeriod; // 保质期
	public String dryingTime;// 干燥时间
	
	public Product(long id, String logo, double price, String title){
		this.id = id;
		this.logo = logo;
		this.price = price;
		this.title = title;
	}
	
	public Product() {}

}

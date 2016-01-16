package com.ryan.corelibstest.model.bean;

import java.util.ArrayList;
import java.util.List;

public class MainDatas {
	private static List<Category> categories = new ArrayList<Category>();
	private static List<Brand> brands = new ArrayList<Brand>();
	private static List<Category> hotCategories = new ArrayList<Category>();

	public static List<Category> getCategories() {
		return categories;
	}

	public static void setCategories(List<Category> categories) {
		MainDatas.categories = categories;
	}

	public static List<Brand> getBrands() {
		return brands;
	}

	public static void setBrands(List<Brand> brands) {
		MainDatas.brands = brands;
	}

	public static List<Category> getHotCategories() {
		return hotCategories;
	}

	public static void setHotCategories(List<Category> hotCategories) {
		MainDatas.hotCategories = hotCategories;
	}

}

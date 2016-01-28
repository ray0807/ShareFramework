package com.corelibs.logic;

import com.corelibs.logic.BaseLogic.NListener;

import java.util.HashMap;

/**
 * 网络请求封装类, 可以使用链式方式调用
 * @Copyright Copyright © 2014 蓝色互动. All rights reserved.
 *
 * @author ZhuFan
 * @Date: 2015-6-1
 * @param <T>
 */
@SuppressWarnings("rawtypes")
public class NetworkParams<T> {
	public static final String RAW_PARAM = "NetworkParams_RAW_PARAM";
	/** 请求参数 */
	private HashMap<String, String> params = new HashMap<String, String>();
	/** 请求地址 */
	private String url;
	/** 解析基类 */
	private Class baseClass;
	/** 解析子类 */
	private Class childClass;
	/** 请求回调 */
	private NListener<T> listener;
	private boolean isRaw = false;
	private String token = "";
	
	/**
	 * 重置请求参数, 不会重置URL等其他参数
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @return 
	 * @return NetworkParams<T>
	 * @throws 
	 * @Date 2015-6-1 下午3:34:13
	 */
	public NetworkParams<T> resetParams(){
		if(params != null)
			params.clear();
		return this;
	}
	
	/**
	 * 重置所有网络请求参数
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @return 
	 * @return NetworkParams<T>
	 * @throws 
	 * @Date 2015-6-1 下午3:35:18
	 */
	public NetworkParams<T> resetAll(){
		if(params != null)
			params.clear();
		url = "";
		listener = null;
		return this;
	}
	
	/**
	 * 获取请求参数
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @return 
	 * @return HashMap<String,String>
	 * @throws 
	 * @Date 2015-6-1 下午3:36:01
	 */
	public HashMap<String, String> getParams() {
		return params;
	}
	
	/**
	 * 替换请求参数
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @param params
	 * @return 
	 * @return NetworkParams<T>
	 * @throws 
	 * @Date 2015-6-1 下午3:37:28
	 */
	public NetworkParams<T> setParams(HashMap<String, String> params) {
		this.params = params;
		return this;
	}
	
	/**
	 * 压入请求参数
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @param key
	 * @param value
	 * @return 
	 * @return NetworkParams<T>
	 * @throws 
	 * @Date 2015-6-1 下午3:37:41
	 */
	public NetworkParams<T> addParam(String key, String value){
		if(params == null)
			params = new HashMap<String, String>();
		params.put(key, value);
		return this;
	}
	
	/**
	 * 获取网络地址
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @return 
	 * @return String
	 * @throws 
	 * @Date 2015-6-1 下午3:37:51
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * 设置网络地址
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @param url
	 * @return 
	 * @return NetworkParams<T>
	 * @throws 
	 * @Date 2015-6-1 下午3:37:58
	 */
	public NetworkParams<T> setUrl(String url) {
		this.url = url;
		return this;
	}
	
	/**
	 * 获取token
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @return 
	 * @return String
	 * @throws 
	 * @Date 2015-6-1 下午3:37:51
	 */
	public String getToken() {
		return token;
	}
	
	/**
	 * 设置token
	 */
	public NetworkParams<T> setToken(String token) {
		this.token = token;
		return this;
	}
	
	/**
	 * 获取网络地址
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @return 
	 * @return String
	 * @throws 
	 * @Date 2015-6-1 下午3:37:51
	 */
	public boolean isRaw() {
		return isRaw;
	}
	
	/**
	 * 设置网络地址
	 */
	public NetworkParams<T> setRaw(boolean isRaw) {
		this.isRaw = isRaw;
		return this;
	}
	
	/**
	 * 获取解析基类
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @return 
	 * @return Class<T>
	 * @throws 
	 * @Date 2015-6-1 下午3:38:05
	 */
	public Class getBaseClass() {
		return baseClass;
	}
	
	/**
	 * 设置解析基类
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @param baseClass
	 * @return 
	 * @return NetworkParams<T>
	 * @throws 
	 * @Date 2015-6-1 下午3:38:12
	 */
	public NetworkParams<T> setBaseClass(Class baseClass) {
		this.baseClass = baseClass;
		return this;
	}
	
	/**
	 * 获取解析子类
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @return 
	 * @return Class<T>
	 * @throws 
	 * @Date 2015-6-1 下午3:38:18
	 */
	public Class getChildClass() {
		return childClass;
	}
	
	/**
	 * 设置解析子类
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @param childClass
	 * @return 
	 * @return NetworkParams<T>
	 * @throws 
	 * @Date 2015-6-1 下午3:38:27
	 */
	public NetworkParams<T> setChildClass(Class childClass) {
		this.childClass = childClass;
		return this;
	}
	
	/**
	 * 获取请求回调
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @return 
	 * @return NListener<T>
	 * @throws 
	 * @Date 2015-6-1 下午3:38:33
	 */
	public NListener<T> getListener() {
		return listener;
	}
	
	/**
	 * 设置请求回调
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @param listener
	 * @return 
	 * @return NetworkParams<T>
	 * @throws 
	 * @Date 2015-6-1 下午3:38:43
	 */
	public NetworkParams<T> setListener(NListener<T> listener) {
		this.listener = listener;
		return this;
	}

}

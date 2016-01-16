package com.corelibs.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * Copyright © 2015 蓝色互动. All rights reserved.
 * 
 * @Title :SharedPreferencesUtil.java
 * @Prject :YingWangTechnology
 * @Package com.bm.yingwang.utils
 * @Description : SharedPreferencesUtil工具类
 * @author : zhangbin
 * @date 2015-1-26
 * @version : 1.0
 */
public class SharedPreferencesUtil {

	private Editor editor;
	private SharedPreferences sp;

	public SharedPreferencesUtil(Context context, String sharedName) {

		sp = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
		editor = sp.edit();
	}

	/**
	 * 保存一个字符串
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean saveString(String key, String value) {
		try {
			editor.putString(key, value);
			return editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获得一个字符串
	 * 
	 * @param key
	 * @return
	 */
	public String getStringByKey(String key) {
		try {
			return sp.getString(key, "");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 全部清除
	 */
	public void clear() {
		sp.edit().clear().commit();
	}
	
	
	/**
	 * @author GuoHao
	 * @Package com.bm.corelibs.utils
	 * @param name 
	 * @return void
	 * @throws 
	 * 清除单个
	 * @Date 2015年6月1日 下午5:05:41
	 */
	public void clearInfo(String name){
		try {
			
			sp.edit().remove(name).commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

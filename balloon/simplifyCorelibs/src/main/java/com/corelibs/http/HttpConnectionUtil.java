package com.corelibs.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 
 * Copyright © 2015 蓝色互动. All rights reserved.
 * 
 * @Title :HttpConnectionUtil.java
 * @Prject :YingWangTechnology
 * @Package com.bm.yingwang.http
 * @Description : Http请求工具类
 * @author  : zhangbin
 * @date 2015-1-30
 * @version : 1.0
 */
public class HttpConnectionUtil {
   
	/**
	 * 判断手机网络是否打开
	 * 
	 * @param context
	 * @return
	 */
	public static boolean CheckInternetConn(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivity.getActiveNetworkInfo();
		if (null != info && info.isConnected()) {
			return true;
		} else {
			return false;
		}
	}
}

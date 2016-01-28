package com.corelibs.logic;

import com.android.volley.VolleyError;
import com.corelibs.http.HttpVolleyRequest;

/**
 * 基础逻辑, 内部包含一个{@link NetworkParams}, 通过edit()方法来设置网络参数, 通过doPost()/doGet()发送网络请求
 * @Copyright Copyright © 2014 蓝色互动. All rights reserved.
 *
 * @author ZhuFan
 * @Date: 2015-6-1
 * @param <T>
 */
public class BaseLogic<T> {

	private NetworkParams<T> params = new NetworkParams<T>();

	public BaseLogic() {}
	
	/**
	 * 获取网络参数并编辑
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @return 
	 * @return NetworkParams<T>
	 * @throws 
	 * @Date 2015-6-1 下午3:40:02
	 */
	public NetworkParams<T> edit() {
		return params;
	}
	
	/**
	 * 强制设置网络参数
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @param params 
	 * @return void
	 * @throws 
	 * @Date 2015-6-1 下午3:40:22
	 */
	public void setParams(NetworkParams<T> params) {
		this.params = params;
	}
	
	/**
	 * 发送post请求
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic 
	 * @return void
	 * @throws 
	 * @Date 2015-6-1 下午3:40:33
	 */
	@SuppressWarnings("unchecked")
	public void doPost() {
		HttpVolleyRequest<T> request = new HttpVolleyRequest<T>();
		request.HttpVolleyRequestPost(params.getUrl(), params.getParams(), params.getBaseClass(), params.getChildClass(), params.getListener(), params.getToken());
	}
	
	/**
	 * 发送get请求
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic 
	 * @return void
	 * @throws 
	 * @Date 2015-6-1 下午3:40:41
	 */
	public void doGet() {

	}
	
	/**
	 * 网络请求回调函数
	 * @Copyright Copyright © 2014 蓝色互动. All rights reserved.
	 *
	 * @author ZhuFan
	 * @Date: 2015-6-1
	 * @param <T>
	 */
	public interface NListener<T> {
		/**
		 * 请求成功时调用此回调
		 * @author ZhuFan
		 * @Package com.bm.corelibs.logic
		 * @param data 解析后的数据
		 * @return void
		 * @throws 
		 * @Date 2015-6-1 下午3:39:04
		 */
		void onResponse(T data);
		/**
		 * 网络请求失败时调用此回调
		 * @author ZhuFan
		 * @Package com.bm.corelibs.logic
		 * @param error 
		 * @return void
		 * @throws 
		 * @Date 2015-6-1 下午3:39:26
		 */
		void onErrResponse(VolleyError error);
		/**
		 * 所有分页加载完毕时调用此回调
		 * @author ZhuFan
		 * @Package com.bm.corelibs.logic
		 * @return void
		 * @throws 
		 * @Date 2015-6-1 下午3:39:26
		 */
		void onAllPageLoaded(int nowPage, int totalPage);
	}
}

package com.corelibs.http;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import java.io.File;

/**
 * Manager for the queue
 * 
 * @author Trey Robinson
 * 
 */
public class RequestManager {

	public static final String TAG = "LTZSVolley";
	/**
	 * the queue :-)
	 */
	private static RequestQueue mRequestQueue;

	/**
	 * Nothing to see here.
	 */
	private RequestManager() {
		// no instances
	}

	/**
	 * @param context
	 *            application context
	 */
	public static void init(Context context, String cachePath) {

		// File sdDir = Environment.getExternalStorageDirectory();// 获取根目录
		// File file = new File(sdDir, "LTZS/Cache"); //
		// 此处一定不要与json缓存路径重名，否则json缓存会被覆盖
		File file = new File(Environment.getExternalStorageDirectory().getPath() + cachePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		DiskBasedCache cache = new DiskBasedCache(file, 10 * 1024 * 1024); //
		Network network = new BasicNetwork(new HurlStack()); //
		mRequestQueue = new RequestQueue(cache, network); //
		mRequestQueue.start();
	}

	/**
	 * Adds the specified request to the global queue, if tag is specified then
	 * it is used else Default TAG is used.
	 * 
	 * @param req
	 * @param tag
	 */
	public static <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

		getRequestQueue().add(req);
	}

	/**
	 * Adds the specified request to the global queue using the Default TAG.
	 * 
	 * @param req
	 * @param tag
	 */
	public static <T> void addToRequestQueue(Request<T> req) {
		// set the default tag if tag is empty
		req.setTag(TAG);

		getRequestQueue().add(req);
	}

	/**
	 * Cancels all pending requests by the specified TAG, it is important to
	 * specify a TAG so that the pending/ongoing requests can be cancelled.
	 * 
	 * @param tag
	 */
	public static void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

	/**
	 * @return instance of the queue
	 * @throws IllegalStatException
	 *             if init has not yet been called
	 */
	public static synchronized RequestQueue getRequestQueue() {
		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			throw new IllegalStateException("Not initialized");
		}
	}
}

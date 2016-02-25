/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title PicassoFactory.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.api
 * @Description TODO
 * @author zhaocl  
 * @date 2015年5月4日 上午11:50:27
 * @version V1.0  
 */
package com.ryan.corelibstest.tools.image.picasso;

import android.content.Context;

import com.ryan.corelibstest.App;
import com.ryan.corelibstest.constants.Constant;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;

/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title PicassoFactory.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.api
 * @Description 图片加载Picasso的工厂类
 * @author 赵成龙  
 * @date 2015年5月4日 上午11:50:27
 * @version V1.0  
 */
public class PicassoFactory {

	private static Picasso sPicasso;

	public static Picasso createPicasso(Context context) {
	    if (sPicasso == null) {
	    	File cacheDir = new File(App.getInstance().getDiskCacheDir() + Constant.IMAGE_CACHE_PATH);
	    	if (!cacheDir.exists()) {
	    		cacheDir.mkdirs();
	    	}
	    	
	        sPicasso = new Picasso.Builder(context)
	            .downloader(new OkHttpDownloader(cacheDir))
	            .memoryCache(new LruCache(Constant.MAX_MEMORY_CACHE_SIZE))
	            .build();
	        sPicasso.setIndicatorsEnabled(false);
	    }
	    
	    return sPicasso;
	  }
	
}

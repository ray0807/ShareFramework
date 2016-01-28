package com.corelibs.api;

import com.corelibs.common.Configuration;
import okhttp3.OkHttpClient;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * 用于获取配置好的retrofit对象, 通过设置{@link Configuration#enableLoggingNetworkParams()}来启用网络请求
 * 参数与相应结果.
 * <br/>
 * Created by Ryan on 2015/12/30.
 */
public class RetrofitFactory {
    private static Retrofit retrofit;
    private static String baseUrl;

    public static void setBaseUrl(String url) {
        baseUrl = url;
    }

    /**
     * 获取配置好的retrofit对象来生产Manager对象
     */
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            if (baseUrl == null || baseUrl.length() <= 0)
                throw new IllegalStateException("请在调用getFactory之前先调用setBaseUrl");

            Retrofit.Builder builder = new Retrofit.Builder();

            builder.baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

            if (Configuration.isShowNetworkParams()) {
//                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient client = new OkHttpClient.Builder().addInterceptor(
                        new HttpLoggingInterceptor()).build();

                builder.client(client);
            }

            retrofit = builder.build();
        }

        return retrofit;
    }
}

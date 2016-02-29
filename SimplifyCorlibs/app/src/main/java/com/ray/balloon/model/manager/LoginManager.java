package com.ray.balloon.model.manager;

import com.ray.balloon.constants.Urls;
import com.ray.balloon.model.bean.BaseData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2016/2/29.
 */
public interface LoginManager {
    @FormUrlEncoded
    @POST(Urls.LOGIN)
    Observable<BaseData> login(@Field("account") String username, @Field("password") String password);
}

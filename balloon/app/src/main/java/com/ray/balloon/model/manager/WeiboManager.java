package com.ray.balloon.model.manager;

import com.ray.balloon.constants.Urls;
import com.ray.balloon.model.bean.BaseData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2016/3/1.
 */
public interface WeiboManager {
    @FormUrlEncoded
    @POST(Urls.REGISTER)
    Observable<BaseData> getData(@Field("pageSize") String pageSize, @Field("pageNum") String pageNum);

}

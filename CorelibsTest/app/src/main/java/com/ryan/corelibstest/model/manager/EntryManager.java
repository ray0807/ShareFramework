package com.ryan.corelibstest.model.manager;

import com.ryan.corelibstest.constants.Urls;
import com.ryan.corelibstest.model.bean.BaseData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 登录注册等入口接口
 * <br/>
 * Created by Ryan on 2015/12/30.
 */
public interface EntryManager {

    @FormUrlEncoded
    @POST(Urls.LOGIN)
    Observable<BaseData> login(@Field("name") String username, @Field("password") String password);
}

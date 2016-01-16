package com.ryan.corelibstest.model.manager;

import com.ryan.corelibstest.constants.Urls;
import com.ryan.corelibstest.model.bean.BaseData;

import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by Ryan on 2016/1/6.
 */
public interface AdManager {

    @POST(Urls.GET_ADS)
    Observable<BaseData> getAds();
}

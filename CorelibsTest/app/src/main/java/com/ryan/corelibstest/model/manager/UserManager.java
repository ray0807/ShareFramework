package com.ryan.corelibstest.model.manager;

import com.corelibs.api.RequestBodyCreator;
import com.ryan.corelibstest.constants.Urls;
import com.ryan.corelibstest.model.bean.BaseData;
import okhttp3.RequestBody;

import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by Ryan on 2016/1/18.
 */
public interface UserManager {

    @Multipart
    @POST(Urls.IMAGE_UPLOAD)
    Observable<BaseData> uploadAvatar(@Part("id") RequestBody id,
                                      @Part("file" + RequestBodyCreator.MULTIPART_HACK) RequestBody avatar);
}

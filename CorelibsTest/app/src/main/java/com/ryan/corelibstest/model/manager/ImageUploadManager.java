package com.ryan.corelibstest.model.manager;

import com.corelibs.utils.uploader.ImageUploadHelper;
import com.corelibs.utils.uploader.ImageUploadRequest;
import com.ryan.corelibstest.constants.Urls;
import com.ryan.corelibstest.model.bean.BaseData;

import java.io.File;

import rx.Observable;

/**
 * 图片上传
 * Created by Ryan on 2016/1/20.
 */
public class ImageUploadManager {
    private ImageUploadHelper<BaseData> uploadHelper;

    public ImageUploadManager() {
        uploadHelper = new ImageUploadHelper<>();
    }

    public Observable<BaseData> uploadAvatar(long id, File avatar) {
        ImageUploadRequest.Builder<BaseData> builder = new ImageUploadRequest.Builder<>();
        ImageUploadRequest<BaseData> request = builder.addParam("id", id + "")
                .addFile("file", avatar).setUrl(Urls.ROOT + Urls.IMAGE_UPLOAD)
                .setOutputClass(BaseData.class).build();
        return uploadHelper.doPostWithObservable(request);
    }
}

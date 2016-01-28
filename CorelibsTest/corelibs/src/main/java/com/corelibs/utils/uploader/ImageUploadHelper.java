package com.corelibs.utils.uploader;

import com.google.gson.Gson;

import java.io.File;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * 通过{@link ImageUploader} 与 {@link ImageUploadRequest} 来上传图片.
 * Created by Ryan on 2016/1/20.
 */
public class ImageUploadHelper<T> {
    private ImageUploader uploader;
    private Gson gson;

    public ImageUploadHelper() {
        uploader = new ImageUploader();
        gson = new Gson();
    }

    private void doPost(String url, Map<String, String> params, Map<String, File> files,
                        String fileKey, ImageUploader.OnResponseListener listener) {
        uploader.post(url, params, files, fileKey, listener);
    }

    public void doPost(ImageUploadRequest<T> request) {
        doPost(request.getUrl(), request.getParams(), request.getFiles(),
                request.getFileKey(), request.getListener());
    }

    public Observable<T> doPostWithObservable(final ImageUploadRequest<T> request) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(final Subscriber<? super T> subscriber) {
                uploader.post(request.getUrl(), request.getParams(), request.getFiles(),
                        request.getFileKey(), new ImageUploader.OnResponseListener() {
                    @Override
                    public void onResponse(String data) {
                        subscriber.onNext(gson.fromJson(data, request.getOutputClass()));
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Exception e) {
                        subscriber.onError(e);
                    }
                });
            }
        });
    }

}

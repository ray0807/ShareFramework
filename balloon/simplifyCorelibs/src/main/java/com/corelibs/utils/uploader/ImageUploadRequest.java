package com.corelibs.utils.uploader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传请求封装类, 通过{@link com.corelibs.utils.uploader.ImageUploadRequest.Builder}
 * 组装请求.
 * <BR/>
 * Created by Ryan on 2016/1/20.
 */
public class ImageUploadRequest<T> {
    private String url;
    private Map<String, String> params;
    private Map<String, File> files;
    private String fileKey;
    private ImageUploader.OnResponseListener listener;
    private Class<T> outputClass;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, File> getFiles() {
        return files;
    }

    public void setFiles(Map<String, File> files) {
        this.files = files;
    }

    public ImageUploader.OnResponseListener getListener() {
        return listener;
    }

    public void setListener(ImageUploader.OnResponseListener listener) {
        this.listener = listener;
    }

    public Class<T> getOutputClass() {
        return outputClass;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public void setOutputClass(Class<T> outputClass) {
        this.outputClass = outputClass;
    }

    /**
     * 标准的Builder类
     */
    public static class Builder<T> {
        private ImageUploadRequest<T> request;

        public Builder() {
            request = new ImageUploadRequest<>();
        }

        public Builder<T> addParam(String key, String value) {
            if (request.getParams() == null) request.setParams(new HashMap<String, String>());
            request.getParams().put(key, value);
            return this;
        }

        public Builder<T> addFile(String key, File file) {
            if (request.getFiles() == null) request.setFiles(new HashMap<String, File>());
            request.getFiles().put(key, file);
            return this;
        }

        public Builder<T> setOutputClass(Class<T> clz) {
            request.setOutputClass(clz);
            return this;
        }

        public Builder<T> setUrl(String url) {
            request.setUrl(url);
            return this;
        }

        public Builder<T> setFileKey(String key) {
            request.setFileKey(key);
            return this;
        }

        public Builder<T> setListener(ImageUploader.OnResponseListener listener) {
            request.setListener(listener);
            return this;
        }

        public ImageUploadRequest<T> build() {
            return request;
        }
    }

}

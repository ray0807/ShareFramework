package com.corelibs.base;

/**
 * Created by Ryan on 2016/1/7.
 */
public interface BasePaginationView extends BaseView {
    void onLoadingCompleted(boolean reload);
    void onAllPageLoaded();
}

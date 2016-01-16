package com.corelibs.base;

import android.content.Context;

import com.trello.rxlifecycle.components.support.RxFragmentActivity;

/**
 * MVPView基础接口
 * <br/>
 * Created by Ryan on 2015/12/31.
 */
public interface BaseView {

    /**
     * 加载时显示加载框
     */
    void showLoadingDialog();
    /**
     * 加载完成时隐藏加载框
     */
    void hideLoadingDialog();
    /**
     * 显示提示消息
     */
    void showToastMessage(String message);

    Context getViewContext();

    RxFragmentActivity getRxContext();
}

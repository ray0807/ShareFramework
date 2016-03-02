package com.ray.balloon.subcriber;

import com.corelibs.base.BaseView;
import com.ray.balloon.R;
import com.ray.balloon.model.bean.BaseData;

import java.net.ConnectException;

import retrofit2.HttpException;
import rx.Subscriber;

/**
 * 请使用此类来subscribe Retrofit返回Observable.
 * <br/>
 * 此类会判断网络错误与业务错误, 并分发给{@link #error(Throwable)}
 * 与{@link #operationError(Object, int, String)}函数. 当请求成功同时业务成功的情况下会调用
 * {@link #success(Object)}函数. 如果在创建ResponseSubscriber对象的同时传入MVPView对象,
 * 此类会托管隐藏加载框与错误处理, 如果希望自行处理错误, 请覆写{@link #error(Throwable)}函数,
 * 并且取消super的调用.
 * <br/>
 * Created by Ryan on 2015/12/31.
 */
public class ResponseSubscriber<T> extends Subscriber<T> {

    public static final int SUCCESS_STATUS = 200;

    private BaseView view;

    public ResponseSubscriber() {
    }

    /**
     * 传入MVPView来托管隐藏加载框与错误处理
     */
    public ResponseSubscriber(BaseView view) {
        this.view = view;
    }

    @Override
    public void onCompleted() {
        view = null;
    }

    @Override
    public void onError(Throwable e) {
        operateView();
        e.printStackTrace();
        error(e);
        view = null;
    }

    @Override
    public void onNext(T t) {
        operateView();
        view = null;
        BaseData data;
        if (t instanceof BaseData) {
            data = (BaseData) t;
            if (data.ErrorCode == SUCCESS_STATUS) {
                success(t);
            } else {
                operationError(t, data.ErrorCode, data.Msg);
            }
        } else {
            success(t);
        }
    }

    /**
     * 请求成功同时业务成功的情况下会调用此函数
     */
    public void success(T t) {}

    /**
     * 请求成功但业务失败的情况下会调用此函数
     */
    public void operationError(T t, int status, String message) {
        if (view != null)
            view.showToastMessage(message);
    }

    /**
     * 请求失败的情况下会调用此函数
     */
    public void error(Throwable e) {
        handleException(e);
    }

    private void operateView() {
        if (view != null) {
            view.hideLoadingDialog();
        }
    }

    private void handleException(Throwable e) {
        if(view != null) {
            if (e instanceof ConnectException) {
                view.showToastMessage(view.getViewContext().getString(R.string.network_error));
            } else if (e instanceof HttpException) {
                view.showToastMessage(view.getViewContext().getString(R.string.network_server_error));
            } else {
                view.showToastMessage(view.getViewContext().getString(R.string.network_other));
            }
        }
    }
}

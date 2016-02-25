package com.corelibs.base;

import com.corelibs.api.ResponseTransformer;
import com.trello.rxlifecycle.ActivityEvent;

import rx.Observable;

/**
 * 参考{@link BasePresenter}, Activity需使用继承自此类的子类
 * <BR/>
 * Created by Ryan on 2016/1/7.
 */
public abstract class BaseRxPresenter<T extends BaseView> extends BasePresenter<T> {

    /**
     * 将网络请求的Observable绑定至Activity的生命周期, 以防内存溢出.
     * 请配合{@link ResponseTransformer}一起使用.
     */
    protected <V> Observable.Transformer<V, V> bindLifeCycle() {
        return getView().getRxContext().bindToLifecycle();
    }

    /**
     * 将网络请求的Observable绑定至Activity的生命周期, 以防内存溢出.
     * 请配合{@link ResponseTransformer}一起使用.
     *
     * @param event 指定生命周期
     */
    protected <V> Observable.Transformer<V, V> bindLifeCycle(ActivityEvent event) {
        return getView().getRxContext().bindUntilEvent(event);
    }
}

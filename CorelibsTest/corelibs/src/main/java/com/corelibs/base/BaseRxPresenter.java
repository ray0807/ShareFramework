package com.corelibs.base;

import com.corelibs.api.ResponseTransformer;
import com.trello.rxlifecycle.ActivityEvent;

import rx.Observable;

/**
 * Presenter基类, 泛型需传入继承自{@link BaseView}的MVPView.
 * <br/>
 * 子类可直接调用通过attachView传递过来的view来操作Activity, 无需再声明绑定.
 * <br/>
 * 如子类需要自行管理生命周期, 请在Activity的onCreate中调用{@link #attachView}方法,
 * 并一定要在onDestroy中调用{@link #detachView}, 以防内存溢出.
 * <br/>
 * Created by Ryan on 2015/12/29.
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

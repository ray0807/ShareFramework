package com.corelibs.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragmentActivity;

import butterknife.ButterKnife;

/**
 * Fragment基类, 继承自此类的Fragment需要实现{@link #getLayoutId}, {@link #init}
 * 以及{@link #createPresenter()}, 不需要覆写onCreate方法.
 * <br/>
 * 鉴于FragmentManager的attach与detach会销毁Fragment的视图, 此基类会在onCreate中生成一个
 * parentView, 缓存起来, 并在onCreateView中直接返回该View, 来达到保存Fragment视图状态的目的,
 * 同时避免不停的销毁与创建.
 * <br/>
 * 实现此类需遵循MVP设计, 第一个泛型V需传入一个继承自{@link BaseView}的MVPView,
 * 第二个泛型需传入继承自{@link BaseRxPresenter}的MVPPresenter.
 * <br/>
 * Presenter的生命周期已交由此类管理, 子类无需管理. 如果子类要使用多个Presenter, 则需要自行管理生命周期.
 * 此类已经实现了BaseView中的抽象方法, 子类无需再实现, 如需自定可覆写对应的方法.
 * <br/>
 * Created by Ryan on 2016/1/5.
 */
public abstract  class BaseFragment<V extends BaseView, T extends BasePresenter<V>>
        extends Fragment implements BaseView {

    private View parentView;
    protected T presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentView = getLayoutInflater(savedInstanceState).inflate(getLayoutId(), null, false);
        presenter = createPresenter();
        if (presenter != null) presenter.attachView((V) this);
        ButterKnife.bind(this, parentView);

        init(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return parentView;
    }

    @Override
    public void onDestroy() {
        presenter.detachView();
        presenter = null;
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    public T getPresenter() {
        return presenter;
    }

    public View getParentView() {
        return parentView;
    }

    /**
     * 指定Fragment需加载的布局ID
     *
     * @return 需加载的布局ID
     */
    protected abstract int getLayoutId();

    /**
     * 初始化方法, 类似OnCreate, 仅在此方法中做初始化操作, findView与事件绑定请使用ButterKnife
     */
    protected abstract void init(Bundle savedInstanceState);

    /**
     * 创建Presenter, 然后通过调用{@link #getPresenter()}来使用生成的Presenter
     * @return Presenter
     */
    protected abstract T createPresenter();

    @Override
    public void showLoadingDialog() {
        if(getActivity() instanceof BaseActivity) ((BaseActivity)getActivity()).showLoadingDialog();
    }

    @Override
    public void hideLoadingDialog() {
        if(getActivity() instanceof BaseActivity) ((BaseActivity)getActivity()).hideLoadingDialog();
    }

    @Override
    public void showToastMessage(String message) {
        if(getActivity() instanceof BaseActivity) ((BaseActivity)getActivity()).showToastMessage(message);
    }

    @Override
    public Context getViewContext() {
        return getActivity();
    }

    @Override
    public RxFragmentActivity getRxContext() {
        return getActivity() instanceof BaseActivity ? ((BaseActivity) getActivity()) : null;
    }
}

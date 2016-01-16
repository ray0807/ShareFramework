package com.ryan.corelibstest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.corelibs.base.BasePresenter;
import com.corelibs.base.BaseView;
import com.corelibs.views.LoadingDialog;
import com.trello.rxlifecycle.components.support.RxFragmentActivity;

import butterknife.ButterKnife;

/**
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

    protected abstract int getLayoutId();

    protected abstract void init(Bundle savedInstanceState);

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

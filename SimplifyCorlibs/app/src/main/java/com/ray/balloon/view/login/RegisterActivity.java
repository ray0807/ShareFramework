package com.ray.balloon.view.login;

import android.os.Bundle;

import com.corelibs.base.BaseActivity;
import com.corelibs.views.SplideBackLinearLayout;
import com.ray.balloon.R;
import com.ray.balloon.presenter.RegisterPresenter;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/3/1.
 */
public class RegisterActivity extends BaseActivity<RegisterView,RegisterPresenter> implements RegisterView{
    @Bind(R.id.spl_back)
    SplideBackLinearLayout spl_back;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        spl_back.setBackListener(this);
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return null;
    }

    @Override
    public void registerSuccess() {
        showToast("注册成功");

    }
}

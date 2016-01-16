package com.ryan.corelibstest.view.entry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.ryan.corelibstest.BaseActivity;
import com.ryan.corelibstest.R;
import com.ryan.corelibstest.authority.AuthorityContext;
import com.ryan.corelibstest.presenter.LoginPresenter;
import com.ryan.corelibstest.widget.NavigationBar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 登录界面
 * Created by Ryan on 2015/12/28.
 */
public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView {

    @Bind(R.id.nav) NavigationBar nav;
    @Bind(R.id.et_username) EditText etUsername;
    @Bind(R.id.et_password) EditText etPassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setTitle(R.string.login_hint6);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    /**
     * 登录
     */
    @OnClick(R.id.btn_login)
    protected void login() {
        getPresenter().login(getText(etUsername), getText(etPassword));
    }

    /**
     * 注册跳转
     */
    @OnClick(R.id.tv_register)
    protected void register() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    /**
     * 忘记密码跳转
     */
    @OnClick(R.id.tv_forget)
    protected void forgetPassword() {

    }

    @Override
    public void loginSuccess() {
        showToastMessage(getString(R.string.login_success));
        finish();
    }
}

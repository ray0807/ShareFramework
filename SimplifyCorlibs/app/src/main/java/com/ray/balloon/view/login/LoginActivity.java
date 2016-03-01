package com.ray.balloon.view.login;

import android.content.Intent;
import android.os.Bundle;

import com.corelibs.base.BaseActivity;
import com.ray.balloon.R;
import com.ray.balloon.presenter.LoginPresenter;
import com.ray.balloon.widget.NavigationBar;

import butterknife.Bind;
import butterknife.OnClick;
import carbon.widget.Button;
import carbon.widget.CheckBox;
import carbon.widget.EditText;

/**
 * Created by Administrator on 2016/2/29.
 */
public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView {
    @Bind(R.id.navi)
    NavigationBar bar;
    @Bind(R.id.et_phone)
    EditText et_phone;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.remember_enabled)
    CheckBox remember_enabled;
    @Bind(R.id.btn_login)
    Button btn_login;
    @Bind(R.id.btn_reg)
    Button btn_reg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        bar.setTitle(R.string.login_title);

    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @OnClick(R.id.btn_login)
    protected void login() {

        getPresenter().login(et_phone.getText().toString(), et_password.getText().toString());
    }

    @OnClick(R.id.btn_reg)
    protected void register() {
        Intent it =new Intent(this,RegisterActivity.class);
        startActivity(it);
    }


    @Override
    public void loginSuccess() {

    }
}

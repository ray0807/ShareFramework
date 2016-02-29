package com.ray.balloon.view.login;

import android.os.Bundle;
import android.util.Log;

import com.corelibs.base.BaseActivity;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.ray.balloon.R;
import com.ray.balloon.model.bean.User;
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
        Log.i("wanglei", "success: " + PreferencesHelper.getData(User.class));
        ToastMgr.show("注册");
    }


    @Override
    public void loginSuccess() {

    }
}

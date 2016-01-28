package com.ryan.corelibstest.view.entry;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.corelibs.base.BaseActivity;
import com.ryan.corelibstest.R;
import com.ryan.corelibstest.presenter.LoginPresenter;
import com.ryan.corelibstest.view.main.MainActivity;
import com.ryan.corelibstest.widget.NavigationBar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 登录界面
 * Created by Ryan on 2015/12/28.
 */
public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView {

    private static final String EXTRA_IS_TO_MAIN = "EXTRA_IS_TO_MAIN";

    @Bind(R.id.nav) NavigationBar nav;
    @Bind(R.id.et_username) EditText etUsername;
    @Bind(R.id.et_password) EditText etPassword;

    private boolean isToMain;

    public static Intent getLaunchIntent(Context context, boolean isToMain) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(EXTRA_IS_TO_MAIN, isToMain);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        isToMain = getIntent().getBooleanExtra(EXTRA_IS_TO_MAIN, false);
        nav.setTitle(R.string.login_hint6);
        nav.setBackAction(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onActivityFinish();
            }
        });
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

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onActivityFinish();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    private void onActivityFinish() {
        if(isToMain) startActivity(MainActivity.getLaunchIntent(this));
        finish();
    }
}

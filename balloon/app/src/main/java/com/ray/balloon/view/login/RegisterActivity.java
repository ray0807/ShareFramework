package com.ray.balloon.view.login;

import android.os.Bundle;
import android.view.View;

import com.corelibs.base.BaseActivity;
import com.corelibs.views.SplideBackLinearLayout;
import com.ray.balloon.R;
import com.ray.balloon.presenter.RegisterPresenter;

import butterknife.Bind;
import butterknife.OnClick;
import carbon.widget.Button;
import carbon.widget.EditText;
import carbon.widget.Toolbar;

/**
 * Created by Administrator on 2016/3/1.
 */
public class RegisterActivity extends BaseActivity<RegisterView, RegisterPresenter> implements RegisterView, View.OnClickListener {
    @Bind(R.id.spl_back)
    SplideBackLinearLayout spl_back;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_phone)
    EditText et_phone;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.et_password1)
    EditText et_password1;
    @Bind(R.id.btn_reg)
    Button btn_reg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        spl_back.setBackListener(this);
        toolbar.setText(R.string.registerTitle);
        toolbar.setBackgroundColor(getResources().getColor(R.color.main));
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    //注册以后的回调接口
    @Override
    public void registerSuccess() {
        finish();
    }

    @OnClick(R.id.btn_reg)
    protected void register() {

        getPresenter().registerSuccess(et_phone.getText().toString(),et_password.getText().toString(),et_password1.getText().toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back_operate:
                finish();
                break;
        }
    }
}

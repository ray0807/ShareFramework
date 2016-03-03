package com.ray.balloon.view.bluetooth;

import android.os.Bundle;

import com.corelibs.base.BaseActivity;
import com.corelibs.views.SplideBackLinearLayout;
import com.ray.balloon.R;
import com.ray.balloon.presenter.BluetoothPresenter;

import butterknife.Bind;
import carbon.widget.Toolbar;

/**
 * Created by Administrator on 2016/3/3.
 */
public class BluetoothActivity  extends BaseActivity<BluetoothView,BluetoothPresenter> {
    @Bind(R.id.spl_back)
    SplideBackLinearLayout spl_back;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_bluetooth;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        spl_back.setBackListener(this);
        toolbar.setText(R.string.bluetooth_title);

        toolbar.setBackgroundColor(getResources().getColor(R.color.main));
        toolbar.setIcon(R.drawable.img_back);
    }

    @Override
    protected BluetoothPresenter createPresenter() {
        return new BluetoothPresenter();
    }

}

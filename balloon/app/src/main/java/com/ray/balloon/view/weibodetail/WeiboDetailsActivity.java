package com.ray.balloon.view.weibodetail;

import android.os.Bundle;

import com.corelibs.base.BaseActivity;
import com.ray.balloon.R;
import com.ray.balloon.presenter.WeiboDetailsPresenter;

/**
 * Created by Ray on 2016/3/17.
 * https://github.com/ray0807
 */
public class WeiboDetailsActivity extends BaseActivity<DetailsView, WeiboDetailsPresenter> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_weibo_details;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected WeiboDetailsPresenter createPresenter() {
        return new WeiboDetailsPresenter();
    }
}

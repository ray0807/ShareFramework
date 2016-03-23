package com.ray.balloon.view.weibodetail;

import android.os.Bundle;
import android.view.View;

import com.corelibs.base.BaseActivity;
import com.corelibs.views.ptr.PtrLoadMoreListView;
import com.ray.balloon.R;
import com.ray.balloon.adapter.WeiboDetailsCommentAdapter;
import com.ray.balloon.presenter.WeiboDetailsPresenter;

import butterknife.Bind;
import carbon.widget.Toolbar;

/**
 * Created by Ray on 2016/3/17.
 * https://github.com/ray0807
 */
public class WeiboDetailsActivity extends BaseActivity<DetailsView, WeiboDetailsPresenter> implements View.OnClickListener {
    private PtrLoadMoreListView ptr_list;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weibo_details;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        toolbar.setText(R.string.main_details_title);
        toolbar.setBackgroundColor(getResources().getColor(R.color.main));
        toolbar.setIcon(R.drawable.img_back);
        ptr_list = (PtrLoadMoreListView) findViewById(R.id.ptr_list);
        ptr_list.getPtrView().setAdapter(new WeiboDetailsCommentAdapter(this));
        ptr_list.enableLoading();
    }

    @Override
    protected WeiboDetailsPresenter createPresenter() {
        return new WeiboDetailsPresenter();
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

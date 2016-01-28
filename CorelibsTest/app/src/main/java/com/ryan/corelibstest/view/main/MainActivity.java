package com.ryan.corelibstest.view.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.corelibs.base.BaseView;
import com.corelibs.base.BaseActivity;
import com.ryan.corelibstest.R;
import com.ryan.corelibstest.presenter.MainPresenter;
import com.ryan.corelibstest.view.about.AboutFragment;
import com.ryan.corelibstest.view.cart.CartFragment;
import com.ryan.corelibstest.view.mine.MineFragment;
import com.ryan.corelibstest.widget.tab.InterceptedFragmentTabHost;
import com.ryan.corelibstest.widget.tab.TabChangeInterceptor;
import com.ryan.corelibstest.widget.tab.TabNavigator;

import butterknife.Bind;

/**
 * Created by Ryan on 2016/1/4.
 */
public class MainActivity extends BaseActivity<BaseView, MainPresenter> implements
        TabNavigator.TabNavigatorContent {

    @Bind(android.R.id.tabhost)
    InterceptedFragmentTabHost tabHost;

    private TabNavigator navigator = new TabNavigator();
    private String[] tabTags;
    private int[] imageResIds = new int[]{R.drawable.tab_home, R.drawable.tab_about,
            R.drawable.tab_cart, R.drawable.tab_mine};

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tabTags = new String[]{getString(R.string.home), getString(R.string.about),
                getString(R.string.cart), getString(R.string.mine)};
        navigator.setup(this, tabHost, this, getSupportFragmentManager(), R.id.realtabcontent);
        navigator.setTabChangeInterceptor(new TabChangeInterceptor() {
            @Override
            public boolean canTab(String tabId) {
                return presenter.isAuthorized(tabId);
            }

            @Override
            public void onTabIntercepted(String tabId) {
            }
        });
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public View getTabView(int position) {
        View view = getLayoutInflater().inflate(R.layout.item_tab, null);
        view.setBackgroundResource(R.drawable.tab_background_color);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_tab);
        TextView tv = (TextView) view.findViewById(R.id.tv_tab);
        iv.setImageResource(imageResIds[position]);
        tv.setText(tabTags[position]);
        return view;
    }

    @Override
    public Bundle getArgs(int position) {
        return null;
    }

    @Override
    public Class[] getFragmentClasses() {
        return new Class[]{HomeFragment.class, AboutFragment.class,
                CartFragment.class, MineFragment.class};
    }

    @Override
    public String[] getTabTags() {
        return tabTags;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (tabHost.getCurrentTabTag().equals(tabTags[3])) {
            MineFragment fragment = (MineFragment) getSupportFragmentManager().findFragmentByTag(tabTags[3]);
            if (fragment != null) fragment.onActivityResult(requestCode, resultCode, data);
        }

    }
}

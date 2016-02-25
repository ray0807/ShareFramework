package com.ryan.corelibstest.view.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.corelibs.base.BaseFragment;
import com.corelibs.views.cube.ptr.PtrFrameLayout;
import com.corelibs.views.ptr.PtrLoadMoreListView;
import com.corelibs.views.ptr.base.PtrLollipopBaseView;
import com.ryan.corelibstest.R;
import com.ryan.corelibstest.adapter.HomeQuickAdapter;
import com.ryan.corelibstest.model.bean.Brand;
import com.ryan.corelibstest.model.bean.Category;
import com.ryan.corelibstest.model.bean.Product;
import com.ryan.corelibstest.presenter.HomePresenter;
import com.ryan.corelibstest.view.fresco.FrescoActivity;
import com.ryan.corelibstest.widget.NavigationBar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ryan on 2016/1/5.
 */
public class HomeFragment extends BaseFragment<HomeView, HomePresenter>
        implements HomeView, PtrLollipopBaseView.RefreshLoadHandler {

    @Bind(R.id.nav) NavigationBar nav;
    @Bind(R.id.lv_home) PtrLoadMoreListView lvHome;

    private HeaderViewHolder headerViews;
    private HomeQuickAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setTitle(R.string.app_name);
        nav.hideBackButton();
        initHeader(savedInstanceState);
        getHomeData();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvHome.refreshComplete();
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @SuppressLint("InflateParams")
    private void initHeader(Bundle savedInstanceState) {
        View header = getLayoutInflater(savedInstanceState).inflate(R.layout.layout_header_home, null);
        headerViews = new HeaderViewHolder(header);
        intiListView(header);
    }

    private void intiListView(View header) {
        lvHome.getPtrFrameLayout().disableWhenHorizontalMove(true);
        lvHome.getPtrView().addHeaderView(header);
        adapter = new HomeQuickAdapter(getActivity(), R.layout.item_home);
        lvHome.getPtrView().setAdapter(adapter);
        lvHome.setRefreshLoadHandler(this);
    }

    private void getHomeData() {
        presenter.getTypes();
        presenter.getTopProducts(true);
    }

    @Override
    public void renderTypes(List<Category> hotCategories, List<Category> categories, List<Brand> brands) {
        setCategories(hotCategories);
    }

    @Override
    public void renderProducts(boolean reload, List<Product> products) {
        if (reload) {
            adapter.replaceAll(products);
        } else {
            adapter.addAll(products);
        }

    }

    @Override
    public void onLoadingCompleted(boolean reload) {
        lvHome.refreshComplete();
    }

    @Override
    public void onAllPageLoaded() {
        lvHome.disableLoading();
    }

    private void setCategories(List<Category> hotCategories) {
        for (int i = 0; i < hotCategories.size(); i++) {
            headerViews.cateTextViews.get(i).setText(hotCategories.get(i).name.substring(0, 1));
            headerViews.cateNameTextViews.get(i).setText(hotCategories.get(i).name);
        }
    }

    @Override
    public void onRefreshing(PtrFrameLayout frame) {
        lvHome.enableLoading();
        presenter.getTopProducts(true);
    }

    @Override
    public void onLoading(PtrFrameLayout frame) {
        presenter.getTopProducts(false);
    }

    public void onClick(View view) {

        showToastMessage("clicked");
        Intent it =new Intent(getActivity(), FrescoActivity.class);
        startActivity(it);
    }

    @Override
    public void onDestroy() {
        headerViews.unBind();
        super.onDestroy();
    }

    class HeaderViewHolder {
        @Bind({R.id.tv_nav_sum1, R.id.tv_nav_sum2, R.id.tv_nav_sum3, R.id.tv_nav_sum4})
        List<TextView> cateTextViews;
        @Bind({R.id.tv_nav1, R.id.tv_nav2, R.id.tv_nav3, R.id.tv_nav4})
        List<TextView> cateNameTextViews;

        public HeaderViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void unBind() {
            ButterKnife.unbind(this);
        }

        @OnClick({R.id.ll_nav_sum1, R.id.ll_nav_sum2, R.id.ll_nav_sum3, R.id.ll_nav_sum4})
        public void onCategoryClicked(View view) {
            HomeFragment.this.onClick(view);
        }
    }
}

package com.ryan.corelibstest.view.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.corelibs.views.cube.ptr.PtrFrameLayout;
import com.corelibs.views.ptr.PtrListView;
import com.corelibs.views.ptr.PtrLollipopBaseView;
import com.ryan.corelibstest.BaseFragment;
import com.ryan.corelibstest.R;
import com.ryan.corelibstest.adapter.HomeAdapter;
import com.ryan.corelibstest.model.bean.Brand;
import com.ryan.corelibstest.model.bean.Category;
import com.ryan.corelibstest.model.bean.Product;
import com.ryan.corelibstest.presenter.HomePresenter;
import com.ryan.corelibstest.widget.NavigationBar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ryan on 2016/1/5.
 */
public class HomeFragment extends BaseFragment<HomeView, HomePresenter>
        implements HomeView, PtrLollipopBaseView.RALHandler {

    @Bind(R.id.nav) NavigationBar nav;
    @Bind(R.id.lv_home) PtrListView lvHome;

    private HeaderViewHolder headerViews;
    private HomeAdapter adapter;
    private List<Product> products;

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
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @SuppressLint("InflateParams")
    private void initHeader(Bundle savedInstanceState) {
        View header = getLayoutInflater(savedInstanceState).inflate(R.layout.layout_header_home, null);
        headerViews = new HeaderViewHolder(header, new View.OnClickListener() {
            @Override public void onClick(View v) {
                showToastMessage("clicked");
            }
        });
        intiListView(header);
    }

    private void intiListView(View header) {
        lvHome.getPtrFrameLayout().disableWhenHorizontalMove(true);
        lvHome.getPtrView().addHeaderView(header);
        adapter = new HomeAdapter(getActivity());
        lvHome.getPtrView().setAdapter(adapter);
        lvHome.setRALHandler(this);
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
        if(reload) {
            this.products = products;
            adapter.setProducts(this.products);
        } else {
            this.products.addAll(products);
            adapter.notifyDataSetChanged();
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

    static class HeaderViewHolder {
        @Bind({R.id.tv_nav_sum1, R.id.tv_nav_sum2, R.id.tv_nav_sum3, R.id.tv_nav_sum4})
        List<TextView> cateTextViews;
        @Bind({R.id.tv_nav1, R.id.tv_nav2, R.id.tv_nav3, R.id.tv_nav4})
        List<TextView> cateNameTextViews;

        private View.OnClickListener listener;

        public HeaderViewHolder(View view, @NonNull View.OnClickListener listener) {
            ButterKnife.bind(this, view);
            this.listener = listener;
        }

        @OnClick({R.id.ll_nav_sum1, R.id.ll_nav_sum2, R.id.ll_nav_sum3, R.id.ll_nav_sum4})
        public void onCategoryClicked(View view) {
            listener.onClick(view);
        }
    }
}

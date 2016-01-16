package com.ryan.corelibstest.view.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.corelibs.utils.DisplayUtil;
import com.ryan.corelibstest.BaseFragment;
import com.ryan.corelibstest.R;
import com.ryan.corelibstest.model.bean.RecommendAd;
import com.ryan.corelibstest.presenter.AdPresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 轮播广告碎片
 */
public class RecommendAdFragment extends BaseFragment<AdView, AdPresenter>
        implements OnClickListener, AdView {

    /**
     * 广告轮播速度
     */
    public static final int SCROLL_SPEED = 4000;

    @Bind(R.id.vp_ads) ViewPager vp_ads;
    @Bind(R.id.ll_dots) LinearLayout ll_dots;

    /**
     * 存储广告信息
     */
    private List<RecommendAd> ads;
    /**
     * 存储广告图片
     */
    private List<ImageView> images;
    /**
     * 存储标识广告的圆点
     */
    private List<View> dots;
    /**
     * 广告数量
     */
    private int count = 0;
    /**
     * 用于自动轮播
     */
    private Handler handler;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommend_ad;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        handler = new Handler();
        getPresenter().getAds();
    }

    @Override
    protected AdPresenter createPresenter() {
        return new AdPresenter();
    }

    @Override
    public void renderAds(List<RecommendAd> ads) {
        this.ads = ads;
        initViews();
        initViewPager();
    }

    /**
     * 根据广告数量初始化图片以及小圆点
     */
    private void initViews() {
        count = ads.size();

        images = new ArrayList<>();
        dots = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            View view = buildChildView();
            ll_dots.addView(view);
            dots.add(view);
        }

        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setTag(ads.get(i));
            imageView.setOnClickListener(this);
            Picasso.with(getActivity()).load(ads.get(i).imgsrc).into(imageView);
            imageView.setScaleType(ScaleType.CENTER_CROP);
            images.add(imageView);
        }
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        vp_ads.setAdapter(new RecommendAdAdapter());
        vp_ads.setOnPageChangeListener(new RecommendAdPageChangedListener());
        vp_ads.setCurrentItem(0);
        dots.get(0).setBackgroundResource(R.drawable.dot_focused);
        handler.postDelayed(runnable, SCROLL_SPEED);
    }

    /**
     * 生成小圆点
     */
    private View buildChildView() {
        View view = new View(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                DisplayUtil.dip2px(getActivity(), 12), DisplayUtil.dip2px(getActivity(), 3));
        view.setBackgroundResource(R.drawable.dot_normal);
        lp.setMargins(DisplayUtil.dip2px(
                getActivity(), 1), 0, DisplayUtil.dip2px(getActivity(), 1), 0);
        view.setLayoutParams(lp);

        return view;
    }

    class RecommendAdPageChangedListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {

            handler.removeCallbacks(runnable);

            int position = arg0 % count;
            for (int i = 0; i < count; i++) {
                if (i == position)
                    dots.get(i).setBackgroundResource(R.drawable.dot_focused);
                else
                    dots.get(i).setBackgroundResource(R.drawable.dot_normal);
            }

            handler.postDelayed(runnable, SCROLL_SPEED);
        }

    }

    /**
     * 用于自动轮播的线程体
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int current = vp_ads.getCurrentItem();
            vp_ads.setCurrentItem(current + 1);
        }
    };

    /**
     * 用于存储当页面被添加或删除时的position 当页面个数为3的时候, 无限轮播会出现bug,
     * 必须在destroyItem中判断 当Math.abs(add - remove) == 0的时候不删除页面, 不然会出现空页面的情况.
     */
    private int add, remove = 0;

    class RecommendAdAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return count < 3 ? count : Integer.MAX_VALUE;
        }

        @Override
        public Object instantiateItem(View container, int position) {
            int i = position % count;
            add = i;
            if (images.get(i).getParent() != null) {
                ((ViewPager) images.get(i).getParent()).removeView(images.get(i));
            }
            ((ViewPager) container).addView(images.get(i));
            return images.get(i);
        }

        @Override
        public void destroyItem(View arg0, int position, Object arg2) {
            int i = position % count;
            remove = i;
            if (count == 3 && Math.abs(add - remove) == 0)
                return;
            ((ViewPager) arg0).removeView(images.get(i));
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }

    /**
     * 页面暂停时取消轮播
     */
    @Override
    public void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    /**
     * 页面恢复时注册轮播
     */
    @Override
    public void onResume() {
        handler.postDelayed(runnable, SCROLL_SPEED);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        RecommendAd ad = (RecommendAd) v.getTag();
        getPresenter().toProductDetail(ad);
    }

}

package com.ray.balloon.view.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BaseRxPresenter;
import com.corelibs.utils.DisplayUtil;
import com.corelibs.utils.ViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ray.balloon.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yiw on 2016/1/6.
 */
public class ImagePagerActivity extends BaseActivity {
    public static final String INTENT_IMGURLS = "imgurls";
    public static final String INTENT_POSITION = "position";
    private List<View> guideViewList = new ArrayList<View>();
    private LinearLayout guideGroup;


    private static ImagePagerActivity activity;



    public static void startImagePagerActivity(Context context, List<String> imgUrls, int position) {
        Intent intent = new Intent(context, ImagePagerActivity.class);
        intent.putStringArrayListExtra(INTENT_IMGURLS, new ArrayList<String>(imgUrls));
        intent.putExtra(INTENT_POSITION, position);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_imagepager;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        activity = this;
        guideGroup = (LinearLayout) findViewById(R.id.guideGroup);

        int startPos = getIntent().getIntExtra(INTENT_POSITION, 0);
        ArrayList<String> imgUrls = getIntent().getStringArrayListExtra(INTENT_IMGURLS);

        ImageAdapter mAdapter = new ImageAdapter(this);
        mAdapter.setDatas(imgUrls);
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < guideViewList.size(); i++) {
                    guideViewList.get(i).setSelected(i == position ? true : false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(startPos);

        addGuideView(guideGroup, startPos, imgUrls);
    }

    @Override
    protected BaseRxPresenter createPresenter() {
        return null;
    }

    private void addGuideView(LinearLayout guideGroup, int startPos, ArrayList<String> imgUrls) {
        if (imgUrls != null && imgUrls.size() > 0) {
            guideViewList.clear();
            for (int i = 0; i < imgUrls.size(); i++) {
                View view = new View(this);
                view.setBackgroundResource(R.drawable.selector_guide_bg);
                view.setSelected(i == startPos ? true : false);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.gudieview_width),
                        getResources().getDimensionPixelSize(R.dimen.gudieview_heigh));
                layoutParams.setMargins(10, 0, 0, 0);
                guideGroup.addView(view, layoutParams);
                guideViewList.add(view);
            }
        }
    }

    private static class ImageAdapter extends PagerAdapter {

        private List<String> datas = new ArrayList<String>();
        private LayoutInflater inflater;
        private Context context;

        public void setDatas(List<String> datas) {
            if (datas != null)
                this.datas = datas;
        }

        public ImageAdapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if (datas == null) return 0;
            return datas.size();
        }


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            View view = inflater.inflate(R.layout.item_pager_image, container, false);
            if (view != null) {
                SimpleDraweeView item_show_images = ViewHolder.get(view, R.id.item_show_images);
                item_show_images.getLayoutParams().width = DisplayUtil.getScreenWidth(context);
                final String imgurl = datas.get(position);
                item_show_images.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.finish();
                    }
                });
                item_show_images.setImageURI(Uri.parse(imgurl));

                container.addView(view, 0);
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }


    }
}

package com.ray.balloon.view.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BaseRxPresenter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.ray.balloon.R;
import com.ray.balloon.widget.phoneview.MultiTouchViewPager;
import com.ray.balloon.widget.phoneview.phonedraweeview.OnPhotoTapListener;
import com.ray.balloon.widget.phoneview.phonedraweeview.PhotoDraweeView;

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


        activity = this;
        guideGroup = (LinearLayout) findViewById(R.id.guideGroup);

        int startPos = getIntent().getIntExtra(INTENT_POSITION, 0);
        ArrayList<String> imgUrls = getIntent().getStringArrayListExtra(INTENT_IMGURLS);

        MultiTouchViewPager viewPager = (MultiTouchViewPager) findViewById(R.id.pager);
        DraweePagerAdapter adapter = new DraweePagerAdapter();
        adapter.setDatas(imgUrls);
        viewPager.setAdapter(adapter);

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


    public class DraweePagerAdapter extends PagerAdapter {
        private List<String> datas = new ArrayList<String>();

        public void setDatas(List<String> datas) {
            if (datas != null)
                this.datas = datas;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int position) {
            final PhotoDraweeView photoDraweeView = new PhotoDraweeView(viewGroup.getContext());
            PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
            controller.setUri(Uri.parse(datas.get(position)));
            controller.setOldController(photoDraweeView.getController());
            controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                    super.onFinalImageSet(id, imageInfo, animatable);
                    if (imageInfo == null) {
                        return;
                    }
                    photoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
                }
            });
            photoDraweeView.setController(controller.build());

            try {
                viewGroup.addView(photoDraweeView, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
            photoDraweeView.setOnPhotoTapListener(new OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    activity.finish();
                }
            });

            return photoDraweeView;
        }
    }
}

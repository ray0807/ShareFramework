package com.ray.balloon.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.corelibs.utils.DisplayUtil;
import com.corelibs.utils.ViewHolder;
import com.corelibs.views.NoScrollingGridView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ray.balloon.R;
import com.ray.balloon.tools.MTextViewUtils;
import com.ray.balloon.view.main.ImagePagerActivity;
import com.ray.balloon.widget.MTextView;

import carbon.drawable.ripple.RippleDrawable;
import carbon.drawable.ripple.RippleDrawableFroyo;
import carbon.widget.CardView;

/**
 * Created by Ray on 2016/3/14.
 * https://github.com/ray0807
 */
public class HomePageMainAdapter extends BaseAdapter {
    private Context context;

    public HomePageMainAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 50;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_home_page, null);
        }
        CardView card = ViewHolder.get(view, R.id.card_main);

        card.setClickable(true);
        card.setBackgroundColor(Color.WHITE);

        RippleDrawable rippleDrawable = new RippleDrawableFroyo(ColorStateList.valueOf(0x42ff0000), null, RippleDrawable.Style.Over);
        rippleDrawable.setCallback(card);
        rippleDrawable.setHotspotEnabled(true);
        card.setRippleDrawable(rippleDrawable);

        SimpleDraweeView item_preson_icon = ViewHolder.get(view, R.id.item_preson_icon);
        item_preson_icon.setImageURI(Uri.parse("http://imgsrc.baidu.com/forum/pic/item/3b292df5e0fe992503d986c834a85edf8cb17155.jpg"));

        MTextView mtv_content = ViewHolder.get(view, R.id.mtv_content);
        MTextViewUtils.setText(mtv_content, "默认使用一个 shader 绘制圆角，但是仅仅占位图和所要显示的图有圆角效果。失败示意图和重下载示意图无圆角效果，且这种圆角方式不支持动画", context);

        NoScrollingGridView gv_images = ViewHolder.get(view, R.id.gv_images);
        HomePageImagesAdapter childAdapter = new HomePageImagesAdapter(context);
        gv_images.setAdapter(childAdapter);
        gv_images.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 因为单张图片时，图片实际大小是自适应的，imageLoader缓存时是按测量尺寸缓存的
                DisplayUtil.getNetImageWidthBIHeight();
                HomePageImagesAdapter adapter= (HomePageImagesAdapter) parent.getAdapter();

                ImagePagerActivity.startImagePagerActivity(context, adapter.getPhones(), position);
            }
        });

        return view;
    }
}

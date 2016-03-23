package com.ray.balloon.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.corelibs.utils.DisplayUtil;
import com.corelibs.utils.ToastMgr;
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
        Holder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_home_page, null);
            holder = new Holder();
            ViewStub linkOrImgViewStub = (ViewStub) view.findViewById(R.id.linkOrImgViewStub);
            switch (position % 2) {
                case 0:// 图片
                    linkOrImgViewStub.setLayoutResource(R.layout.viewstub_image);
                    linkOrImgViewStub.inflate();
                    LinearLayout ll_image_view = (LinearLayout) view.findViewById(R.id.ll_image_view);
                    if (ll_image_view != null) {
                        holder.ll_image_view = ll_image_view;
                        holder.gv_images = (NoScrollingGridView) ll_image_view.findViewById(R.id.gv_images);
                    }
                    break;
                case 1:
                    linkOrImgViewStub.setLayoutResource(R.layout.viewstub_video);
                    linkOrImgViewStub.inflate();
                    LinearLayout ll_video = (LinearLayout) view.findViewById(R.id.ll_video);
                    if (ll_video != null) {
                        holder.ll_video = ll_video;
                        holder.item_video_shoot = (SimpleDraweeView) ll_video.findViewById(R.id.item_video_shoot);
                    }
                    break;
                default:
                    break;
            }
            holder.card = (CardView) view.findViewById(R.id.card_main);
            holder.item_preson_icon = (SimpleDraweeView) view.findViewById(R.id.item_preson_icon);
            holder.mtv_content = (MTextView) view.findViewById(R.id.mtv_content);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        holder.card.setClickable(true);
        holder.card.setBackgroundColor(Color.WHITE);

        RippleDrawable rippleDrawable = new RippleDrawableFroyo(ColorStateList.valueOf(0x42ff0000), null, RippleDrawable.Style.Over);
        rippleDrawable.setCallback(holder.card);
        rippleDrawable.setHotspotEnabled(true);
        holder.card.setRippleDrawable(rippleDrawable);


        holder.item_preson_icon.setImageURI(Uri.parse("http://imgsrc.baidu.com/forum/pic/item/3b292df5e0fe992503d986c834a85edf8cb17155.jpg"));

        MTextViewUtils.setText(holder.mtv_content, "默认使用一个 shader 绘制圆角，但是仅仅占位图和所要显示的图有圆角效果。失败示意图和重下载示意图无圆角效果，且这种圆角方式不支持动画", context);


        switch (position % 2) {
            case 0:
                if (holder.ll_image_view != null) {
                    holder.ll_image_view.setVisibility(View.VISIBLE);
                    HomePageImagesAdapter childAdapter = new HomePageImagesAdapter(context);
                    holder.gv_images.setAdapter(childAdapter);
                    holder.gv_images.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // 因为单张图片时，图片实际大小是自适应的，imageLoader缓存时是按测量尺寸缓存的
                            DisplayUtil.getNetImageWidthBIHeight();
                            HomePageImagesAdapter adapter = (HomePageImagesAdapter) parent.getAdapter();
                            ImagePagerActivity.startImagePagerActivity(context, adapter.getPhones(), position);
                        }
                    });
                }
                break;
            case 1:
                if (holder.ll_video != null) {
                    holder.ll_video.setVisibility(View.VISIBLE);
                    holder.item_video_shoot.setImageURI(Uri.parse("http://www.lizhi77.com/uploads/allimg/140930/2-14093010032N14.jpg"));
                    holder.ll_video.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToastMgr.show("播放视频");
                        }
                    });
                }
                break;
            default:
                break;
        }


        return view;
    }


    class Holder {
        SimpleDraweeView item_preson_icon;
        SimpleDraweeView item_video_shoot;
        CardView card;
        MTextView mtv_content;
        LinearLayout ll_image_view;
        LinearLayout ll_video;
        NoScrollingGridView gv_images;
    }

}

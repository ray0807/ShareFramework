package com.ray.balloon.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.corelibs.utils.DisplayUtil;
import com.corelibs.utils.ViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ray.balloon.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ray on 2016/3/14.
 * https://github.com/ray0807
 * 类似微博图片显示adapter
 */
public class HomePageImagesAdapter extends BaseAdapter {
    private Context context;
    private int screenWidth = 0;
    final List<String> photos = new ArrayList<>();


    public HomePageImagesAdapter(Context context) {
        this.context = context;
        screenWidth = DisplayUtil.getScreenWidth(context);
        photos.add("http://h.hiphotos.baidu.com/lvpics/w=1000/sign=049d1d655cafa40f3cc6cadd9b54024f/29381f30e924b899de6cd36f68061d950a7bf611.jpg");
        photos.add("http://imgsrc.baidu.com/forum/pic/item/3b292df5e0fe992503d986c834a85edf8cb17155.jpg");
        photos.add("http://h.hiphotos.baidu.com/lvpics/w=1000/sign=049d1d655cafa40f3cc6cadd9b54024f/29381f30e924b899de6cd36f68061d950a7bf611.jpg");
        photos.add("http://imgsrc.baidu.com/forum/pic/item/3b292df5e0fe992503d986c834a85edf8cb17155.jpg");
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int position) {
        return photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_home_page_images, null);
        }
        SimpleDraweeView item_child_iamge = ViewHolder.get(view, R.id.item_child_iamge);
        item_child_iamge.getLayoutParams().width = (screenWidth - DisplayUtil.dip2px(context, 50)) / 3;
        item_child_iamge.setAspectRatio(1);
        item_child_iamge.setImageURI(Uri.parse(photos.get(position)));

        if (!views.contains(item_child_iamge)) {
            views.add(item_child_iamge);
        }
        return view;
    }

    private List<SimpleDraweeView> views = new ArrayList<>();


    public List<String> getPhones() {
        return photos;
    }
}

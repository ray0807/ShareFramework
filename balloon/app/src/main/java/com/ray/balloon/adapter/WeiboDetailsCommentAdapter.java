package com.ray.balloon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ray.balloon.R;

/**
 * Created by Ray on 2016/3/23.
 * https://github.com/ray0807
 */
public class WeiboDetailsCommentAdapter extends BaseAdapter {
    private Context context;


    public WeiboDetailsCommentAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 200;
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
            view= LayoutInflater.from(context).inflate(R.layout.item_weibo_details_conmment,null);
        }
        return view;
    }
}

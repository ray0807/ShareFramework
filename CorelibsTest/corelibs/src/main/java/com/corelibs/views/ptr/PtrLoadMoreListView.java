package com.corelibs.views.ptr;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.corelibs.R;
import com.corelibs.views.cube.ptr.PtrFrameLayout;
import com.corelibs.views.ptr.base.PtrLoadMoreBase;
import com.corelibs.views.ptr.wrapper.ListViewWrapper;

/**
 * 带下拉刷新, 自动加载的ListView
 * <BR/>
 * Created by Ryan on 2016/1/20.
 */
public class PtrLoadMoreListView extends PtrLoadMoreBase<ListView, ListViewWrapper<ListView>> {

    private ListView listView;

    public PtrLoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PtrLoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public ListViewWrapper<ListView> getWrapper() {
        return new ListViewWrapper<>(listView);
    }

    @Override
    protected PtrFrameLayout onPtrFrameCreated(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.ptr_listview, this);
        listView = (ListView) view.findViewById(R.id.ptr_content_list);
        return (PtrFrameLayout) view.findViewById(R.id.ptr_frame);
    }
}

package com.corelibs.views.ptr;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.corelibs.R;
import com.corelibs.utils.DisplayUtil;
import com.corelibs.views.cube.ptr.PtrFrameLayout;
import com.corelibs.views.ptr.base.PtrLoadMoreBase;
import com.corelibs.views.ptr.wrapper.ListViewWrapper;
import com.corelibs.views.swipemenulistview.SwipeMenu;
import com.corelibs.views.swipemenulistview.SwipeMenuCreator;
import com.corelibs.views.swipemenulistview.SwipeMenuItem;
import com.corelibs.views.swipemenulistview.SwipeMenuListView;

/**
 * 带下拉刷新, 自动加载, 以及侧滑menu的ListView, 参照{@link #createMenu} 来创建侧滑menu
 * <BR/>
 * Created by Ryan on 2016/1/20.
 */
public class PtrLoadMoreSwipeMenuListView extends PtrLoadMoreBase<SwipeMenuListView, ListViewWrapper<SwipeMenuListView>> {

    private SwipeMenuListView listView;

    public PtrLoadMoreSwipeMenuListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PtrLoadMoreSwipeMenuListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected PtrFrameLayout onPtrFrameCreated(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.ptr_swipemenulistview, this);
        listView = (SwipeMenuListView) view.findViewById(R.id.ptr_content_list);
        listView.setMenuCreator(createMenu());

        ptrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.ptr_frame);
        ptrFrameLayout.disableWhenHorizontalMove(true);
        return ptrFrameLayout;
    }

    @Override
    public ListViewWrapper<SwipeMenuListView> getWrapper() {
        return new ListViewWrapper<>(listView);
    }

    private SwipeMenuCreator createMenu() {
        return new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(context);
                openItem.setBackground(new ColorDrawable(0xffff0000));
                openItem.setWidth(DisplayUtil.dip2px(context, 90));
                openItem.setTitle("DELETE");
                openItem.setTitleSize(18);
                openItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem);
            }
        };
    }
}

package com.corelibs.views.ptr.wrapper;

import android.view.View;
import android.widget.AbsListView;

/**
 * 针对ListView/GridView等View的包装接口, 用于带自动加载更多的视图
 * <BR/>
 * Created by Ryan on 2016/1/21.
 */
public interface LoadMoreWrapper<T> {
    /**
     * 添加FooterView的包装
     */
    void addFooterView(View v, Object data, boolean isSelectable);
    /**
     * 删除FooterView的包装
     */
    boolean removeFooterView(View v);
    /**
     * 获取最后可见的position的包装
     */
    int getLastVisiblePosition();
    /**
     * 设置OnScrollListener的包装
     */
    void setOnScrollListener(AbsListView.OnScrollListener l);
    /**
     * 获取总行数的包装
     */
    int getRowCount();
    /**
     * 获取被包装的View
     */
    T getView();
}

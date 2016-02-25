package com.corelibs.views.ptr.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

import com.corelibs.R;
import com.corelibs.views.ptr.wrapper.LoadMoreWrapper;

/**
 * 带自动加载更多的ListView/GridView基类. 需通过实现{@link #getWrapper} 方法传入一个
 * {@link LoadMoreWrapper} 对象.
 * <BR/>
 * Created by Ryan on 2016/1/20.
 */
public abstract class PtrLoadMoreBase<V extends View, T extends LoadMoreWrapper<V>>
        extends PtrLollipopBaseView<V> {

    private T wrapper;
    /**
     * 我的状态
     **/
    private State state = State.ENABLED;
    /**
     * 自动加载布局
     **/
    private View loadingView;
    /**
     * 自动加载提示文字
     **/
    private TextView loadingLabel;

    private int whenToLoading = 1;
    private boolean isLoading = false;
    private boolean isCustomBackground = false;

    public PtrLoadMoreBase(Context context) {
        this(context, null);
    }

    public PtrLoadMoreBase(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PtrLoadMoreBase(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * 传入{@link LoadMoreWrapper} 包装对象
     */
    public abstract T getWrapper();

    @Override
    public V getPtrView() {
        return getWrapper().getView();
    }

    private void init() {
        wrapper = getWrapper();
        initLoadingView();
        wrapper.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (state != State.DISABLED && state != State.REFRESHING && state != State.FORCE_REFRESH) {
                    if (visibleItemCount >= totalItemCount) {
                        return;
                    } else {
                        if (view.getCount() > 0)
                            if (aboutToLoad())
                                setLoadingStatus();
                    }
                }
            }
        });
    }

    @SuppressLint("InflateParams")
    private void initLoadingView() {
        if (loadingView == null) {
            loadingView = LayoutInflater.from(context).inflate(R.layout.loading_more, null);
            loadingLabel = (TextView) loadingView.findViewById(R.id.loadingText);
        }
    }

    @Override
    public void refreshComplete() {
        super.refreshComplete();
        onLoadingFinished();
    }

    private boolean aboutToLoad() {
        for (int i = 0; i < whenToLoading; i++) {
            if (wrapper.getLastVisiblePosition() == (wrapper.getRowCount() - whenToLoading - i))
                return true;
        }
        return false;
    }

    private void load() {
        switch (state) {
            case ENABLED:
                break;

            case REFRESHING:
            case FORCE_REFRESH:
                addLoadingView();
                break;

            case DISABLED:
            case FINISHED:
                removeLoadingView();
                break;
        }
    }

    /**
     * 显示加载视图
     */
    private synchronized void addLoadingView() {
        if (!ptrFrameLayout.isRefreshing())
            if (!isLoading) {
                wrapper.addFooterView(loadingView, null, false);
                isLoading = true;
                if (handler != null)
                    handler.onLoading(ptrFrameLayout);
            }
    }

    /**
     * 删除加载视图
     */
    private synchronized void removeLoadingView() {
        if (isLoading) {
            wrapper.removeFooterView(loadingView);
            isLoading = false;
        }
    }

    /**
     * 获取控件当前状态
     */
    public State getLoadingState() {
        return state;
    }

    /**
     * 设置当前状态
     */
    private void setLoadingState(State state) {
        if (this.state == State.DISABLED && (state == State.FINISHED || state == State.REFRESHING))
            return;
        this.state = state;
        load();
    }

    /**
     * 强制刷新, 并显示加载视图
     */
    public void setLoading() {
        setLoadingState(State.FORCE_REFRESH);
    }

    /**
     * 将控件设置成刷新中状态
     */
    private void setLoadingStatus() {
        setLoadingState(State.REFRESHING);
    }

    /**
     * 加载完成时调用此方法
     */
    public void onLoadingFinished() {
        setLoadingState(State.FINISHED);
    }

    /**
     * 启动自动加载
     */
    public void enableLoading() {
        setLoadingState(State.ENABLED);
    }

    /**
     * 禁用自动加载
     */
    public void disableLoading() {
        setLoadingState(State.DISABLED);
    }

    /**
     * 设置加载视图中的文字
     */
    public void setLoadingLable(String lable) {
        if (!isCustomBackground) {
            loadingLabel.setText(lable);
        }
    }

    /**
     * 设置加载视图中的文字
     */
    public void setLoadingLable(int lable) {
        if (!isCustomBackground) {
            loadingLabel.setText(lable);
        }
    }

    /**
     * 设置加载视图中文字的颜色
     */
    public void setLoadingLableColor(int color) {
        if (!isCustomBackground) {
            loadingLabel.setTextColor(color);
        }
    }

    /**
     * 设置加载视图的背景色
     */
    public void setLoadingBackgroundColor(int color) {
        if (!isCustomBackground) {
            loadingView.setBackgroundColor(color);
        }
    }

    /**
     * 自定义背景视图, 调用此方法后其他设置颜色或文字的方法均不可用
     */
    public void setLoadingBackground(View view) {
        loadingView = view;
        isCustomBackground = true;
    }

    /**
     * 获取何时会触发自动加载的值
     */
    public int getWhenToLoading() {
        return whenToLoading;
    }

    /**
     * 设置何时会触发自动加载的值
     */
    public void setWhenToLoading(int whenToLoading) {
        this.whenToLoading = whenToLoading;
    }

    /**
     * PtrListView内部状态值
     */
    public enum State {

        /**
         * 刷新结束
         **/
        FINISHED,
        /**
         * 刷新中
         **/
        REFRESHING,
        /**
         * 失效
         **/
        DISABLED,
        /**
         * 生效, 默认状态
         **/
        ENABLED,
        /**
         * 强制刷新
         **/
        FORCE_REFRESH

    }
}

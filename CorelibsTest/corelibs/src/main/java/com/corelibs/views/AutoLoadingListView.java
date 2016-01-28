package com.corelibs.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.corelibs.views.pulltorefresh.library.PullToRefreshListView;
import com.corelibs.R;

/**
 * 带下拉刷新的自动加载ListView
 * 
 * @Copyright Copyright © 2014 蓝色互动. All rights reserved.
 * 
 * @author ZhuFan
 * @Date: 2015-6-9
 */
public class AutoLoadingListView extends PullToRefreshListView {

	/** 上下文 **/
	private Context mContext;
	/** 列表ListView **/
	private ListView mListView;
	/** 监听器, 同PullToRefreshListView的监听器 **/
	private OnRefreshListener2<ListView> mListener;
	/** 我的状态 **/
	private State mState = State.ENABLED;
	/** 自动加载布局 **/
	private View loadingView;
	/** 自动加载提示文字 **/
	private TextView loadingLabel;
	/** 列表ListView **/
	private int whenToLoading = 1;

	/** 列表ListView **/
	private boolean isLoading = false;
	/** 列表ListView **/
	private boolean isCustomBackground = false;

	public AutoLoadingListView(Context context) {
		super(context);
		init();
	}

	public AutoLoadingListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public AutoLoadingListView(Context context, Mode mode) {
		super(context, mode);
		init();
	}

	public AutoLoadingListView(Context context, Mode mode, AnimationStyle style) {
		super(context, mode, style);
		init();
	}
	
	/**
	 * 初始化
	 */
	private void init() {
		mContext = getContext();
		mListView = getRefreshableView();
		mListView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (mState != State.DISABLED && mState != State.REFRESHING && mState != State.FORSE_REFRESH) {
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
		setMode(Mode.DISABLED);
		initLoadingView();
	}
	
	/**
	 * 判断是否需要触发加载
	 * @author ZhuFan
	 * @Package com.bm.corelibs.views
	 * @return 
	 * @return boolean
	 * @throws 
	 * @Date 2015-6-12 上午10:05:10
	 */
	private boolean aboutToLoad() {
		for (int i = 0; i < whenToLoading; i++) {
			if (mListView.getLastVisiblePosition() == (mListView.getCount() - whenToLoading - i))
				return true;
		}
		return false;
	}
	
	/**
	 * 初始化加载视图
	 * @author ZhuFan
	 * @Package com.bm.corelibs.views 
	 * @return void
	 * @throws 
	 * @Date 2015-6-12 上午10:05:33
	 */
	@SuppressLint("InflateParams")
	private void initLoadingView() {
		if (loadingView == null) {
			loadingView = LayoutInflater.from(mContext).inflate(R.layout.loading_more, null);
			loadingLabel = (TextView) loadingView.findViewById(R.id.loadingText);
		}
	}
	
	/**
	 * 根据Status加载视图
	 * @author ZhuFan
	 * @Package com.bm.corelibs.views 
	 * @return void
	 * @throws 
	 * @Date 2015-6-12 上午10:05:45
	 */
	private void load() {
		mListener = getListener();
		switch (mState) {
		case ENABLED:
			break;

		case REFRESHING:
		case FORSE_REFRESH:
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
	 * @author ZhuFan
	 * @Package com.bm.corelibs.views 
	 * @return void
	 * @throws 
	 * @Date 2015-6-12 上午10:06:02
	 */
	private synchronized void addLoadingView() {
		if (getState() != com.corelibs.views.pulltorefresh.library.PullToRefreshBase.State.REFRESHING)
			if (!isLoading) {
				mListView.addFooterView(loadingView, null, false);
				isLoading = true;
				if (mListener != null)
					mListener.onPullUpToRefresh(this);
			}
	}
	
	/**
	 * 删除加载视图
	 * @author ZhuFan
	 * @Package com.bm.corelibs.views 
	 * @return void
	 * @throws 
	 * @Date 2015-6-12 上午10:06:17
	 */
	private synchronized void removeLoadingView() {
		if (isLoading) {
			mListView.removeFooterView(loadingView);
			isLoading = false;
		}
	}
	
	/**
	 * 获取控件当前状态
	 * @author ZhuFan
	 * @Package com.bm.corelibs.views
	 * @return 
	 * @return State
	 * @throws 
	 * @Date 2015-6-12 上午10:07:01
	 */
	public State getLoadingState() {
		return mState;
	}
	
	/**
	 * 设置当前状态
	 * @author ZhuFan
	 * @Package com.bm.corelibs.views
	 * @param state 
	 * @return void
	 * @throws 
	 * @Date 2015-6-12 上午10:07:18
	 */
	private void setLoadingState(State state) {
		if (mState == State.DISABLED && (state == State.FINISHED || state == State.REFRESHING))
			return;
		this.mState = state;
		load();
	}
	
	/**
	 * 强制刷新, 并显示加载视图
	 * @author ZhuFan
	 * @Package com.bm.corelibs.views 
	 * @return void
	 * @throws 
	 * @Date 2015-6-12 上午10:07:30
	 */
	public void setLoading() {
		setLoadingState(State.FORSE_REFRESH);
	}
	
	/**
	 * 将控件设置成刷新中状态
	 * @author ZhuFan
	 * @Package com.bm.corelibs.views 
	 * @return void
	 * @throws 
	 * @Date 2015-6-12 上午10:07:44
	 */
	private void setLoadingStatus() {
		setLoadingState(State.REFRESHING);
	}
	
	/**
	 * 加载完成时调用此方法
	 * @author ZhuFan
	 * @Package com.bm.corelibs.views 
	 * @return void
	 * @throws 
	 * @Date 2015-6-12 上午10:08:36
	 */
	public void OnLoadingFinished() {
		setLoadingState(State.FINISHED);
	}
	
	/**
	 * 启动自动加载
	 * @author ZhuFan
	 * @Package com.bm.corelibs.views 
	 * @return void
	 * @throws 
	 * @Date 2015-6-12 上午10:08:56
	 */
	public void enableLoading() {
		setLoadingState(State.ENABLED);
	}
	
	/**
	 * 禁用自动加载
	 * @author ZhuFan
	 * @Package com.bm.corelibs.views 
	 * @return void
	 * @throws 
	 * @Date 2015-6-12 上午10:09:09
	 */
	public void disableLoading() {
		setLoadingState(State.DISABLED);
	}
	
	/**
	 * AutoLoadingListView内部状态值
	 * @Copyright Copyright © 2014 蓝色互动. All rights reserved.
	 *
	 * @author ZhuFan
	 * @Date: 2015-6-12
	 */
	public static enum State {

		/** 刷新结束 **/
		FINISHED,
		/** 刷新中 **/
		REFRESHING,
		/** 失效 **/
		DISABLED,
		/** 生效, 默认状态 **/
		ENABLED,
		/** 强制刷新 **/
		FORSE_REFRESH;

	}
	
	/**
	 * 设置加载视图中的文字
	 * @author ZhuFan
	 * @Package com.bm.corelibs.views
	 * @param lable 
	 * @return void
	 * @throws 
	 * @Date 2015-6-12 上午10:10:22
	 */
	public void setLoadingLable(String lable) {
		if (!isCustomBackground) {
			loadingLabel.setText(lable);
		}
	}
	
	/**
	 * 设置加载视图中的文字
	 * @author ZhuFan
	 * @Package com.bm.corelibs.views
	 * @param lable 
	 * @return void
	 * @throws 
	 * @Date 2015-6-12 上午10:10:22
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
			((LinearLayout) loadingView).setBackgroundColor(color);
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
	 * 启用下拉刷新, 默认禁用
	 * @author ZhuFan
	 * @Package com.bm.corelibs.views 
	 * @return void
	 * @throws 
	 * @Date 2015-6-12 上午10:12:25
	 */
	public void enablePullDownToRefresh() {
		setMode(Mode.PULL_FROM_START);
	}

	/**
	 * 获取何时会触发自动加载的值
	 * @author ZhuFan
	 * @Package com.bm.corelibs.views
	 * @return int 1为滚动至最后一个触发自动加载, 2为倒数第二个, 依次类推
	 * @Date 2015-6-12 上午10:15:27
	 */
	public int getWhenToLoading() {
		return whenToLoading;
	}
	
	/**
	 * 设置何时会触发自动加载的值
	 * @author ZhuFan
	 * @Package com.bm.corelibs.views
	 * @param whenToLoading 1为滚动至最后一个触发自动加载, 2为倒数第二个, 依次类推
	 * @return void
	 * @throws 
	 * @Date 2015-6-12 上午10:14:26
	 */
	public void setWhenToLoading(int whenToLoading) {
		this.whenToLoading = whenToLoading;
	}

}

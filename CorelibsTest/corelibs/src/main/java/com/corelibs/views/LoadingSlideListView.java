package com.corelibs.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.corelibs.views.pulltorefresh.library.PullToRefreshSlideListView;
import com.corelibs.R;

/**
 * 带下拉刷新的自动加载ListView
 * 
 * @Copyright Copyright © 2014 蓝色互动. All rights reserved.
 * 
 * @author ZhuFan
 * @Date: 2015-6-9
 */
public class LoadingSlideListView extends PullToRefreshSlideListView {

	/** 上下文 **/
	private Context mContext;
	/** 列表ListView **/
	private SlideListView mListView;
	/** 监听器, 同PullToRefreshListView的监听器 **/
	private OnRefreshListener2<SlideListView> mListener;
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

	public LoadingSlideListView(Context context) {
		super(context);
		init();
	}

	public LoadingSlideListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LoadingSlideListView(Context context, Mode mode) {
		super(context, mode);
		init();
	}

	public LoadingSlideListView(Context context, Mode mode, AnimationStyle style) {
		super(context, mode, style);
		init();
	}

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
							if (aboutToLoad()) {
								setLoadingStatus();
							}
					}
				}
			}
		});
		setMode(Mode.DISABLED);
		initLoadingView();
	}

	private boolean aboutToLoad() {
		for (int i = 0; i < whenToLoading; i++) {
			if (mListView.getLastVisiblePosition() == (mListView.getCount() - whenToLoading - i))
				return true;
		}
		return false;
	}

	@SuppressLint("InflateParams")
	private void initLoadingView() {
		if (loadingView == null) {
			loadingView = LayoutInflater.from(mContext).inflate(R.layout.loading_more, null);
			loadingLabel = (TextView) loadingView.findViewById(R.id.loadingText);
		}
	}

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

	private synchronized void addLoadingView() {
		if (getState() != com.corelibs.views.pulltorefresh.library.PullToRefreshBase.State.REFRESHING)
			if (!isLoading) {
				mListView.addFooterView(loadingView, null, false);
				isLoading = true;
				if (mListener != null)
					mListener.onPullUpToRefresh(this);
			}
	}

	private synchronized void removeLoadingView() {
		if (isLoading) {
			mListView.removeFooterView(loadingView);
			isLoading = false;
		}
	}

	public State getLoadingState() {
		return mState;
	}

	private void setLoadingState(State state) {
		if (mState == State.DISABLED && (state == State.FINISHED || state == State.REFRESHING))
			return;
		this.mState = state;
		load();
	}

	public void setLoading() {
		setLoadingState(State.FORSE_REFRESH);
	}

	private void setLoadingStatus() {
		setLoadingState(State.REFRESHING);
	}

	public void OnLoadingFinished() {
		setLoadingState(State.FINISHED);
	}
	
	public void enableLoading(){
		setLoadingState(State.ENABLED);
	}
	
	public void disableLoading(){
		setLoadingState(State.DISABLED);
	}

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

	public void setLoadingLable(String lable) {
		if (!isCustomBackground) {
			loadingLabel.setText(lable);
		}
	}

	public void setLoadingLable(int lable) {
		if (!isCustomBackground) {
			loadingLabel.setText(lable);
		}
	}

	public void setLoadingLableColor(int color) {
		if (!isCustomBackground) {
			loadingLabel.setTextColor(color);
		}
	}

	public void setLoadingBackgroundColor(int color) {
		if (!isCustomBackground) {
			((LinearLayout) loadingView).setBackgroundColor(color);
		}
	}

	public void setLoadingBackground(View view) {
		loadingView = view;
		isCustomBackground = true;
	}

	public void enablePullDownToRefresh() {
		setMode(Mode.PULL_FROM_START);
	}

	public int getWhenToLoading() {
		return whenToLoading;
	}

	public void setWhenToLoading(int whenToLoading) {
		this.whenToLoading = whenToLoading;
	}

}

package com.corelibs.views.ptr;

import com.corelibs.views.cube.ptr.PtrFrameLayout;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.corelibs.views.swipemenulistview.SwipeMenu;
import com.corelibs.views.swipemenulistview.SwipeMenuCreator;
import com.corelibs.views.swipemenulistview.SwipeMenuItem;
import com.corelibs.views.swipemenulistview.SwipeMenuListView;
import com.corelibs.R;
import com.corelibs.utils.DisplayUtil;

public class PtrSwipeMenuListView extends PtrLollipopBaseView<SwipeMenuListView> {
	
	private SwipeMenuListView mListView;
	
	/** 我的状态 **/
	private State mState = State.ENABLED;
	/** 自动加载布局 **/
	private View loadingView;
	/** 自动加载提示文字 **/
	private TextView loadingLabel;
	
	private int whenToLoading = 1;
	private boolean isLoading = false;
	private boolean isCustomBackground = false;
	
	/**
	 * PtrListView内部状态值
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
	
	public PtrSwipeMenuListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PtrSwipeMenuListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected PtrFrameLayout onPtrFrameCreated(Context context, AttributeSet attrs) {
		View view = LayoutInflater.from(context).inflate(R.layout.ptr_swipemenulistview, this);
		mListView = (SwipeMenuListView) view.findViewById(R.id.ptr_content_list);
		
		mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.ptr_frame);
		mPtrFrameLayout.disableWhenHorizontalMove(true);
		
		initLoadingView();
		
		mListView.setMenuCreator(createMenu());
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
		return mPtrFrameLayout;
	}

	@Override
	public SwipeMenuListView getPtrView() {
		return mListView;
	}
	
	@Override
	public void refreshComplete() {
		super.refreshComplete();
		OnLoadingFinished();
	}
	
	private SwipeMenuCreator createMenu() {
		return new SwipeMenuCreator() {
			@Override
			public void create(SwipeMenu menu) {
				SwipeMenuItem openItem = new SwipeMenuItem(mContext);
				openItem.setBackground(new ColorDrawable(0xffff0000));
				openItem.setWidth(DisplayUtil.dip2px(mContext, 90));
				openItem.setTitle("DELETE");
				openItem.setTitleSize(18);
				openItem.setTitleColor(Color.WHITE);
				menu.addMenuItem(openItem);
			}
		};
	}
	
	@SuppressLint("InflateParams")
	private void initLoadingView() {
		if (loadingView == null) {
			loadingView = LayoutInflater.from(mContext).inflate(R.layout.loading_more, null);
			loadingLabel = (TextView) loadingView.findViewById(R.id.loadingText);
		}
	}
	
	private boolean aboutToLoad() {
		for (int i = 0; i < whenToLoading; i++) {
			if (mListView.getLastVisiblePosition() == (mListView.getCount() - whenToLoading - i))
				return true;
		}
		return false;
	}
	
	private void load() {
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
		if (!mPtrFrameLayout.isRefreshing())
			if (!isLoading) {
				System.out.println("before addFooterView" + (loadingView == null));
				mListView.addFooterView(loadingView, null, false);
				System.out.println("after addFooterView");
				isLoading = true;
				if (mHandler != null)
					mHandler.onLoading(mPtrFrameLayout);
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

}

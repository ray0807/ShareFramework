package com.corelibs.views.ptr.base;

import com.corelibs.views.cube.ptr.PtrDefaultHandler;
import com.corelibs.views.cube.ptr.PtrFrameLayout;
import com.corelibs.views.cube.ptr.PtrHandler;
import com.corelibs.views.cube.ptr.PtrUIHandler;
import com.corelibs.views.cube.ptr.header.MaterialHeader;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.corelibs.R;
import com.corelibs.utils.DisplayUtil;

/**
 * 根据Ultra-Pull-To-Refresh库提取的一个Lollipop风格的BaseView
 * 任意视图均可添加Ptr下拉刷新, 需继承自此类, 将子View类型设置为该视图的类型, 
 * 并提供相应的布局文件(由于Ultra-Pull-To-Refresh是使用onFinishInflate方法来初始化Header与Content,
 * 所以此处只能暂时使用加载布局文件的方式, 不能直接构造一个视图).
 * 
 * @author Ryan
 * @param <T> 子View类型, 如ListView, TextView等..
 */
public abstract class PtrLollipopBaseView<T extends View> extends FrameLayout implements PtrHandler {
	
	protected Context context;
	/** Ultra-Pull-To-Refresh的PtrFrameLayout, 需通过onPtrFrameCreated方法将创建的PtrFrameLayout返回 **/
	protected PtrFrameLayout ptrFrameLayout;
	/** 刷新回调 **/
	protected RefreshLoadHandler handler;
	/** 刷新头部, 自定的头部需实现PtrUIHandler, 默认的头部是Android Lollipop风格 **/
	protected View header;
	
	/**
	 * 在初始化PTR时回调此方法, 你可以在此加载布局文件, 初始化PtrFrameLayout并将其返回
	 */
	protected abstract PtrFrameLayout onPtrFrameCreated(Context context, AttributeSet attrs);
	/**
	 * 返回子View本身, 可以用此来做自定的操作
	 */
	public abstract T getPtrView();
	
	public PtrLollipopBaseView(Context context) {
		this(context, null);
	}

	public PtrLollipopBaseView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PtrLollipopBaseView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}
	
	private void init(Context context, AttributeSet attrs) {
		this.context = context;
		ptrFrameLayout = onPtrFrameCreated(context, attrs);
		ptrFrameLayout.setPtrHandler(this);
		header = onPtrHeaderCreated();
		
		if(!(header instanceof PtrUIHandler))
			throw new IllegalStateException("PtrHeader must implement PtrUIHandler");
		
		if(header instanceof MaterialHeader)
			((MaterialHeader) header).setPtrFrameLayout(ptrFrameLayout);
		
		ptrFrameLayout.setHeaderView(header);
		ptrFrameLayout.addPtrUIHandler((PtrUIHandler) header);
		setupPtrFrame();
	}
	
	/**
	 * 初始化ptr header, 覆写此方法实现自定义头部样式. 默认样式为Lollipop.
	 * 自定的Header需要实现PtrUIHandler
	 */
	protected View onPtrHeaderCreated() {
		MaterialHeader header = new MaterialHeader(context);
		int[] colors = getResources().getIntArray(R.array.google_colors);
		header.setColorSchemeColors(colors);
		header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
		header.setPadding(0, DisplayUtil.dip2px(context, 15), 0, DisplayUtil.dip2px(context, 10));
		return header;
	}
	
	private void setupPtrFrame() {
		ptrFrameLayout.setLoadingMinTime(1000);
		ptrFrameLayout.setDurationToCloseHeader(300);
		ptrFrameLayout.setPinContent(true);
		ptrFrameLayout.setDurationToClose(300);
	}
	
	@Override
	public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
		return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
	}

	@Override
	public void onRefreshBegin(final PtrFrameLayout frame) {
		if (handler != null)
			handler.onRefreshing(frame);
	}
	
	/**
	 * 将ptr设置为刷新状态
	 */
	public void setRefreshing() {
		ptrFrameLayout.post(new Runnable() {
			@Override
			public void run() {
				ptrFrameLayout.autoRefresh(false);
			}
		});
	}
	
	/**
	 * 设置ptr刷新回调
	 */
	public void setRefreshLoadHandler(RefreshLoadHandler handler) {
		this.handler = handler;
	}
	
	/**
	 * ptr完成时调用
	 */
	public void refreshComplete() {
		ptrFrameLayout.refreshComplete();
	}
	
	/**
	 * 获取PtrFrameLayout
	 */
	public PtrFrameLayout getPtrFrameLayout() {
		return ptrFrameLayout;
	}
	
	/**
	 * ptr刷新回调
	 */
	public interface RefreshLoadHandler {
		/**
		 * 下拉刷新时会回调此方法
		 */
		void onRefreshing(final PtrFrameLayout frame);
		/**
		 * 自动加载时会回调此方法, 此方法需在自定的自动加载ListView中调用.
		 */
		void onLoading(final PtrFrameLayout frame);
	}
}

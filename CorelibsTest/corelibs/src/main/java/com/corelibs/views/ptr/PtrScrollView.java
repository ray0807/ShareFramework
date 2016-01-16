package com.corelibs.views.ptr;

import com.corelibs.views.cube.ptr.PtrFrameLayout;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;

import com.corelibs.R;

public class PtrScrollView extends PtrLollipopBaseView<ScrollView> {

	private ScrollView mScrollView;
	private View child;

	public PtrScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public PtrScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected PtrFrameLayout onPtrFrameCreated(Context context, AttributeSet attrs) {
		View view = LayoutInflater.from(context).inflate(R.layout.ptr_scrollview, this);
		mScrollView = (ScrollView) view.findViewById(R.id.ptr_content);
		return (PtrFrameLayout) view.findViewById(R.id.ptr_frame);
	}

	@Override
	public ScrollView getPtrView() {
		return mScrollView;
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		int childCount = getChildCount();
		if(childCount > 2) {
			throw new IllegalStateException("PtrScrollView can only hold one child");
		}
		
		if(childCount > 1) {
			child = getChildAt(1);
			removeView(child);
			mScrollView.addView(child);
		}
	}

}

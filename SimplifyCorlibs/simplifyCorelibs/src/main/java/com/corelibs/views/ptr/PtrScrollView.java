package com.corelibs.views.ptr;

import com.corelibs.views.cube.ptr.PtrFrameLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;

import com.corelibs.R;
import com.corelibs.views.ptr.base.PtrLollipopBaseView;

/**
 * 带下拉刷新的ScrollView, PtrScrollView只能包含一个子元素.
 * <BR/>
 * Created by Ryan on 2016/1/20.
 */
public class PtrScrollView extends PtrLollipopBaseView<ScrollView> {

    private ScrollView mScrollView;

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
        if (childCount > 2) {
            throw new IllegalStateException("PtrScrollView can only hold one child");
        }

        if (childCount > 1) {
            View child = getChildAt(1);
            removeView(child);
            mScrollView.addView(child);
        }
    }

}

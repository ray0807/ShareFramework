package com.corelibs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 无滚动的ListView, 可与ScrollView一起使用
 * @Copyright Copyright © 2014 蓝色互动. All rights reserved.
 *
 * @author ZhuFan
 * @Date: 2015-1-7
 */
public class NoScrollingListView extends ListView {
	public NoScrollingListView(Context context) {
		super(context);
	}

	public NoScrollingListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollingListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
		        MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	
	
}

package com.corelibs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 无滚动的GridView, 可与ScrollView一起使用
 * @Copyright Copyright © 2014 蓝色互动. All rights reserved.
 *
 * @author ZhuFan
 * @Date: 2015-6-9
 */
public class NoScrollingGridView extends GridView {
	public NoScrollingGridView(Context context) {
		super(context);

	}

	public NoScrollingGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
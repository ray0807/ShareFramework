package com.ray.balloon.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ray.balloon.R;

public class NavigationBar extends FrameLayout {
	
	public static final int DEFAULT_COLOR = 0xffffffff;
	
	private Context mContext;
	private TextView title;
	private TextView tv_top_right;
	private LinearLayout back;


	public NavigationBar(Context context) {
		super(context);
		mContext = context;
		initBar();
	}

	public NavigationBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initBar();
	}

	public NavigationBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		initBar();
	}

	private void initBar() {
		View parent = LayoutInflater.from(mContext).inflate(R.layout.view_navigation_bar, this);
		title = (TextView) parent.findViewById(R.id.tv_top_title);
		back = (LinearLayout) parent.findViewById(R.id.ll_back_operate);
		tv_top_right= (TextView) findViewById(R.id.tv_top_right);

		setBackgroundColor(DEFAULT_COLOR);
	}
	

	public void setBackListener(OnClickListener listener) {
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(listener);
	}
	
	public void setTitle(int res) {
		title.setText(res);
	}
	
	public void setTitle(String res) {
		title.setText(res);
	}
	
	public void setTitleAndColor(int res, int color) {
		title.setText(res);
		title.setTextColor(color);
	}
	
	public void setTitleAndColor(String res, int color) {
		title.setText(res);
		title.setTextColor(color);
	}
	public void setRight(OnClickListener listener,String rightText){
		tv_top_right.setVisibility(View.VISIBLE);
		tv_top_right.setText(rightText);
		tv_top_right.setOnClickListener(listener);
	}
	

}

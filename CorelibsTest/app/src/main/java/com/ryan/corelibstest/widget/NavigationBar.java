package com.ryan.corelibstest.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ryan.corelibstest.R;

public class NavigationBar extends FrameLayout {
	
	public static final int DEFAULT_COLOR = 0xffffffff;
	
	private Context mContext;
	private TextView title;
	private LinearLayout back;
	private TextView tv_del, tv_logout, tv_alter;
	
	private OnClickListener deflt = new OnClickListener() {
		@Override
		public void onClick(View v) {
			((Activity)mContext).finish();
		}
	};

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
		tv_del = (TextView) parent.findViewById(R.id.tv_del);
		tv_logout = (TextView) parent.findViewById(R.id.tv_logout);
		tv_alter = (TextView) parent.findViewById(R.id.tv_alter);
		
		back.setOnClickListener(deflt);
		setBackgroundColor(DEFAULT_COLOR);
	}
	
	public void hideBackButton() {
		back.setVisibility(View.GONE);
	}
	
	public void showBackButton() {
		back.setVisibility(View.VISIBLE);
	}
	
	public void setBackAction(OnClickListener listener) {
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
	
	public void showDelete(OnClickListener listener) {
		tv_del.setVisibility(View.VISIBLE);
		tv_del.setOnClickListener(listener);
	}
	
	public void hideDelete() {
		tv_del.setVisibility(View.GONE);
	}
	
	public void showAlter(OnClickListener listener) {
		tv_alter.setVisibility(View.VISIBLE);
		tv_alter.setOnClickListener(listener);
	}
	
	public void hideAlter() {
		tv_alter.setVisibility(View.GONE);
	}
	
	public void showLogout(OnClickListener listener) {
		tv_logout.setVisibility(View.VISIBLE);
		tv_logout.setOnClickListener(listener);
	}
	
	public void hideLogout() {
		tv_logout.setVisibility(View.GONE);
	}

}

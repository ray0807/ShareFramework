package com.corelibs.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.corelibs.R;

@SuppressLint("InflateParams")
public class LoadingDialog extends Dialog implements AnimatorListener {

	private static int theme = android.R.style.Theme_Translucent_NoTitleBar;
	private Context context;
	// private CircularBar bar;
	private TextView msg;
	private View contentView;
	private LinearLayout ll_parent;
	private LinearLayout ll_container;

	private AnimatorSet showSet;
	private AnimatorSet hideSet;

	public LoadingDialog(Context context) {
		super(context, theme);
		this.context = context;
		init();
		setMessage(null);
	}

	public LoadingDialog(Context context, View view, View.OnClickListener l) {
		super(context, theme);
		this.context = context;
		initWithView(view, l);
	}

	public LoadingDialog(Context context, int viewId, View.OnClickListener l) {
		super(context, theme);
		this.context = context;
		initWithView(viewId, l);
	}

	public LoadingDialog(Context context, String message) {
		super(context, theme);
		this.context = context;
		init();
		setMessage(message);
	}

	public LoadingDialog(Context context, int message) {
		super(context, theme);
		this.context = context;
		init();
		setMessage(message);
	}

	private void init() {
		contentView = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
		setContentView(contentView);
		setCanceledOnTouchOutside(false);

		// bar = (CircularBar) contentView.findViewById(R.id.circle);
		msg = (TextView) contentView.findViewById(R.id.tv_msg);
		ll_parent = (LinearLayout) contentView.findViewById(R.id.ll_parent);
		ll_container = (LinearLayout) contentView.findViewById(R.id.ll_container);

		initAnimator();
	}

	private void initWithView(View view, View.OnClickListener l) {
		contentView = view;
		setContentView(contentView);

		ll_parent = (LinearLayout) contentView.findViewById(R.id.ll_parent);
		ll_container = (LinearLayout) contentView.findViewById(R.id.ll_container);
		View cview = ll_container.getChildAt(0);
		cview.setOnClickListener(l);
		initAnimator();
	}

	private void initWithView(int viewId, View.OnClickListener l) {
		contentView = LayoutInflater.from(context).inflate(viewId, null);
		setContentView(contentView);

		ll_parent = (LinearLayout) contentView.findViewById(R.id.ll_parent);
		ll_container = (LinearLayout) contentView.findViewById(R.id.ll_container);
		View view = ll_container.getChildAt(0);
		view.setOnClickListener(l);
		initAnimator();
	}

	private void initAnimator() {
		showSet = new AnimatorSet();
		hideSet = new AnimatorSet();

		ObjectAnimator showScaleX = ObjectAnimator.ofFloat(ll_parent, "scaleX", 0.65f, 1f);
		ObjectAnimator showScaleY = ObjectAnimator.ofFloat(ll_parent, "scaleY", 0.65f, 1f);
		ObjectAnimator showAlpha = ObjectAnimator.ofFloat(ll_parent, "alpha", 0f, 1f);
		ObjectAnimator showAlphaC = ObjectAnimator.ofFloat(ll_container, "alpha", 0f, 1f);

		showSet.play(showScaleX).with(showScaleY).with(showAlpha).with(showAlphaC);
		showSet.setDuration(150);
		showSet.addListener(this);

		ObjectAnimator hideScaleX = ObjectAnimator.ofFloat(ll_parent, "scaleX", 0.65f);
		ObjectAnimator hideScaleY = ObjectAnimator.ofFloat(ll_parent, "scaleY", 0.65f);
		ObjectAnimator hideAlpha = ObjectAnimator.ofFloat(ll_parent, "alpha", 0f);
		ObjectAnimator hideAlphaC = ObjectAnimator.ofFloat(ll_container, "alpha", 0f);

		hideSet.play(hideScaleX).with(hideScaleY).with(hideAlpha).with(hideAlphaC);
		hideSet.setDuration(150);
		hideSet.addListener(this);
	}

	public void setMessage(String message) {
		if (message == null) {
			if (msg.getVisibility() == View.VISIBLE)
				msg.setVisibility(View.GONE);
			return;
		}

		if (msg.getVisibility() == View.GONE)
			msg.setVisibility(View.VISIBLE);
		msg.setText(message);
	}

	public void setMessage(int message) {
		if (msg.getVisibility() == View.GONE)
			msg.setVisibility(View.VISIBLE);
		msg.setText(message);
	}

	private boolean shouldShow;

	@Override
	public void show() {
		shouldShow = true;
		showSet.start();
	}

	@Override
	public void dismiss() {
		shouldShow = false;
		hideSet.start();
	}

	@Override
	public void onAnimationStart(Animator animation) {
		if (!isShowing() && shouldShow)
			super.show();
	}

	@Override
	public void onAnimationEnd(Animator animation) {
		if (isShowing() && !shouldShow && context != null)
			super.dismiss();
	}

	@Override
	public void onAnimationCancel(Animator animation) {
	}

	@Override
	public void onAnimationRepeat(Animator animation) {
	}

}

package com.ray.balloon.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ray.balloon.R;

public class CommonDialog extends Dialog {

	public static final int DIALOG_OK = 1; // 带有OK键值的状态
	public static final int DIALOG_OK_CANCEL = 2; // 带有OK Cancel键值的状态
	private Context context;
	private String title;
	private String msg;
	private int status; // 是否带有多个按钮的状态
	private TextView textView, textv_title, tv_dialog_cut;
	private Button btn_confir, btn_cancel;

	/**
	 * yangqc
	 * 
	 * @param context
	 * @param title
	 *            标题内容
	 * @param msg
	 *            msg内容
	 * @param status
	 *            标志位 用于区分状态
	 */
	public CommonDialog(Context context, String title, String msg, int status) {
		super(context, R.style.MyDialog);
		this.context = context;
		this.title = title;
		this.msg = msg;
		this.status = status;
		// this.onClickListener = onClickListener;
	}

	@SuppressLint("InflateParams")
	public View InitDialog(String title, String msg) {

		View v = LayoutInflater.from(context).inflate(R.layout.item_textview, null);
		textView = (TextView) v.findViewById(R.id.textv_content);
		textView.setText(msg);
		textView.setGravity(Gravity.CENTER);
		textView.setTextColor(Color.parseColor("#666666"));
		return v;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_common);

		// 实例化控件
		btn_confir = (Button) findViewById(R.id.btn_dialog_confir);
		btn_cancel = (Button) findViewById(R.id.btn_dialog_cancel);
		tv_dialog_cut = (TextView) findViewById(R.id.tv_dialog_cut);
		textv_title = (TextView) findViewById(R.id.textv_title);

		// 设置标题
		textv_title.setText(title);
		// 添加要引用的布局
		LinearLayout mainLayout = (LinearLayout) findViewById(R.id.relayout_addview);
		View v = InitDialog(title, msg);
		mainLayout.addView(v);

		setButtonView();

		// 给确认键设置监听
		btn_confir.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CommonDialog.this.dismiss();
			}
		});
		// 点击取消关闭dialog
		btn_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CommonDialog.this.dismiss();
			}
		});

	}

	private void setButtonView() {
		if (status == 1) {
			btn_confir.setVisibility(View.VISIBLE);
			tv_dialog_cut.setVisibility(View.GONE);
			btn_cancel.setVisibility(View.GONE);
		} else if (status == 2) {
			btn_confir.setVisibility(View.VISIBLE);
			tv_dialog_cut.setVisibility(View.VISIBLE);
			btn_cancel.setVisibility(View.VISIBLE);
		}
	}

	public void setStatus(int status) {
		this.status = status;
		setButtonView();
	}

	public void setTitle(String title) {
		this.title = title;
		textv_title.setText(title);
	}

	public void setpositext(String text) {
		btn_confir.setText(text);
	}

	public void setMsg(String msg) {
		this.msg = msg;
		textView.setText(msg);
	}

	public void setPositiveListener(View.OnClickListener listener) {
		btn_confir.setOnClickListener(listener);

	}

}
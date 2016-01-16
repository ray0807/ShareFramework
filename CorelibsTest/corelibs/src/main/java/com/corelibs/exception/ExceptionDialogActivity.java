package com.corelibs.exception;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.corelibs.R;

public class ExceptionDialogActivity extends Activity {
	
	public static final String APP_NAME = "APP_NAME";
	public static final String OUTPUT_INFO = "OUTPUT_INFO";
	
	private TextView tv_msg;
	private Button btn_nav, btn_pos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_exception_dialog);
		
		tv_msg = (TextView) findViewById(R.id.tv_msg);
		btn_nav = (Button) findViewById(R.id.btn_nav);
		btn_pos = (Button) findViewById(R.id.btn_pos);
		
		String appName = getIntent().getStringExtra(APP_NAME);
		if(appName == null || appName.length() <= 0) {
			tv_msg.setText("应用程序已停止运行");
		} else {
			tv_msg.setText(appName + "已停止运行");
		}
		
		btn_nav.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		btn_pos.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ExceptionDialogActivity.this, ExceptionDetailActivity.class);
				intent.putExtra(OUTPUT_INFO, getIntent().getStringExtra(OUTPUT_INFO));
				startActivity(intent);
				finish();
			}
		});
	}
	
}

package com.corelibs.exception;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.corelibs.R;
import com.corelibs.utils.ToastMgr;

public class ExceptionDetailActivity extends Activity {
	
	private TextView tv_detail, tv_copy, tv_close;
	private String outputInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_exception_detail);
		
		outputInfo = getIntent().getStringExtra(ExceptionDialogActivity.OUTPUT_INFO);
		
		tv_detail = (TextView) findViewById(R.id.tv_detail);
		tv_copy = (TextView) findViewById(R.id.tv_copy_info);
		tv_close = (TextView) findViewById(R.id.tv_close);
		
		tv_detail.setText(outputInfo);
		tv_close.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				finish();
			}
		});
		
		tv_copy.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
				ClipData data = ClipData.newPlainText(ExceptionDialogActivity.OUTPUT_INFO, outputInfo);
				manager.setPrimaryClip(data);
				ToastMgr.show("错误详情已复制到剪贴板");
			}
		});
	}
	
}

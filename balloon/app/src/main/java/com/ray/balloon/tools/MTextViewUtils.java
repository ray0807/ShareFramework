package com.ray.balloon.tools;


import android.content.Context;
import android.text.SpannableString;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.ray.balloon.R;
import com.ray.balloon.widget.MTextView;


public class MTextViewUtils {
	public static void setText(MTextView mTextView, String text, Context c) {
		SpannableString ss = new SpannableString(text);

		mTextView.setMText(ss);
//		mTextView.setTextSize(18);
		mTextView.setTextColor(c.getResources().getColor(R.color.carbon_grey_700));

		mTextView.invalidate();

	}

	/**
	 * 调起键盘
	 * 
	 * @param editText
	 */
	public static void openInputMethod(EditText editText) {

		InputMethodManager inputManager = (InputMethodManager) editText

				.getContext().getSystemService(

						Context.INPUT_METHOD_SERVICE);

		inputManager.showSoftInput(editText, 0);

	}

	/**
	 * 隐藏键盘
	 * 
	 * @param context
	 * @param v
	 */
	public static void hideInputMethod(Context context, View v) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		}
	}

}

package com.corelibs.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * toast管理器, 确保只有一个Toast对象
 */
@SuppressLint("ShowToast")
public class ToastMgr {
    private static Toast it;
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    private ToastMgr() {
    }

    /**
     * 在程序初始化的时候调用, 只需调用一次
     */
    public static void init(Context _context) {
        mContext = _context;
        View v = Toast.makeText(mContext, "", Toast.LENGTH_SHORT).getView();
        init(mContext, v);
    }

    /**
     * 在程序初始化的时候调用, 只需调用一次
     */
    public static void init(Context _context, View view) {
        it = new Toast(_context);
        it.setView(view);
    }

    /**
     * 设置Toast背景
     */
    public static void setBackgroundView(View view) {
        checkInit();
        it.setView(view);
    }

    public static void show(CharSequence text, int duration) {
        checkInit();
        it.setText(text);
        it.setDuration(duration);
        it.show();
    }

    public static void show(int resid, int duration) {
        checkInit();
        it.setText(resid);
        it.setDuration(duration);
        it.show();
    }

    public static void show(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void show(int resid) {
        show(resid, Toast.LENGTH_SHORT);
    }

    private static void checkInit() {
        if (it == null) {
            throw new IllegalStateException("ToastMgr is not initialized, please call init once before you call this method");
        }
    }
}

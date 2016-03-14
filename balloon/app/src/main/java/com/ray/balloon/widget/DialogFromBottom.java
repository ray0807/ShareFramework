package com.ray.balloon.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.corelibs.utils.ToastMgr;
import com.corelibs.utils.Tools;
import com.ray.balloon.R;
import com.ray.balloon.view.bluetooth.BluetoothActivity;

import carbon.widget.Button;
import carbon.widget.EditText;
import carbon.widget.TextView;

/**
 * Created by Administrator on 2016/3/9.
 */
public class DialogFromBottom implements View.OnClickListener {
    private Context c;
    private Dialog dialog;
    private static int theme = android.R.style.Theme_Translucent_NoTitleBar;
    private Button btn_commend1;
    private Button btn_commend2;
    private Button btn_commend3;
    private Button btn_commend4;
    private Button btn_commend5;
    private Button btn_commend6;
    private EditText et_input_commend;
    private TextView tv_show_retruen;

    private BluetoothActivity activity;

    public DialogFromBottom(Context c) {
        this.c = c;
        activity = (BluetoothActivity) c;
        init();
    }

    private void init() {
        dialog = new Dialog(c, theme);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        LayoutInflater inflater = LayoutInflater.from(c);
        View v = inflater.inflate(R.layout.view_show_blue_controller, null);
        dialog.setContentView(v);// 设置使用View
        btn_commend1 = (Button) v.findViewById(R.id.btn_commend1);
        btn_commend2 = (Button) v.findViewById(R.id.btn_commend2);
        btn_commend3 = (Button) v.findViewById(R.id.btn_commend3);
        btn_commend4 = (Button) v.findViewById(R.id.btn_commend4);
        btn_commend5 = (Button) v.findViewById(R.id.btn_commend5);
        btn_commend6 = (Button) v.findViewById(R.id.btn_commend6);
        tv_show_retruen = (TextView) v.findViewById(R.id.tv_show_retruen);
        et_input_commend = (EditText) v.findViewById(R.id.et_input_commend);
        btn_commend1.setOnClickListener(this);
        btn_commend2.setOnClickListener(this);
        btn_commend3.setOnClickListener(this);
        btn_commend4.setOnClickListener(this);
        btn_commend5.setOnClickListener(this);
        btn_commend6.setOnClickListener(this);
    }

    public void setText(String text) {
        tv_show_retruen.setText(text);
    }

    public void show() {
        if (!dialog.isShowing())
            dialog.show();
    }

    public void dismiss() {
        if (dialog.isShowing())
            dialog.dismiss();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commend1:
                if (Tools.isNull(et_input_commend.getText().toString())) {
                    ToastMgr.show("请输入命令");
                    return;
                }
                activity.sendMessage(et_input_commend.getText().toString());
                break;
        }
    }
}

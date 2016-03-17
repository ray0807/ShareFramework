package com.ray.balloon.widget;

import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.ray.balloon.R;
import com.ray.balloon.adapter.BluetoothDevicesAdapter;
import com.ray.balloon.callback.RecyclerViewCallback;

import carbon.widget.RecyclerView;
import carbon.widget.TextView;

/**
 * Created by Administrator on 2016/3/9.
 */
public class DialogSelecteDevices implements View.OnClickListener {
    private Context c;
    private Dialog dialog;
    private TextView carbon_windowTitle;
    private RecyclerView list_device;
    private BluetoothDevicesAdapter adapter;
    private static int theme = android.R.style.Theme_Translucent_NoTitleBar;

    public DialogSelecteDevices(Context c) {
        this.c = c;
        init();
    }

    private void init() {
        dialog = new Dialog(c, theme);
        LayoutInflater inflater = LayoutInflater.from(c);
        View v = inflater.inflate(R.layout.dialog_bluetooth_devices, null);
        dialog.setContentView(v);// 设置使用View
        carbon_windowTitle = (TextView) v.findViewById(R.id.carbon_windowTitle);
        list_device = (RecyclerView) v.findViewById(R.id.list_device);

        list_device.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false));
        adapter = new BluetoothDevicesAdapter();
//        SpaceItemDecoration itemSpace = new SpaceItemDecoration(DisplayUtil.dip2px(c, 10));
//        list_device.addItemDecoration(itemSpace);
        list_device.setAdapter(adapter);
    }

    public void setText(String text) {
        carbon_windowTitle.setText(text);
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
                break;
        }
    }

    public void setDevice(BluetoothDevice device) {
        adapter.addDevice(device);
    }

    public void setOnItemClick(RecyclerViewCallback callback) {
        adapter.setOnItemClickListener(callback);
    }

    public void setState(int what, BluetoothDevice clickDevice) {
        adapter.setState(what, clickDevice);
    }
}

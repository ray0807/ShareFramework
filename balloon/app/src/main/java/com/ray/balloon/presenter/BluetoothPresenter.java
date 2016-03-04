package com.ray.balloon.presenter;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;

import com.corelibs.base.BaseRxPresenter;
import com.ray.balloon.view.bluetooth.BluetoothView;

/**
 * 蓝牙
 */
public class BluetoothPresenter extends BaseRxPresenter<BluetoothView> {
    public static final int DISCOVERABLE_DURATION_RESULT_CODE = 999;
    private BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onViewAttached() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

//    public void connect(BluetoothDevice device) {
//        // 固定的UUID
//        UUID uuid = UUID.fromString(Constant.UUID);
//        BluetoothSocket socket = null;
//        try {
//            socket = device.createRfcommSocketToServiceRecord(uuid);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            socket.connect();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    public void turnOn(Activity activity) {
        if (bluetoothAdapter == null) {
            return ;
        }
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
            startDiscovery();
            //让手机设备可见5分钟
            //Intent discoveryEnableIntent = new Intent(BluetoothDevicesAdapter.ACTION_REQUEST_DISCOVERABLE);
            //discoveryEnableIntent.putExtra(BluetoothDevicesAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            //activity.startActivityForResult(discoveryEnableIntent,DISCOVERABLE_DURATION_RESULT_CODE);
        }
    }

    public boolean getEnableBluetooth() {
        return bluetoothAdapter != null ? true : false;
    }

    public boolean getBluetoothTurnOnState() {
        if (bluetoothAdapter == null) {
            return false;
        }
        if (bluetoothAdapter.isEnabled())
            startDiscovery();
        return bluetoothAdapter.isEnabled();
    }

    public void startDiscovery() {
        if (bluetoothAdapter == null) {
            return ;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!bluetoothAdapter.startDiscovery()) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void cancleDiscovery(){
        bluetoothAdapter.cancelDiscovery();
    }

}

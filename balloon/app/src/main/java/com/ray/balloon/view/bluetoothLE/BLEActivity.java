package com.ray.balloon.view.bluetoothLE;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.corelibs.utils.ToastMgr;
import com.ray.balloon.R;
import com.ray.balloon.widget.NavigationBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/3/8.
 */
public class BLEActivity extends Activity implements View.OnClickListener {
    private Button button_sync_device_time;
    private Button button_device_current_time;
    private Button button_device_battery;
    private Button button_device_all_date;
    private Button button_device_one_min_date;
    private NavigationBar bar;
    private TextView tv_show;
    private PopupWindow pw;
    private BluetoothAdapter bluetoothAdapter;
    private Intent gattServiceIntent;
    private ArrayList<BluetoothDevice> devicesData = new ArrayList<BluetoothDevice>();
    private String mDeviceName;//点击后的设备名
    private String mDeviceAddress;//点击后的设备MAC地址

    private static final int CAN_GET_DATA = 100;
    private static final int SHOW_TEXT = 101;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CAN_GET_DATA:
                    tv_show.setText("可以发送命令获取数据了");
                    break;
                case SHOW_TEXT:
                    tv_show.setText("获取到数据：" + msg.obj.toString());
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);
        button_sync_device_time = (Button) findViewById(R.id.button_sync_device_time);
        button_device_current_time = (Button) findViewById(R.id.button_device_current_time);
        button_device_battery = (Button) findViewById(R.id.button_device_battery);
        button_device_all_date = (Button) findViewById(R.id.button_device_all_date);
        button_device_one_min_date = (Button) findViewById(R.id.button_device_one_min_date);
        bar = (NavigationBar) findViewById(R.id.bar);
        button_sync_device_time.setOnClickListener(this);
        button_device_current_time.setOnClickListener(this);
        button_device_battery.setOnClickListener(this);
        button_device_all_date.setOnClickListener(this);
        button_device_one_min_date.setOnClickListener(this);
        bar.setTitle("蓝牙4.0");
        bar.setBackListener(this);
        bar.setRight(this, "查找设备");
        tv_show = (TextView) findViewById(R.id.tv_show);
        initLogic();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initLogic() {
        gattServiceIntent = new Intent(this, BluetoothLeService.class);
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (!getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_BLUETOOTH_LE)) {
            ToastMgr.show("不支持蓝牙");
        }

        // 获得蓝牙管理器
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled()) {
                bluetoothAdapter.enable();
            }
        }
        boolean isSupport = bluetoothAdapter.startLeScan(mLeScanCallback);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sync_device_time:
                getSynchronizationTime();
                break;
            case R.id.button_device_current_time:
                getDeviceCurrentTime();
                break;
            case R.id.button_device_battery:
                getDeviceBattery();
                break;
            case R.id.button_device_all_date:
                getDeviceAllData();
                break;
            case R.id.button_device_one_min_date:
                getDeviceOneMinData();
                break;
            case R.id.tv_top_right:
                showPopupWindow(v);
                break;
            case R.id.ll_back_operate:
                finish();
                break;
        }
    }

    // 设备扫描回调
    @SuppressLint("NewApi")
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi,
                             byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // 扫描到后添加然后刷新
                    if (!devicesData.contains(device)) {
                        devicesData.add(device);
                    }
                }
            });
        }
    };

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter
                .addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }


    // 服务链接
    ServiceConnection mServiceConnection = new ServiceConnection() {

        // 链接服务
        @Override
        public void onServiceConnected(ComponentName componentName,
                                       IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service)
                    .getService();
            if (!mBluetoothLeService.initialize()) {
                finish();
            }

            // 链接设备
            boolean flag = mBluetoothLeService.connect(mDeviceAddress);
            if (flag) {
                ToastMgr.show("链接成功ServiceConnection");
            }
        }

        // 断开服务
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
            ToastMgr.show("断开连接");
        }
    };

    private BluetoothLeService mBluetoothLeService;
    // 广播接受 者
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            // 如果已经链接了
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                ToastMgr.show("链接成功BroadcastReceiver");
//                if (mDeviceName.equalsIgnoreCase("目明乐视")) {
//                }


                // 如果没有链接
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED
                    .equals(action)) {
                ToastMgr.show("链接失败");

                // 如果发现服务
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED
                    .equals(action)) {
                ToastMgr.show("可以获取数据");
                displayGattServices(mBluetoothLeService
                        .getSupportedGattServices());
                handler.sendEmptyMessage(CAN_GET_DATA);
                //获取数据
                getStartData();


                // 有用的数据
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                String str = intent
                        .getStringExtra(BluetoothLeService.EXTRA_DATA);
                String[] stringByte = str.split(" ");
                StringBuffer sb = new StringBuffer();
                for (int j = 0; j < stringByte.length; j++) {
                    sb.append(stringByte[j]).append(" ");
                }
                Log.i("wanglei", "sb:" + sb.toString());
                Message msg = Message.obtain();
                msg.what = SHOW_TEXT;
                msg.obj = sb.toString();
                handler.sendMessage(msg);
            }
        }
    };
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null)
            return;
        String uuid = null;
        String unknownServiceString = "未知设备";
        String unknownCharaString = "Unknown characteristic";
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData = new ArrayList<ArrayList<HashMap<String, String>>>();
        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();
            currentServiceData.put(LIST_NAME,
                    SampleGattAttributes.lookup(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);

            ArrayList<HashMap<String, String>> gattCharacteristicGroupData = new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics = gattService
                    .getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas = new ArrayList<BluetoothGattCharacteristic>();

            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();
                currentCharaData.put(LIST_NAME,
                        SampleGattAttributes.lookup(uuid, unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);
            }
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }
    }

    private void showPopupWindow(View v) {
        if (devicesData.size() == 0) {
            View contentView = LayoutInflater.from(
                    BLEActivity.this).inflate(
                    R.layout.view_pw_nodata, null);
            pw = new PopupWindow(contentView, 400, 500);
            pw.setAnimationStyle(R.style.pw_style);
            int[] location = new int[2];

            pw.setFocusable(true);
            pw.setBackgroundDrawable(new BitmapDrawable());
            // 显示的位置
            pw.showAtLocation(v, Gravity.CENTER_VERTICAL, location[0],
                    location[1]);
        } else {
            View contentView = LayoutInflater.from(
                    BLEActivity.this).inflate(
                    R.layout.view_pw, null);
            pw = new PopupWindow(contentView, 400, 500);

            pw.setAnimationStyle(R.style.pw_style);
            int[] location = new int[2];
            pw.setFocusable(true);
            pw.setBackgroundDrawable(new BitmapDrawable());
            ListView device_list = (ListView) contentView
                    .findViewById(R.id.device_list_id);
            device_list.setAdapter(new LeDeviceListAdapter(
                    BLEActivity.this, devicesData));
            pw.showAtLocation(v, Gravity.CENTER_VERTICAL, location[0],
                    location[1]);
            device_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    BluetoothDevice device = devicesData.get(position);
                    mDeviceName = device.getName();
                    mDeviceAddress = device.getAddress();
                    pw.dismiss();
                    bindService(gattServiceIntent, mServiceConnection,
                            BIND_AUTO_CREATE);
                    isBind = true;
                    if (mBluetoothLeService != null) {
                        final boolean result = mBluetoothLeService
                                .connect(mDeviceAddress);
                    }
                }
            });
        }
    }

    private boolean isBind = false;

    private class LeDeviceListAdapter extends BaseAdapter {
        ArrayList<BluetoothDevice> list;
        Context context;

        public LeDeviceListAdapter(Context context,
                                   ArrayList<BluetoothDevice> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = View.inflate(context, R.layout.list_item_device, null);
                viewHolder.deviceAddress = (TextView) view
                        .findViewById(R.id.device_address);
                viewHolder.deviceName = (TextView) view
                        .findViewById(R.id.device_name);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            BluetoothDevice device = list.get(i);
            String deviceName = device.getName();
            String deviceAddress = device.getAddress();
            if (deviceName != null && deviceName.length() > 0) {
                viewHolder.deviceName.setText(deviceName);
            }

            if (deviceName != null && deviceName.length() > 0) {
                viewHolder.deviceAddress.setText(deviceAddress);
            }

            return view;
        }
    }

    static class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
    }


    /**
     * 获取设备当前时间
     */
    private void getDeviceCurrentTime() {
        byte[] buffer = new byte[4];
        buffer[0] = (byte) 0xAA;
        buffer[1] = (byte) 0x01;
        buffer[2] = (byte) 0xF9;
        buffer[3] = (byte) 0x00;
        mBluetoothLeService.writeLlsAlertLevel(2, buffer);
    }

    /**
     * 获取设备电量
     */
    private void getDeviceBattery() {
        byte[] buffer = new byte[4];
        buffer[0] = (byte) 0xAA;
        buffer[1] = (byte) 0x01;
        buffer[2] = (byte) 0xF4;
        buffer[3] = (byte) 0x00;
        mBluetoothLeService.writeLlsAlertLevel(2, buffer);
    }

    /**
     * 获取设备一分钟数据
     */
    private void getDeviceOneMinData() {
        byte[] buffer = new byte[5];
        buffer[0] = (byte) 0xAA;
        buffer[1] = (byte) 0x01;
        buffer[2] = (byte) 0xFE;
        buffer[3] = (byte) 0x01;
        buffer[4] = (byte) 0x01;
        mBluetoothLeService.writeLlsAlertLevel(2, buffer);
    }

    /**
     * 获取设备所有训练数据
     */
    private void getDeviceAllData() {
        byte[] buffer = new byte[4];
        buffer[0] = (byte) 0xAA;
        buffer[1] = (byte) 0x01;
        buffer[2] = (byte) 0xFC;
        buffer[3] = (byte) 0x00;
        mBluetoothLeService.writeLlsAlertLevel(2, buffer);
    }


    // 与设备时间同步
    private void getSynchronizationTime() {
        // AA 01 F8 05 15 07 22 17 30
        byte[] buffer = new byte[9];
        buffer[0] = (byte) 0xAA;
        buffer[1] = (byte) 0x01;
        buffer[2] = (byte) 0xF8;
        buffer[3] = (byte) 0x05;
        buffer[4] = (byte) 0x16;//2016
        buffer[5] = (byte) 0x03;
        buffer[6] = (byte) 0x08;
        buffer[7] = (byte) 0x15;
        buffer[8] = (byte) 0x57;
        mBluetoothLeService.writeLlsAlertLevel(2, buffer);
    }

    private void getStartData() {
        final BluetoothGattCharacteristic characteristic = mGattCharacteristics
                .get(3).get(0);
        mBluetoothLeService.setCharacteristicNotification(characteristic, true);
        mBluetoothLeService.readCharacteristic(characteristic);
        final BluetoothGattCharacteristic characteristic2 = mGattCharacteristics
                .get(3).get(0);
        mBluetoothLeService
                .setCharacteristicNotification(characteristic2, true);
        byte[] bb = new byte[40];
        bb[0] = (byte) 0xFF;
        bb[1] = (byte) 0xA0;
        bb[2] = (byte) 0xFF;
        bb[3] = (byte) 0xA0;
        bb[4] = (byte) 0xFF;
        bb[5] = (byte) 0xA0;
        bb[6] = (byte) 0xFF;
        bb[7] = (byte) 0xA0;
        bb[8] = (byte) 0xFF;
        bb[9] = (byte) 0xA0;
        bb[10] = (byte) 0xFF;
        bb[11] = (byte) 0xA0;
        bb[12] = (byte) 0xFF;
        bb[13] = (byte) 0xA0;
        bb[14] = (byte) 0xFF;
        bb[15] = (byte) 0xA0;
        bb[16] = (byte) 0xFF;
        bb[17] = (byte) 0xA0;
        bb[18] = (byte) 0xFF;
        bb[19] = (byte) 0xA0;
        bb[20] = (byte) 0xFF;
        bb[21] = (byte) 0xA0;
        bb[22] = (byte) 0xFF;
        bb[23] = (byte) 0xA0;
        bb[24] = (byte) 0xFF;
        bb[25] = (byte) 0xA0;
        bb[26] = (byte) 0xFF;
        bb[27] = (byte) 0xA0;
        bb[28] = (byte) 0xFF;
        bb[29] = (byte) 0xA0;
        bb[30] = (byte) 0xFF;
        bb[31] = (byte) 0xA0;
        bb[32] = (byte) 0xFF;
        bb[33] = (byte) 0xA0;
        bb[34] = (byte) 0xFF;
        bb[35] = (byte) 0xA0;
        bb[36] = (byte) 0xFF;
        bb[37] = (byte) 0xA0;
        bb[38] = (byte) 0xFF;
        bb[39] = (byte) 0xA0;

        mBluetoothLeService.writeLlsAlertLevel(2, bb);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onDestroy() {
        bluetoothAdapter.stopLeScan(mLeScanCallback);
        unregisterReceiver(mGattUpdateReceiver);
        if (isBind){
            unbindService(mServiceConnection);
        }
        mBluetoothLeService = null;
        super.onDestroy();
    }


}

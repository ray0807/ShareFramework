package com.ray.balloon.view.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.corelibs.base.BaseActivity;
import com.corelibs.utils.ToastMgr;
import com.corelibs.views.SplideBackLinearLayout;
import com.ray.balloon.R;
import com.ray.balloon.callback.RecyclerViewCallback;
import com.ray.balloon.presenter.BluetoothPresenter;
import com.ray.balloon.widget.DialogFromBottom;
import com.ray.balloon.widget.DialogSelecteDevices;

import java.lang.reflect.Method;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import carbon.beta.TransitionLayout;
import carbon.widget.FrameLayout;
import carbon.widget.ImageView;
import carbon.widget.LinearLayout;
import carbon.widget.Toolbar;

/**
 * Created by Administrator on 2016/3/3.
 */
public class BluetoothActivity extends BaseActivity<BluetoothView, BluetoothPresenter> implements BluetoothView, RecyclerViewCallback {
    private boolean isTurnOn = false;

    private int postion = -1;
    private DialogFromBottom dialog;
    private DialogSelecteDevices dialogSelecteDevices;
    @Bind(R.id.spl_back)
    SplideBackLinearLayout spl_back;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.icon_bluetooth)
    ImageView icon_bluetooth;
    @Bind(R.id.powerMenu)
    View powerMenu;
    @Bind(R.id.transition)
    TransitionLayout transitionLayout;
    @Bind(R.id.ll_turn_on)
    LinearLayout ll_turn_on;

    private BluetoothDevice clickDevice;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothPresenter.BLUETOOTH_IS_CONNECTING:
                    dialogSelecteDevices.setState(msg.what, clickDevice);
                    toolbar.setText("蓝牙设备(正在连接...)");
                    break;
                case BluetoothPresenter.BLUETOOTH_IS_CONNECTED:
                    dialogSelecteDevices.setState(msg.what, clickDevice);
                    dialog.show();
                    toolbar.setText("蓝牙设备(已连接)");
                    break;
                case BluetoothPresenter.BLUETOOTH_IS_CONNECT_FAIL:
                    dialogSelecteDevices.setState(msg.what, clickDevice);
                    dialog.dismiss();
                    toolbar.setText("蓝牙设备(连接失败)");
                    break;
                case BluetoothPresenter.BLUETOOTH_IS_CONNECT_LOST:
                    dialogSelecteDevices.setState(msg.what, clickDevice);
                    dialog.dismiss();
                    toolbar.setText("蓝牙设备(断开)");
                    break;
                case BluetoothPresenter.BLUETOOTH_RECEIVE_MSG:
                    ToastMgr.show("蓝牙收到消息：" + msg.obj.toString());
                    dialog.setText(msg.obj.toString());
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bluetooth;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        spl_back.setBackListener(this);
        toolbar.setText(R.string.bluetooth_title);

        spl_back.setBackListener(this);

        toolbar.setBackgroundColor(getResources().getColor(R.color.main));
        toolbar.setIcon(R.drawable.img_back);
        initLayout();
        initReceiver();
        dialog = new DialogFromBottom(this);
        if (!getPresenter().getEnableBluetooth(handler)) {
            showToast("您的设备暂不支持蓝牙");
        }


        isTurnOn = getPresenter().getBluetoothTurnOnState();
        if (!isTurnOn) {
            showToast("您暂未开启蓝牙");
        } else {
            getPresenter().start();
        }


    }

    private void initLayout() {
        dialogSelecteDevices = new DialogSelecteDevices(this);
        dialogSelecteDevices.setOnItemClick(this);
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        if (getPresenter() != null) {
            getPresenter().cancleDiscovery();
            getPresenter().stop();
        }

        super.onDestroy();
    }

    private void initReceiver() {
        // 注册BroadcastReceiver
        IntentFilter filter = new IntentFilter();

        filter.addAction(BluetoothDevice.ACTION_FOUND);//发现设备
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter); // 不要忘了之后解除绑定

    }

    @Override
    protected BluetoothPresenter createPresenter() {
        return new BluetoothPresenter();
    }


    @OnClick(R.id.icon_bluetooth)
    protected void makeTureTurnOn() {
        isTurnOn = getPresenter().getBluetoothTurnOnState();
        if (isTurnOn) {
            //已经打开
            dialogSelecteDevices.show();
        } else {
            if (powerMenu.getVisibility() == View.VISIBLE)
                return;
            transitionLayout.setCurrentChild(0);
            final List<View> viewsWithTag = ((LinearLayout) transitionLayout.getChildAt(0)).findViewsWithTag("animate");
            for (int i = 0; i < viewsWithTag.size(); i++)
                viewsWithTag.get(i).setVisibility(View.INVISIBLE);
            powerMenu.setVisibility(View.VISIBLE);
            icon_bluetooth.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < viewsWithTag.size(); i++) {
                        final int finalI = i;
                        icon_bluetooth.getHandler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                viewsWithTag.get(finalI).setVisibility(View.VISIBLE);
                            }
                        }, i * 40);
                    }
                }
            }, 200);
        }


    }

    @OnClick(R.id.ll_turn_on)
    protected void turnOn() {
        if (isTurnOn) {
            powerMenu.setVisibility(View.INVISIBLE);
            return;
        }
        final List<View> viewsWithTag = ((FrameLayout) transitionLayout.getChildAt(1)).findViewsWithTag("animate");
        for (int i = 0; i < viewsWithTag.size(); i++)
            viewsWithTag.get(i).setVisibility(View.INVISIBLE);
        ll_turn_on.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < viewsWithTag.size(); i++) {
                    final int finalI = i;
                    ll_turn_on.getHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            viewsWithTag.get(finalI).setVisibility(View.VISIBLE);
                        }
                    }, i * 20);
                }
            }
        }, 400);
        transitionLayout.setHotspot(ll_turn_on.findViewById(R.id.blueTooth_Icon));
        transitionLayout.startTransition(1, TransitionLayout.TransitionType.Radial);
        ll_turn_on.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                powerMenu.setVisibility(View.INVISIBLE);
            }
        }, 3000);
        getPresenter().turnOn(this);
    }

    @Override
    public void onBackPressed() {
        if (powerMenu.getVisibility() == View.VISIBLE) {
            powerMenu.setVisibility(View.INVISIBLE);
            return;
        }
        super.onBackPressed();
    }


    // 创建一个接收ACTION_FOUND广播的BroadcastReceiver

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // 发现设备
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // 从Intent中获取设备对象
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 将设备名称和地址放入array adapter，以便在ListView中显示

                Log.i("wanglei", "device:" + device.getName());
                dialogSelecteDevices.setDevice(device);
                if (device.getName().equalsIgnoreCase("balloon")) {
                    postion = 0;
//                    adapter.addDevice(device, postion);
                    // 搜索蓝牙设备的过程占用资源比较多，一旦找到需要连接的设备后需要及时关闭搜索
                    getPresenter().cancleDiscovery();
                    changeStatusAndConnect(device);
                } else {
//                    adapter.addDevice(device);
                }
            } else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                if (getPresenter().getBluetoothTurnOnState()) {
                    getPresenter().start();
                }
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                changeStatusAndConnect(device);
            }
        }
    };


    private void changeStatusAndConnect(BluetoothDevice device) {
        // 获取蓝牙设备的连接状态
        int connectState = device.getBondState();
        switch (connectState) {
            // 未配对
            case BluetoothDevice.BOND_NONE:
                // 配对
                try {
                    Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
                    createBondMethod.invoke(device);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            // 已配对
            case BluetoothDevice.BOND_BONDED:
                // 连接
                getPresenter().connect(device);
                break;
        }
    }

    @Override
    public void onItemClick(BluetoothDevice device) {
//        if (clickDevice != null && clickDevice.equals(device))
//            return;
        this.clickDevice = device;
//        if (getPresenter().getState() != BluetoothPresenter.STATE_CONNECTED) {
        changeStatusAndConnect(device);
//        } else {
//            dialog.show();
//        }

    }

    @Override
    public void connectSuccess() {

    }

    @Override
    public void changeState(int state) {
//        adapter.setState(state, postion);
    }

    public void sendMessage(String msg) {
        getPresenter().write(msg.getBytes());
    }

}

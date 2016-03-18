package com.ray.balloon.view.bluetooth;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.corelibs.base.BaseActivity;
import com.corelibs.utils.ToastMgr;
import com.corelibs.views.SplideBackLinearLayout;
import com.ray.balloon.R;
import com.ray.balloon.callback.RecyclerViewCallback;
import com.ray.balloon.presenter.BluetoothPresenter;
import com.ray.balloon.widget.DialogSelecteDevices;
import com.ray.balloon.widget.buttons.AnimatorUtils;
import com.ray.balloon.widget.buttons.ArcLayout;
import com.ray.balloon.widget.buttons.ClipRevealFrame;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import carbon.beta.TransitionLayout;
import carbon.widget.FloatingActionButton;
import carbon.widget.FrameLayout;
import carbon.widget.ImageView;
import carbon.widget.LinearLayout;
import carbon.widget.TextView;
import carbon.widget.Toolbar;

/**
 * Created by Administrator on 2016/3/3.
 */
public class BluetoothActivity extends BaseActivity<BluetoothView, BluetoothPresenter> implements BluetoothView, RecyclerViewCallback {
    private boolean isTurnOn = false;

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
    @Bind(R.id.tv_show_notice)
    TextView tv_show_notice;
    @Bind(R.id.v_centre)
    View v_centre;
    @Bind(R.id.menu_layout)
    ClipRevealFrame menuLayout;
    @Bind(R.id.arc_layout)
    ArcLayout arcLayout;
    @Bind(R.id.center_item)
    FloatingActionButton centerItem;

    @Bind(R.id.fab_1324)
    FloatingActionButton fab_1324;
    @Bind(R.id.fab_p2p)
    FloatingActionButton fab_p2p;
    @Bind(R.id.fab_plub)
    FloatingActionButton fab_plub;
    @Bind(R.id.fab_one_by_one)
    FloatingActionButton fab_one_by_one;

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
//                    dialog.show();
                    dialogSelecteDevices.dismiss();
                    tv_show_notice.setVisibility(View.GONE);
                    v_centre.setSelected(false);
                    onFabClick(v_centre);

                    toolbar.setText("蓝牙设备(已连接)");
                    break;
                case BluetoothPresenter.BLUETOOTH_IS_CONNECT_FAIL:
                    dialogSelecteDevices.setState(msg.what, clickDevice);
//                    dialog.dismiss();
                    v_centre.setSelected(true);
                    onFabClick(v_centre);
                    toolbar.setText("蓝牙设备(连接失败)");
                    break;
                case BluetoothPresenter.BLUETOOTH_IS_CONNECT_LOST:
                    dialogSelecteDevices.setState(msg.what, clickDevice);
//                    dialog.dismiss();
                    v_centre.setSelected(true);
                    onFabClick(v_centre);
                    toolbar.setText("蓝牙设备(断开)");
                    break;
                case BluetoothPresenter.BLUETOOTH_RECEIVE_MSG:
                    ToastMgr.show("蓝牙收到消息：" + msg.obj.toString());
//                    dialog.setText(msg.obj.toString());
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
//        dialog = new DialogFromBottom(this);
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

    @OnClick(R.id.fab_one_by_one)
    protected void balloonOneByOne() {
        sendMessage("[<>1]");
    }

    @OnClick(R.id.fab_p2p)
    protected void balloonP2P() {
        sendMessage("[<>2]");
    }

    @OnClick(R.id.fab_1324)
    protected void balloon1324() {
        sendMessage("[<>3]");
    }

    @OnClick(R.id.fab_plub)
    protected void balloonWanToKnow() {
        showToast("说明");
    }

    @OnClick(R.id.center_item)
    protected void balloonBoom() {
        sendMessage("[<>0]");
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

    private boolean isClosing = false;

    @Override
    public void onBackPressed() {
        if (powerMenu.getVisibility() == View.VISIBLE) {
            powerMenu.setVisibility(View.INVISIBLE);
            return;
        }
        if (v_centre.isSelected()) {
            onFabClick(v_centre);
            return;
        }
        isClosing = true;
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
        if (clickDevice != null && clickDevice.equals(device) && getPresenter().getState() == BluetoothPresenter.STATE_CONNECTED) {
            if (!v_centre.isSelected()) {
                v_centre.setSelected(false);
                onFabClick(v_centre);
            }
            dialogSelecteDevices.dismiss();
            return;
        }
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


    /**
     * 以下是view操作
     */


    private void onFabClick(View v) {
        int x = (v.getLeft() + v.getRight()) / 2;
        int y = (v.getTop() + v.getBottom()) / 2;
        float radiusOfFab = 1f * v.getWidth() / 2f;
        float radiusFromFabToRoot = (float) Math.hypot(
                Math.max(x, spl_back.getWidth() - x),
                Math.max(y, spl_back.getHeight() - y));

        if (v.isSelected()) {
            hideMenu(x, y, radiusFromFabToRoot, radiusOfFab);
        } else {
            showMenu(x, y, radiusOfFab, radiusFromFabToRoot);
        }
        v.setSelected(!v.isSelected());
    }

    private void showMenu(int cx, int cy, float startRadius, float endRadius) {
        menuLayout.setVisibility(View.VISIBLE);

        List<Animator> animList = new ArrayList<>();
        if (!isClosing) {
            Animator revealAnim = createCircularReveal(menuLayout, cx, cy, startRadius, endRadius);
            revealAnim.setInterpolator(new AccelerateDecelerateInterpolator());
            revealAnim.setDuration(200);

            animList.add(revealAnim);
            animList.add(createShowItemAnimator(centerItem));

            for (int i = 0, len = arcLayout.getChildCount(); i < len; i++) {
                animList.add(createShowItemAnimator(arcLayout.getChildAt(i)));
            }

            AnimatorSet animSet = new AnimatorSet();
            animSet.playSequentially(animList);

            animSet.start();
        }
    }

    private void hideMenu(int cx, int cy, float startRadius, float endRadius) {
        List<Animator> animList = new ArrayList<>();

        for (int i = arcLayout.getChildCount() - 1; i >= 0; i--) {
            animList.add(createHideItemAnimator(arcLayout.getChildAt(i)));
        }

        animList.add(createHideItemAnimator(centerItem));
        if (!isClosing) {
            Animator revealAnim = createCircularReveal(menuLayout, cx, cy, startRadius, endRadius);
            revealAnim.setInterpolator(new AccelerateDecelerateInterpolator());
            revealAnim.setDuration(200);
            revealAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    menuLayout.setVisibility(View.INVISIBLE);
                }
            });

            animList.add(revealAnim);

            AnimatorSet animSet = new AnimatorSet();
            animSet.playSequentially(animList);

            animSet.start();
        }

    }

    private Animator createShowItemAnimator(View item) {
        float dx = centerItem.getX() - item.getX();
        float dy = centerItem.getY() - item.getY();

        item.setScaleX(0f);
        item.setScaleY(0f);
        item.setTranslationX(dx);
        item.setTranslationY(dy);

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.scaleX(0f, 1f),
                AnimatorUtils.scaleY(0f, 1f),
                AnimatorUtils.translationX(dx, 0f),
                AnimatorUtils.translationY(dy, 0f)
        );

        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(50);
        return anim;
    }

    private Animator createHideItemAnimator(final View item) {
        final float dx = centerItem.getX() - item.getX();
        final float dy = centerItem.getY() - item.getY();

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.scaleX(1f, 0f),
                AnimatorUtils.scaleY(1f, 0f),
                AnimatorUtils.translationX(0f, dx),
                AnimatorUtils.translationY(0f, dy)
        );

        anim.setInterpolator(new DecelerateInterpolator());
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                item.setTranslationX(0f);
                item.setTranslationY(0f);
            }
        });
        anim.setDuration(50);
        return anim;
    }

    private Animator createCircularReveal(final ClipRevealFrame view, int x, int y, float startRadius,
                                          float endRadius) {

        final Animator reveal;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            reveal = ViewAnimationUtils.createCircularReveal(view, x, y, startRadius, endRadius);
        } else {
            view.setClipOutLines(true);
            view.setClipCenter(x, y);
            reveal = ObjectAnimator.ofFloat(view, "ClipRadius", startRadius, endRadius);
            reveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setClipOutLines(false);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        return reveal;
    }


}

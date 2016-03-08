package com.ray.balloon.view.bluetoothLE;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.corelibs.utils.DisplayUtil;

import java.util.List;
import java.util.UUID;

@SuppressLint("NewApi")
public class BluetoothLeService extends Service {

	private final static String TAG = "wanglei";

	private BluetoothManager mBluetoothManager;
	private BluetoothAdapter mBluetoothAdapter;
	private String mBluetoothDeviceAddress;
	private BluetoothGatt mBluetoothGatt;
	private int mConnectionState = STATE_DISCONNECTED;

	private static final int STATE_DISCONNECTED = 0;
	private static final int STATE_CONNECTING = 1;
	private static final int STATE_CONNECTED = 2;

	public final static String ACTION_GATT_CONNECTED = "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
	public final static String ACTION_GATT_DISCONNECTED = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
	public final static String ACTION_GATT_SERVICES_DISCOVERED = "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
	public final static String ACTION_DATA_AVAILABLE = "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
	public final static String EXTRA_DATA = "com.example.bluetooth.le.EXTRA_DATA";
	public static final UUID SERVIE_UUID = UUID
			.fromString("0000FFF0-0000-1000-8000-00805f9b34fb");
	public static final UUID RED_LIGHT_CONTROL_UUID = UUID
			.fromString("0000FFF4-0000-1000-8000-00805f9b34fb");
	public static final UUID RED_LIGHT_CONTROL_UUID_TWO = UUID
			.fromString("0000FFF1-0000-1000-8000-00805f9b34fb");
	public final static UUID UUID_HEART_RATE_MEASUREMENT = UUID
			.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT);

	private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
		@Override
		public void onConnectionStateChange(BluetoothGatt gatt, int status,
				int newState) {
			String intentAction;
			if (newState == BluetoothProfile.STATE_CONNECTED) {
				intentAction = ACTION_GATT_CONNECTED;
				mConnectionState = STATE_CONNECTED;
				broadcastUpdate(intentAction);
				Log.i(TAG, "Connected to GATT server.");
				Log.i(TAG, "Attempting to start service discovery:"
						+ mBluetoothGatt.discoverServices());

			} else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
				intentAction = ACTION_GATT_DISCONNECTED;
				mConnectionState = STATE_DISCONNECTED;
				Log.i(TAG, "Disconnected from GATT server.");
				broadcastUpdate(intentAction);
			}
		}

		@Override
		public void onServicesDiscovered(BluetoothGatt gatt, int status) {
			if (status == BluetoothGatt.GATT_SUCCESS) {
				broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
			} else {
				Log.w(TAG, "onServicesDiscovered received: " + status);
				System.out.println("onServicesDiscovered received: " + status);
			}
		}

		@Override
		public void onCharacteristicRead(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic, int status) {
			Log.i(TAG, "onCharacteristicRead");
			if (status == BluetoothGatt.GATT_SUCCESS) {
				broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
			}
		}

		/**
		 * 返回数据。
		 */
		@Override
		public void onCharacteristicChanged(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic) {
			broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
			// 数据
			Log.i(TAG, "characteristic.getValue():"
					+ characteristic.getValue().toString());
			String[] data = DisplayUtil.bytesToHexStrings(characteristic
					.getValue());
			for (int i = 0; i < data.length; i++) {
				System.out.print(data[i]);
			}
			Log.i(TAG, "返回读出的值");
		}
	};

	private void broadcastUpdate(final String action) {
		final Intent intent = new Intent(action);
		sendBroadcast(intent);
	}

	private void broadcastUpdate(final String action,
			final BluetoothGattCharacteristic characteristic) {
		final Intent intent = new Intent(action);

		if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
			int flag = characteristic.getProperties();
			int format = -1;
			if ((flag & 0x01) != 0) {
				format = BluetoothGattCharacteristic.FORMAT_UINT16;
				Log.d(TAG, "Heart rate format UINT16.");
			} else {
				format = BluetoothGattCharacteristic.FORMAT_UINT8;
				Log.d(TAG, "Heart rate format UINT8.");
			}
			final int heartRate = characteristic.getIntValue(format, 1);
			Log.d(TAG,
					"heartRate:"
							+ String.format("Received heart rate: %d",
									heartRate));
			intent.putExtra(EXTRA_DATA, String.valueOf(heartRate));
		} else {
			final byte[] data = characteristic.getValue();
			if (data != null && data.length > 0) {
				final StringBuilder stringBuilder = new StringBuilder(
						data.length);
				for (byte byteChar : data)
					stringBuilder.append(String.format("%02X ", byteChar));
				intent.putExtra(EXTRA_DATA, new String(data) + "\n"
						+ stringBuilder.toString());
			}
		}
		sendBroadcast(intent);
	}

	public class LocalBinder extends Binder {
		BluetoothLeService getService() {
			return BluetoothLeService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		close();
		return super.onUnbind(intent);
	}

	private final IBinder mBinder = new LocalBinder();

	public boolean initialize() {
		if (mBluetoothManager == null) {
			mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
			if (mBluetoothManager == null) {
				Log.e(TAG, "Unable to initialize BluetoothManager.");
				return false;
			}
		}

		mBluetoothAdapter = mBluetoothManager.getAdapter();
		if (mBluetoothAdapter == null) {
			Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
			return false;
		}

		return true;
	}

	public boolean connect(final String address) {
		if (mBluetoothAdapter == null || address == null) {
			Log.w(TAG, "不能初始化未指明地址的");
			return false;
		}

		if (mBluetoothDeviceAddress != null
				&& address.equals(mBluetoothDeviceAddress)
				&& mBluetoothGatt != null) {
			Log.d(TAG,
					"Trying to use an existing mBluetoothGatt for connection.");
			if (mBluetoothGatt.connect()) {
				mConnectionState = STATE_CONNECTING;
				return true;
			} else {
				return false;
			}
		}

		final BluetoothDevice device = mBluetoothAdapter
				.getRemoteDevice(address);
		if (device == null) {
			Log.w(TAG, "设备没找到，不能 链接");
			return false;
		}
		mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
		Log.d(TAG, "Trying to create a new connection.");
		mBluetoothDeviceAddress = address;
		mConnectionState = STATE_CONNECTING;
		System.out.println("device.getBondState==" + device.getBondState());
		return true;
	}

	public void disconnect() {
		if (mBluetoothAdapter == null || mBluetoothGatt == null) {
			Log.w(TAG, "BluetoothAdapter not initialized");
			return;
		}
		mBluetoothGatt.disconnect();
	}

	public void close() {
		if (mBluetoothGatt == null) {
			return;
		}
		mBluetoothGatt.close();
		mBluetoothGatt = null;
	}

	public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
		if (mBluetoothAdapter == null || mBluetoothGatt == null) {
			Log.w(TAG, "BluetoothAdapter not initialized");
			return;
		}
		mBluetoothGatt.readCharacteristic(characteristic);
	}

	public void setCharacteristicNotification(
			BluetoothGattCharacteristic characteristic, boolean enabled) {
		if (mBluetoothAdapter == null || mBluetoothGatt == null) {
			Log.w(TAG, "BluetoothAdapter not initialized");
			return;
		}
		mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
		/**
		 * 打开数据FFF4
		 */
		// This is specific to Heart Rate Measurement.
		if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
			BluetoothGattDescriptor descriptor = characteristic
					.getDescriptor(UUID
							.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
			descriptor
					.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
			mBluetoothGatt.writeDescriptor(descriptor);
		}
	}

	public List<BluetoothGattService> getSupportedGattServices() {
		if (mBluetoothGatt == null)
			return null;

		return mBluetoothGatt.getServices();
	}

	public void writeLlsAlertLevel(int iAlertLevel, byte[] bb) {

		BluetoothGattService linkLossService = mBluetoothGatt
				.getService(SERVIE_UUID);
		if (linkLossService == null) {
			showMessage("link loss Alert service not found!");
			return;
		}
		BluetoothGattCharacteristic alertLevel = null;
		switch (iAlertLevel) {
		case 1: // red
			alertLevel = linkLossService
					.getCharacteristic(RED_LIGHT_CONTROL_UUID);
			break;
		case 2:
			alertLevel = linkLossService
					.getCharacteristic(RED_LIGHT_CONTROL_UUID_TWO);
			break;
		}
		if (alertLevel == null) {
			showMessage("link loss Alert Level charateristic not found!");
			return;
		}
		boolean status = false;
		int storedLevel = alertLevel.getWriteType();
		Log.d(TAG, "storedLevel() - storedLevel=" + storedLevel);

		alertLevel.setValue(bb);
		Log.e(TAG, "发送的指令:bb" + bb[0]);

		alertLevel
				.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
		status = mBluetoothGatt.writeCharacteristic(alertLevel);
		Log.d(TAG, "writeLlsAlertLevel() - status=" + status);
	}

	private void showMessage(String msg) {
		Log.e(TAG, msg);
	}
}

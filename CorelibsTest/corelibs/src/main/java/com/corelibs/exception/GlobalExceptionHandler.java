package com.corelibs.exception;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.corelibs.common.AppManager;

public class GlobalExceptionHandler implements UncaughtExceptionHandler {

	public static final String TAG = "GlobalExceptionHandler";
	public static String crash_file_dir_path;

	private Context mContext;
	private UncaughtExceptionHandler mDefaultHandler;
	private static GlobalExceptionHandler instance = new GlobalExceptionHandler();
	public String appName;
	private StringBuffer outputSb;
	private Map<String, String> infos = new HashMap<String, String>();
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA);

	private GlobalExceptionHandler() {}

	public static GlobalExceptionHandler getInstance() {
		return instance;
	}

	public void init(Context context, String appName) {
		init(context);
		this.appName = appName;
	}
	
	public void init(Context context) {
		mContext = context;
		crash_file_dir_path = mContext.getExternalCacheDir() + "/logs/";
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(final Thread thread, final Throwable ex) {
		ex.printStackTrace();
		if (!handleException(ex) && mDefaultHandler != null) {
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			Intent intent = new Intent(mContext, ExceptionDialogActivity.class);
			intent.putExtra(ExceptionDialogActivity.APP_NAME, appName);
			intent.putExtra(ExceptionDialogActivity.OUTPUT_INFO, outputSb.toString());
			PendingIntent restartIntent = PendingIntent.getActivity(mContext, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
			AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
			mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 50, restartIntent); // 1秒钟后重启应用

			AppManager.getAppManager().finishAllActivity();
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}

	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}
		ex.printStackTrace();
		collectDeviceInfo(mContext);
		saveCrashInfo2File(ex);
		return true;
	}

	/**
	 * 收集设备参数信息
	 */
	public void collectDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null" : pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "an error occured when collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				Log.d(TAG, field.getName() + " : " + field.get(null));
			} catch (Exception e) {
				Log.e(TAG, "an error occured when collect crash info", e);
			}
		}
	}

	/**
	 * 保存错误信息到文件中
	 * 
	 * @param ex
	 * @return 返回文件名称,便于将文件传送到服务器
	 */
	private String saveCrashInfo2File(Throwable ex) {
		long timestamp = System.currentTimeMillis();
		String time = formatter.format(new Date());
		String fileName = "crashlog-" + time + "-" + timestamp + ".log";
		
		outputSb = new StringBuffer();
		
		outputSb.append(crash_file_dir_path + fileName + "\n");
		outputSb.append("\n\n");
		
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		outputSb.append(result);
		
		outputSb.append("\n\n");
		
		for (Map.Entry<String, String> entry : infos.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			outputSb.append(key + "=" + value + "\n");
		}
		
		try {
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				File dir = new File(crash_file_dir_path);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(crash_file_dir_path + fileName);
				fos.write(outputSb.toString().getBytes());
				fos.close();
			}
			return fileName;
		} catch (Exception e) {
			Log.e(TAG, "an error occured while writing file...", e);
		}
		return null;
	}
}

package com.ray.balloon;

import android.app.Application;
import android.os.Build;
import android.os.Environment;

import com.corelibs.api.RetrofitFactory;
import com.corelibs.common.Configuration;
import com.corelibs.exception.GlobalExceptionHandler;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.ray.balloon.authority.AuthorityContext;
import com.ray.balloon.authority.LoggedIn;
import com.ray.balloon.constants.Urls;
import com.ray.balloon.model.bean.User;
import com.ray.balloon.tools.image.fresco.ImagePipelineConfigFactory;

public class App extends Application {

    static App instance;

    public App() {
        instance = this;
    }

    public static synchronized App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        GlobalExceptionHandler.getInstance().init(this, getResources().getString(R.string.app_name));
        ToastMgr.init(getApplicationContext());
        RetrofitFactory.setBaseUrl(Urls.ROOT);
        PreferencesHelper.init(getApplicationContext());
        Configuration.enableLoggingNetworkParams();
        Fresco.initialize(this, ImagePipelineConfigFactory.getOkHttpImagePipelineConfig(this));
        checkLoginStatus();
    }

    private void checkLoginStatus() {
        if (PreferencesHelper.getData(User.class) != null) {
            AuthorityContext.getContext().setAuthority(new LoggedIn());
        }
    }

    public String getDiskCacheDir() {
        String cachePath = null;
        if (sdCardIsAvailable()) {
            // 内存卡可用时，数据放在/sdcard/Android/Data/<application package>/cache中
            if (hasExternalCacheDir()) {
                // 2.2版本才有将应用缓存转移到sd卡的功能
                cachePath = getExternalCacheDir().getPath();
            } else {
                // 2.2以前我们需要自己构造
                final String cacheDir = "/Android/Data/" + getPackageName()
                        + "/cache/";
                cachePath = Environment.getExternalStorageDirectory().getPath()
                        + cacheDir;
            }
        } else {
            // SD卡不可用时，数据放在data/Data/<application package>/cache中
            cachePath = getCacheDir().getPath();
        }
        return cachePath;
    }

    private boolean hasExternalCacheDir() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    /**
     * 检测sdcard是否可用
     *
     * @return true为可用，否则为不可用
     */
    public static boolean sdCardIsAvailable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return true;
        }
        return false;
    }
}

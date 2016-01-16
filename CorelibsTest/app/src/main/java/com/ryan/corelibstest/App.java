package com.ryan.corelibstest;

import android.app.Application;

import com.corelibs.api.RetrofitFactory;
import com.corelibs.common.Configuration;
import com.corelibs.utils.ToastMgr;
import com.ryan.corelibstest.constants.Urls;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        //GlobalExceptionHandler.getInstance().init(this, getResources().getString(R.string.app_name));
        ToastMgr.init(this);
        RetrofitFactory.setBaseUrl(Urls.ROOT);
        Configuration.enableLoggingNetworkParams();
    }
}

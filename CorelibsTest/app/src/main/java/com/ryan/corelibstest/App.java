package com.ryan.corelibstest;

import android.app.Application;

import com.corelibs.api.RetrofitFactory;
import com.corelibs.common.Configuration;
import com.corelibs.exception.GlobalExceptionHandler;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.ryan.corelibstest.authority.AuthorityContext;
import com.ryan.corelibstest.authority.LoggedIn;
import com.ryan.corelibstest.constants.Urls;
import com.ryan.corelibstest.model.bean.User;

public class App extends Application {

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

        checkLoginStatus();
    }

    private void checkLoginStatus() {
        if (PreferencesHelper.getData(User.class) != null) {
            AuthorityContext.getContext().setAuthority(new LoggedIn());
        }
    }
}

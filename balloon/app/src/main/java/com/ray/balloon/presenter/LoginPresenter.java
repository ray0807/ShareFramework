package com.ray.balloon.presenter;

import com.corelibs.api.ManagerFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BaseRxPresenter;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.Tools;
import com.ray.balloon.R;
import com.ray.balloon.authority.AuthorityContext;
import com.ray.balloon.authority.LoggedIn;
import com.ray.balloon.model.bean.BaseData;
import com.ray.balloon.model.manager.LoginManager;
import com.ray.balloon.subcriber.ResponseSubscriber;
import com.ray.balloon.view.login.LoginView;

/**
 * 登录逻辑
 *
 * Created by Ryan on 2015/12/29.
 */
public class LoginPresenter extends BaseRxPresenter<LoginView> {
    private LoginManager manager;

    @Override
    protected void onViewAttached() {
        manager = ManagerFactory.getFactory().getManager(LoginManager.class);
    }

    public void login(final String username, final String password) {
        if (!isUserInputValidate(username, password)) return;

        getView().showLoadingDialog();
        manager.login(username, password)
                .compose(new ResponseTransformer<>(this.<BaseData>bindLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData>(getView()) {
                    @Override
                    public void success(BaseData baseData) {
                        PreferencesHelper.saveData(baseData.Data.CurrentAccount);
                        AuthorityContext.getContext().setAuthority(new LoggedIn());
                        getView().loginSuccess();
                    }

                    @Override
                    public void operationError(BaseData baseData, int status, String message) {
                        getView().showToastMessage(message);
                    }
                });

    }

    private boolean isUserInputValidate(String username, String password) {
        if (!Tools.validatePhone(username)) {
            getView().showToastMessage(getString(R.string.login_username_no_invalidate));
            return false;
        }

        if (stringIsNull(password) || password.length() < 6 || password.length() > 16) {
            getView().showToastMessage(getString(R.string.login_password_invalid));
            return false;
        }

        return true;
    }

}

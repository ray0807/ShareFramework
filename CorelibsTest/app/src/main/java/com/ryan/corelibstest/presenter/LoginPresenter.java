package com.ryan.corelibstest.presenter;

import com.corelibs.api.ManagerFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BaseRxPresenter;
import com.corelibs.utils.PreferencesHelper;
import com.ryan.corelibstest.R;
import com.ryan.corelibstest.subcriber.ResponseSubscriber;
import com.ryan.corelibstest.authority.AuthorityContext;
import com.ryan.corelibstest.authority.LoggedIn;
import com.ryan.corelibstest.view.entry.LoginView;
import com.ryan.corelibstest.model.bean.BaseData;
import com.ryan.corelibstest.model.manager.EntryManager;

/**
 * 登录逻辑
 *
 * Created by Ryan on 2015/12/29.
 */
public class LoginPresenter extends BaseRxPresenter<LoginView> {
    private EntryManager manager;

    @Override
    protected void onViewAttached() {
        manager = ManagerFactory.getFactory().getManager(EntryManager.class);
    }

    public void login(final String username, final String password) {
        if (!isUserInputValidate(username, password)) return;

        getView().showLoadingDialog();
        manager.login(username, password)
                .compose(new ResponseTransformer<>(this.<BaseData>bindLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData>(getView()) {
                    @Override
                    public void success(BaseData baseData) {
                        PreferencesHelper.saveData(baseData.data.member);
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
        if (stringIsNull(username)) {
            getView().showToastMessage(getString(R.string.login_username_empty));
            return false;
        }

        if (stringIsNull(password) || password.length() < 6 || password.length() > 16) {
            getView().showToastMessage(getString(R.string.login_password_invalid));
            return false;
        }

        return true;
    }

}

package com.ryan.corelibstest.presenter;

import com.corelibs.base.BaseRxPresenter;
import com.corelibs.base.BaseView;
import com.ryan.corelibstest.R;
import com.ryan.corelibstest.authority.AuthorityContext;

/**
 * Created by Ryan on 2016/1/4.
 */
public class MainPresenter extends BaseRxPresenter<BaseView> {
    public boolean isAuthorized(String tabId) {
        if (tabId.equals(getString(R.string.cart))) {
            return AuthorityContext.getContext().showShoppingCart(view.getViewContext());
        } else if (tabId.equals(getString(R.string.mine))) {
            return AuthorityContext.getContext().showPersonCenter(view.getViewContext());
        }

        return true;
    }
}

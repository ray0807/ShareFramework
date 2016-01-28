package com.ryan.corelibstest.view.mine;

import com.corelibs.base.BaseView;
import com.ryan.corelibstest.model.bean.User;

/**
 * Created by Ryan on 2016/1/6.
 */
public interface MineView extends BaseView {
    void showUserData(User user);
}

package com.ryan.corelibstest.presenter;

import com.corelibs.api.ManagerFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.PreferencesHelper;
import com.ryan.corelibstest.subcriber.ResponseSubscriber;
import com.ryan.corelibstest.model.bean.BaseData;
import com.ryan.corelibstest.model.bean.User;
import com.ryan.corelibstest.model.manager.ImageUploadManager;
import com.ryan.corelibstest.model.manager.UserManager;
import com.ryan.corelibstest.view.mine.MineView;

import java.io.File;

/**
 * Created by Ryan on 2016/1/6.
 */
public class MinePresenter extends BasePresenter<MineView> {

    private UserManager manager;
    private ImageUploadManager uploadManager;

    @Override
    protected void onViewAttached() {
        manager = ManagerFactory.getFactory().getManager(UserManager.class);
        uploadManager = new ImageUploadManager();
    }

    public void getLoginUser() {
        User user = PreferencesHelper.getData(User.class);
        if(user != null) view.showUserData(user);
    }

    public void uploadAvatar(File avatar) {
        final User user = PreferencesHelper.getData(User.class);

        view.showLoadingDialog();
        uploadManager.uploadAvatar(user.id, avatar)
                .compose(new ResponseTransformer<BaseData>())
                .subscribe(new ResponseSubscriber<BaseData>(view) {
                    @Override
                    public void success(BaseData baseData) {
                        if (baseData != null && baseData.data != null) {
                            user.icon = baseData.data.icon;
                            PreferencesHelper.saveData(user);
                            view.showUserData(user);
                        }
                    }

                    @Override
                    public void operationError(BaseData baseData, int status, String message) {
                        view.showToastMessage(message);
                    }
                });
    }
}

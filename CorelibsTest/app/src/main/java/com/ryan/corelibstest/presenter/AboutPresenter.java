package com.ryan.corelibstest.presenter;

import com.corelibs.api.ManagerFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.ryan.corelibstest.subcriber.ResponseSubscriber;
import com.ryan.corelibstest.model.bean.BaseData;
import com.ryan.corelibstest.model.manager.AboutUsManager;
import com.ryan.corelibstest.view.about.AboutView;

/**
 * Created by Ryan on 2016/1/8.
 */
public class AboutPresenter extends BasePresenter<AboutView> {

    private AboutUsManager manager;

    @Override
    protected void onViewAttached() {
        manager = ManagerFactory.getFactory().getManager(AboutUsManager.class);
    }

    public void getAboutUs() {
        manager.getAboutUs().compose(new ResponseTransformer<BaseData>())
                .subscribe(new ResponseSubscriber<BaseData>() {
                    @Override
                    public void success(BaseData baseData) {
                        view.loadUrl(baseData.data.form.src);
                    }
                });
    }
}

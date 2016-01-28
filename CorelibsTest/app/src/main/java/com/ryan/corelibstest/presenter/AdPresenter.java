package com.ryan.corelibstest.presenter;

import com.corelibs.api.ManagerFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.ryan.corelibstest.subcriber.ResponseSubscriber;
import com.ryan.corelibstest.model.bean.BaseData;
import com.ryan.corelibstest.model.bean.RecommendAd;
import com.ryan.corelibstest.model.manager.AdManager;
import com.ryan.corelibstest.view.main.AdView;

/**
 * Created by Ryan on 2016/1/6.
 */
public class AdPresenter extends BasePresenter<AdView> {

    private AdManager manager;

    @Override
    protected void onViewAttached() {
        manager = ManagerFactory.getFactory().getManager(AdManager.class);
    }

    public void getAds() {
        manager.getAds().compose(new ResponseTransformer<BaseData>())
                .subscribe(new ResponseSubscriber<BaseData>() {
                    @Override
                    public void success(BaseData baseData) {
                        if (baseData.data.adverList != null)
                            getView().renderAds(baseData.data.adverList);
                    }
                });
    }

    public void toProductDetail(RecommendAd ad) {
//        if (ad.id < 0) {
//
//        }
    }
}

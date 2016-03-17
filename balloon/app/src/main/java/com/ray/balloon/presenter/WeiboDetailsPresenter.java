package com.ray.balloon.presenter;

import com.corelibs.api.ManagerFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BaseRxPresenter;
import com.ray.balloon.model.bean.BaseData;
import com.ray.balloon.model.manager.WeiboManager;
import com.ray.balloon.subcriber.ResponseSubscriber;
import com.ray.balloon.view.weibodetail.DetailsView;

/**
 * Created by Administrator on 2016/3/1.
 */
public class WeiboDetailsPresenter extends BaseRxPresenter<DetailsView> {
    private WeiboManager manager;

    @Override
    protected void onViewAttached() {
        manager= ManagerFactory.getFactory().getManager(WeiboManager.class);
    }
    public void getHomePageData(String pageNum,String PageSize){
        getView().showLoadingDialog();
        manager.getData(PageSize,pageNum).compose(new ResponseTransformer<>(this.<BaseData>bindLifeCycle())).subscribe(new ResponseSubscriber<BaseData>(getView()){
            @Override
            public void success(BaseData baseData) {
                super.success(baseData);
            }

            @Override
            public void operationError(BaseData baseData, int status, String message) {
                super.operationError(baseData, status, message);
            }
        });
    }
}

package com.ryan.corelibstest.presenter;

import com.corelibs.api.ManagerFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePaginationPresenter;
import com.ryan.corelibstest.subcriber.ResponseSubscriber;
import com.ryan.corelibstest.model.bean.BaseData;
import com.ryan.corelibstest.model.manager.HomepageManager;
import com.ryan.corelibstest.view.main.HomeView;

/**
 * Created by Ryan on 2016/1/6.
 */
public class HomePresenter extends BasePaginationPresenter<HomeView> {

    private HomepageManager manager;

    @Override
    protected void onViewAttached() {
        manager = ManagerFactory.getFactory().getManager(HomepageManager.class);
    }

    public void getTypes() {
        manager.getTypes().compose(new ResponseTransformer<BaseData>())
                .subscribe(new ResponseSubscriber<BaseData>() {
                    @Override
                    public void success(BaseData baseData) {
                        if(baseData.data.hotCategoryList != null &&
                                baseData.data.brandList != null &&
                                baseData.data.allCategories != null)
                            view.renderTypes(baseData.data.hotCategoryList,
                                    baseData.data.allCategories, baseData.data.brandList);
                    }

                    @Override
                    public void operationError(BaseData baseData, int status, String message) {
                        view.showToastMessage(message);
                    }
                });
    }

    public void getTopProducts(final boolean reload) {
        if (!doPagination(reload)) return;
        manager.getTopProducts(getPageNo(), getPageSize())
                .compose(new ResponseTransformer<BaseData>())
                .subscribe(new ResponseSubscriber<BaseData>(getView()) {
                    @Override
                    public void success(BaseData baseData) {
                        view.onLoadingCompleted(reload);
                        if (baseData.data.list != null)
                            view.renderProducts(reload, baseData.data.list);
                        setPageCount(baseData.page.pageCount);
                    }

                    @Override
                    public void operationError(BaseData baseData, int status, String message) {
                        view.onLoadingCompleted(reload);
                        view.showToastMessage(message);
                    }

                    @Override
                    public void error(Throwable e) {
                        super.error(e);
                        view.onLoadingCompleted(reload);
                    }
                });
    }
}

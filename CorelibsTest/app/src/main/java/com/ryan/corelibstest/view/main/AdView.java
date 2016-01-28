package com.ryan.corelibstest.view.main;

import com.corelibs.base.BaseView;
import com.ryan.corelibstest.model.bean.RecommendAd;

import java.util.List;

/**
 * Created by Ryan on 2016/1/6.
 */
public interface AdView extends BaseView {
    void renderAds(List<RecommendAd> ads);
}

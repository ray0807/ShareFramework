package com.ryan.corelibstest.view.main;

import com.corelibs.base.BasePaginationView;
import com.corelibs.base.BaseView;
import com.ryan.corelibstest.model.bean.Brand;
import com.ryan.corelibstest.model.bean.Category;
import com.ryan.corelibstest.model.bean.Product;

import java.util.List;

/**
 * Created by Ryan on 2016/1/6.
 */
public interface HomeView extends BasePaginationView {
    void renderTypes(List<Category> hotCategories, List<Category> categories, List<Brand> brands);
    void renderProducts(boolean reload, List<Product> products);
}

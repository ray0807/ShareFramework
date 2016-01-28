package com.ryan.corelibstest.adapter;

import android.content.Context;

import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.QuickAdapter;
import com.ryan.corelibstest.R;
import com.ryan.corelibstest.model.bean.Product;

import java.util.List;

/**
 * Created by Ryan on 2016/1/25.
 */
public class HomeQuickAdapter extends QuickAdapter<Product> {

    public HomeQuickAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public HomeQuickAdapter(Context context, int layoutResId, List<Product> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, Product item) {
        helper.setText(R.id.tv_prod_name, item.title)
                .setText(R.id.tv_prod_price, String.format(context.getResources()
                        .getString(R.string.price), item.price))
                .setImageUrl(R.id.iv_prodImage, item.logo);
    }
}

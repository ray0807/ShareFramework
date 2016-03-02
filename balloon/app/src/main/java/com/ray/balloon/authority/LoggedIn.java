package com.ray.balloon.authority;

import android.content.Context;

import com.ray.balloon.model.bean.OrderItem;

import java.util.List;

/**
 * 登录后的权限
 * Created by Ryan on 2016/1/6.
 */
public class LoggedIn implements Authority {

    @Override
    public boolean showShoppingCart(Context context) {
        return true;
    }

    @Override
    public boolean showPersonCenter(Context context) {
        return true;
    }

    @Override
    public boolean addToShoppingCart(Context context, OrderItem item) {
        return false;
    }

    @Override
    public boolean buyProducts(Context context, List<OrderItem> items) {
        return false;
    }
}

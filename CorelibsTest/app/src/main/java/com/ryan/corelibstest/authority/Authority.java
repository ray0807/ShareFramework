package com.ryan.corelibstest.authority;

import android.content.Context;

import com.ryan.corelibstest.model.bean.OrderItem;

import java.util.List;

/**
 * Created by Ryan on 2016/1/6.
 */
public interface Authority {
    boolean showShoppingCart(Context context);
    boolean showPersonCenter(Context context);
    boolean addToShoppingCart(Context context, OrderItem item);
    boolean buyProducts(Context context, List<OrderItem> items);
}

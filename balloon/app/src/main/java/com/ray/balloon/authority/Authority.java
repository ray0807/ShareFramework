package com.ray.balloon.authority;

import android.content.Context;

import com.ray.balloon.model.bean.OrderItem;

import java.util.List;
public interface Authority {
    boolean showShoppingCart(Context context);
    boolean showPersonCenter(Context context);
    boolean addToShoppingCart(Context context, OrderItem item);
    boolean buyProducts(Context context, List<OrderItem> items);
}

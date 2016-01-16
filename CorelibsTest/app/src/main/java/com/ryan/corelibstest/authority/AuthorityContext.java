package com.ryan.corelibstest.authority;

import android.content.Context;

import com.ryan.corelibstest.model.bean.OrderItem;

import java.util.List;

/**
 * Created by Ryan on 2016/1/6.
 */
public class AuthorityContext {
    private static AuthorityContext context = new AuthorityContext();
    private Authority authority = new LoggedOut();

    private AuthorityContext() {}

    public static AuthorityContext getContext() {
        return context;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public boolean showShoppingCart(Context context) {
        return authority.showShoppingCart(context);
    }

    public boolean showPersonCenter(Context context) {
        return authority.showPersonCenter(context);
    }

    public boolean addToShoppingCart(Context context, OrderItem item) {
        return authority.addToShoppingCart(context, item);
    }

    public boolean buyProducts(Context context, List<OrderItem> items) {
        return authority.buyProducts(context, items);
    }
}

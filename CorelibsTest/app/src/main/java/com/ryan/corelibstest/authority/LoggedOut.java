package com.ryan.corelibstest.authority;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.ryan.corelibstest.model.bean.OrderItem;
import com.ryan.corelibstest.view.entry.LoginActivity;
import com.ryan.corelibstest.widget.CommonDialog;

import java.util.List;

/**
 * Created by Ryan on 2016/1/6.
 */
public class LoggedOut implements Authority {
    @Override
    public boolean showShoppingCart(Context context) {
        showLoginDialog(context);
        return false;
    }

    @Override
    public boolean showPersonCenter(Context context) {
        showLoginDialog(context);
        return false;
    }

    @Override
    public boolean addToShoppingCart(Context context, OrderItem item) {
        return false;
    }

    @Override
    public boolean buyProducts(Context context, List<OrderItem> items) {
        return false;
    }

    private void showLoginDialog(final Context context) {
        final CommonDialog dialog = new CommonDialog(context, "提示", "您当前还未登录, 是否登录?", 2);
        dialog.show();
        dialog.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }
        });
    }
}

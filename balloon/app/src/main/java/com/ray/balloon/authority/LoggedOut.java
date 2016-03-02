package com.ray.balloon.authority;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.ray.balloon.R;
import com.ray.balloon.model.bean.OrderItem;
import com.ray.balloon.view.login.LoginActivity;
import com.ray.balloon.widget.CommonDialog;

import java.util.List;

/**
 * 未登录的权限
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
        final CommonDialog dialog = new CommonDialog(context, context.getResources().getString(R.string.notice), context.getResources().getString(R.string.notice_dialog), 2);
        dialog.show();
        dialog.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                context.startActivity(new Intent(context, LoginActivity.class));
            }
        });
    }
}

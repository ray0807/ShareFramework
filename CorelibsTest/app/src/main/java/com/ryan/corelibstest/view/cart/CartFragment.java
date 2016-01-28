package com.ryan.corelibstest.view.cart;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.corelibs.base.BaseFragment;
import com.ryan.corelibstest.R;
import com.ryan.corelibstest.presenter.CartPresenter;

/**
 * Created by Ryan on 2016/1/5.
 */
public class CartFragment extends BaseFragment<CartView, CartPresenter> {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cart;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected CartPresenter createPresenter() {
        return null;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("fragment", "onPause3");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("fragment", "onResume3");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("fragment", "onAttach3");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("fragment", "onDetach3");
    }

}

package com.ryan.corelibstest.view.mine;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryan.corelibstest.BaseFragment;
import com.ryan.corelibstest.R;
import com.ryan.corelibstest.presenter.MinePresenter;

/**
 * Created by Ryan on 2016/1/5.
 */
public class MineFragment extends BaseFragment<MineView, MinePresenter> {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected MinePresenter createPresenter() {
        return null;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("fragment", "onPause4");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("fragment", "onResume4");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("fragment", "onAttach4");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("fragment", "onDetach4");
    }
}

package com.ryan.corelibstest.view.about;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.corelibs.base.BasePresenter;
import com.ryan.corelibstest.BaseFragment;
import com.ryan.corelibstest.R;

/**
 * Created by Ryan on 2016/1/5.
 */
public class AboutFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("fragment", "onPause2");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("fragment", "onResume2");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("fragment", "onAttach2");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("fragment", "onDetach2");
    }
}

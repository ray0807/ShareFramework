package com.ryan.corelibstest.view.mine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.corelibs.base.BaseFragment;
import com.ryan.corelibstest.ImagePickHelper;
import com.ryan.corelibstest.R;
import com.ryan.corelibstest.model.bean.User;
import com.ryan.corelibstest.presenter.MinePresenter;
import com.ryan.corelibstest.widget.NavigationBar;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Ryan on 2016/1/5.
 */
public class MineFragment extends BaseFragment<MineView, MinePresenter> implements MineView {

    @Bind(R.id.nav) NavigationBar nav;
    @Bind(R.id.iv_icon) ImageView ivIcon;
    @Bind(R.id.tv_name) TextView tvName;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.hideBackButton();
        nav.setTitle(getString(R.string.mine));
        presenter.getLoginUser();
    }

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void showUserData(User user) {
        tvName.setText(user.phone);
        if (user.icon != null && user.icon.length() > 0)
            Picasso.with(getViewContext()).load(user.icon).into(ivIcon);
    }

    @OnClick(R.id.iv_icon)
    public void showImagePicker() {
        ImagePickHelper.showPickerDialog(getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        File avatar = null;
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ImagePickHelper.REQUEST_CAMERA:
                    Bitmap bitmap = BitmapFactory.decodeFile(
                            data.getStringExtra(ImagePickHelper.EXTRA_CAMERA_FILE_PATH));
                    try {
                        avatar = ImagePickHelper.bitmapTofile(ImagePickHelper.compressBitmap(bitmap));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case ImagePickHelper.REQUEST_GALLERY:
                    Uri uri = data.getData();
                    try {
                        avatar = ImagePickHelper.bitmapTofile(ImagePickHelper.compressBitmap(
                                ImagePickHelper.getBitmapFromUri(getActivity(), uri)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }

            presenter.uploadAvatar(avatar);

        }
    }

}

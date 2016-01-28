package com.ryan.corelibstest.view.about;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.corelibs.base.BaseFragment;
import com.ryan.corelibstest.R;
import com.ryan.corelibstest.presenter.AboutPresenter;
import com.ryan.corelibstest.widget.NavigationBar;

import butterknife.Bind;

/**
 * Created by Ryan on 2016/1/5.
 */
public class AboutFragment extends BaseFragment<AboutView, AboutPresenter> implements AboutView {

    @Bind(R.id.nav) NavigationBar nav;
    @Bind(R.id.web) WebView web;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.hideBackButton();
        nav.setTitle(R.string.about);
        initWebView();
        presenter.getAboutUs();
    }

    @Override
    protected AboutPresenter createPresenter() {
        return new AboutPresenter();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        web.getSettings().setSupportMultipleWindows(true);
        web.getSettings().setAppCacheEnabled(true);
        web.getSettings().setDatabaseEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onReceivedSslError(WebView view,
                                           android.webkit.SslErrorHandler handler,
                                           android.net.http.SslError error) {
                handler.proceed();
            }
        });
    }

    @Override
    public void loadUrl(String url) {
        web.loadUrl(url);
    }
}

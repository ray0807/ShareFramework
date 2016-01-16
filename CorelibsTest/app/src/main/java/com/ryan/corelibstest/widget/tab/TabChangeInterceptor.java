package com.ryan.corelibstest.widget.tab;

/**
 * Created by Ryan on 2016/1/6.
 */
public interface TabChangeInterceptor {
    boolean canTab(String tabId);
    void onTabIntercepted(String tabId);
}

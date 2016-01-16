package com.ryan.corelibstest.widget.tab;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TabHost;

import com.ryan.corelibstest.widget.tab.FragmentTabHost;

/**
 * Created by Ryan on 2016/1/5.
 */
public class TabNavigator {

    private TabNavigatorContent content;
    private FragmentTabHost host;

    public void setup(Context context, FragmentTabHost tabHost, TabNavigatorContent content,
                      FragmentManager manager, int containerId) {
        this.host = tabHost;
        this.content = content;
        host.setup(context, manager, containerId);
        init();
    }

    public void setTabChangeInterceptor(TabChangeInterceptor interceptor) {
        host.setTabChangeInterceptor(interceptor);
    }

    private void init() {
        Class[] fragmentClasses = content.getFragmentClasses();
        String[] tabTags = content.getTabTags();

        int tabCount = fragmentClasses.length;

        for (int i = 0; i < tabCount; i++) {
            host.addTab(host.newTabSpec(tabTags[i]).setIndicator(content.getTabView(i)),
                    fragmentClasses[i], content.getArgs(i));
        }
    }


    public interface TabNavigatorContent {
        View getTabView(int position);
        Bundle getArgs(int position);
        Class[] getFragmentClasses();
        String[] getTabTags();
    }

}

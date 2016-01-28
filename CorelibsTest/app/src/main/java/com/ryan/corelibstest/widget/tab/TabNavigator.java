package com.ryan.corelibstest.widget.tab;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

/**
 * 用于Fragment tab页切换
 * <BR/>
 * Created by Ryan on 2016/1/5.
 */
public class TabNavigator {

    private TabNavigatorContent content;
    private InterceptedFragmentTabHost host;

    public void setup(Context context, InterceptedFragmentTabHost tabHost, TabNavigatorContent content,
                      FragmentManager manager, int containerId) {
        this.host = tabHost;
        this.content = content;
        host.setup(context, manager, containerId);
        init();
    }

    /**
     * 设置权限拦截
     */
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

    /**
     * Fragment tab页切换需实现此接口, 来获取tab页的必要信息
     */
    public interface TabNavigatorContent {
        /**
         * 根据position获取每个tab标签视图
         * @param position tab标签位置
         * @return tab标签视图
         */
        View getTabView(int position);

        /**
         * 根据position获取切换至目标Fragment要传递的数据Bundle
         * @param position 目标Fragment位置
         * @return 数据Bundle
         */
        Bundle getArgs(int position);

        /**
         * 获取Fragment的类对象数组
         * @return Fragment的类对象数组
         */
        Class[] getFragmentClasses();

        /**
         * 获取每个Fragment的tag
         * @return Fragment的tag数组
         */
        String[] getTabTags();
    }

}

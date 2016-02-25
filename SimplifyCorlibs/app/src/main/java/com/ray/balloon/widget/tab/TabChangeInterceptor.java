package com.ray.balloon.widget.tab;

/**
 * 用于{@link InterceptedFragmentTabHost}权限控制
 * <BR/>
 * Created by Ryan on 2016/1/6.
 */
public interface TabChangeInterceptor {
    /**
     * 是否能切换到标签为tabId的Fragment
     * @param tabId 目标Fragment的标签
     * @return 是否能切换
     */
    boolean canTab(String tabId);

    /**
     * 当切换被拦截时调用
     * @param tabId 被拦截的Fragment的标签
     */
    void onTabIntercepted(String tabId);
}

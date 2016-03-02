package com.corelibs.common;

import android.app.Activity;

import java.util.Stack;

/**
 * 模拟Android Activity栈, 方便管理Activity
 */
public final class AppManager {

    private static Stack<Activity> activityStack;

    private static AppManager instance;

    private AppManager() {
    }

    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity(堆栈中最后一个压入的)
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        Activity activity = null;
        for (Activity a : activityStack) {
            if (a.getClass().equals(cls)) {
                activity = a;
                break;
            }
        }
        if (activity != null)
            finishActivity(activity);
    }

    /**
     * 获得指定类名的Activity
     */
    public Activity getActivity(Class<?> cls) {
        /*
		 * for (Activity activity : activityStack) { if
		 * (activity.getClass().equals(cls)) { return activity; } }
		 */
        // 应该由栈顶往下去遍历拿最近的activity去比较
        if (null != activityStack && activityStack.size() != 0) {
            int size = activityStack.size();
            for (int i = size - 1; i >= 0; i--) {
                Activity activity = activityStack.get(i);
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        }
        return null;
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        finishAllActivity();
        System.exit(0);
    }
}
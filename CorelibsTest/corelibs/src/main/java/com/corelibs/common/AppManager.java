package com.corelibs.common;

import android.app.Activity;

import java.util.Stack;

/**
 * 
 * [一句话功能简述 应用程序Activity管理类：用于Activity管理和应用程序退出] [功能详细描述]
 * 
 * @作者 lWX178795
 * @version [版本号, 2013-12-3]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class AppManager {
	/**
	 * 保存Activity的栈
	 */
	private static Stack<Activity> activityStack;

	/**
	 * 单例
	 */
	private static AppManager instance;

	/**
	 * 构造函数
	 */
	private AppManager() {
	}

	/**
	 * 
	 * [一句话功能简述 单一实例] [功能详细描述]
	 * 
	 * @param [参数1] [参数1说明]
	 * @param [参数2] [参数2说明]
	 * @return [返回类型说明]
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static AppManager getAppManager() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}

	/**
	 * 
	 * [一句话功能简述 添加Activity到堆栈] [功能详细描述]
	 * 
	 * @param [参数1] [参数1说明]
	 * @param [参数2] [参数2说明]
	 * @return [返回类型说明]
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * 
	 * [一句话功能简述 获取当前Activity（堆栈中最后一个压入的）] [功能详细描述]
	 * 
	 * @param [参数1] [参数1说明]
	 * @param [参数2] [参数2说明]
	 * @return [返回类型说明]
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 
	 * [一句话功能简述 结束当前Activity（堆栈中最后一个压入的）] [功能详细描述]
	 * 
	 * @param [参数1] [参数1说明]
	 * @param [参数2] [参数2说明]
	 * @return [返回类型说明]
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 
	 * [一句话功能简述 结束指定的Activity] [功能详细描述]
	 * 
	 * @param [参数1] [参数1说明]
	 * @param [参数2] [参数2说明]
	 * @return [返回类型说明]
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 
	 * [一句话功能简述 结束指定类名的Activity] [功能详细描述]
	 * 
	 * @param [参数1] [参数1说明]
	 * @param [参数2] [参数2说明]
	 * @return [返回类型说明]
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
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
	 * 
	 * [一句话功能简述 获得指定类名的Activity] [功能详细描述]
	 * 
	 * @param [参数1] [参数1说明]
	 * @param [参数2] [参数2说明]
	 * @return [返回类型说明]
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
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
	 * 
	 * [一句话功能简述 结束所有Activity] [功能详细描述]
	 * 
	 * @param [参数1] [参数1说明]
	 * @param [参数2] [参数2说明]
	 * @return [返回类型说明]
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
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
	 * 
	 * [一句话功能简述 退出应用程序] [功能详细描述]
	 * 
	 * @param [参数1] [参数1说明]
	 * @param [参数2] [参数2说明]
	 * @return [返回类型说明]
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void appExit() {
		try {
			finishAllActivity();
			System.exit(0);
		} catch (Exception e) {

		}
	}

	/**
	 * 
	 * [一句话功能简述 获取activity 栈] [功能详细描述]
	 * 
	 * @param [参数1] [参数1说明]
	 * @param [参数2] [参数2说明]
	 * @return [返回类型说明]
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public Stack<Activity> getStack() {
		return activityStack;
	}
}
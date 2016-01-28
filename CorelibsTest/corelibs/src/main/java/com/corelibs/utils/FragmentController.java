package com.corelibs.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * 用于对Fragment进行管理
 * 
 * @author 赵成龙
 * @since 2014-12-30
 */
public class FragmentController {

	private FragmentManager fragmentManager;
	private int resource;

	private String[] fragmentTags;

	private Fragment currentFragment;

	public FragmentController(FragmentManager fragmentManager, int resource, String[] fragmentTags) {
		this.resource = resource;
		this.fragmentManager = fragmentManager;
		this.fragmentTags = fragmentTags;
	}

	public void add(Class<? extends Fragment> clazz, String tag, Bundle bundle) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		for (String tagName : fragmentTags) {
			Fragment fragment = fragmentManager.findFragmentByTag(tagName);
			if (fragment != null) {
				transaction.hide(fragment);
			}
		}
		Fragment fragment = fragmentManager.findFragmentByTag(tag);
		// transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
		currentFragment = null;
		if (fragment != null) {
			if (bundle != null) {
				try {
					fragment = clazz.newInstance();
					currentFragment = fragment;
					transaction.add(resource, fragment, tag);
					fragment.setArguments(bundle);
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} else {
				currentFragment = fragment;
				transaction.show(fragment);
			}
		} else {
			try {
				fragment = clazz.newInstance();
				currentFragment = fragment;
				transaction.add(resource, fragment, tag);
				if (bundle != null) {
					fragment.setArguments(bundle);
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		// transaction.commit();
		transaction.commitAllowingStateLoss();
	}

	public Fragment getCurrentFragment() {
		return currentFragment;
	}

}

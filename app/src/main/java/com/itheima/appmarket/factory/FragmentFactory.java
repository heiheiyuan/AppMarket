package com.itheima.appmarket.factory;

import android.util.SparseArray;

import com.itheima.appmarket.base.BaseFragment;
import com.itheima.appmarket.fragment.AppFragment;
import com.itheima.appmarket.fragment.CategoryFragment;
import com.itheima.appmarket.fragment.GameFragment;
import com.itheima.appmarket.fragment.HomeFragment;
import com.itheima.appmarket.fragment.HotFragment;
import com.itheima.appmarket.fragment.RecommendFragment;
import com.itheima.appmarket.fragment.SubjectFragment;

public class FragmentFactory {

	private static SparseArray<BaseFragment> fragmentMap = new SparseArray<BaseFragment>();

	public static BaseFragment getFragment(int position) {
		BaseFragment fragment = fragmentMap.get(position);
		if (fragment != null) {
			return fragment;
		}else {
			switch (position) {
			case 0:
				fragment = new HomeFragment();
				break;
			case 1:
				fragment = new AppFragment();
				break;
			case 2:
				fragment = new GameFragment();
				break;
			case 3:
				fragment = new SubjectFragment();
				break;
			case 4:
				fragment = new RecommendFragment();
				break;
			case 5:
				fragment = new CategoryFragment();
				break;
			case 6:
				fragment = new HotFragment();
				break;
			}
			fragmentMap.put(position, fragment);
			return fragment;
		}
	}

}

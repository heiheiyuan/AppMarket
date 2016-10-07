package com.itheima.appmarket.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.itheima.appmarket.R;
import com.itheima.appmarket.factory.FragmentFactory;
import com.itheima.appmarket.utils.UIUtils;

public class MainFragmentAdapter extends FragmentPagerAdapter{

	private final String[] tabNames;

	public MainFragmentAdapter(FragmentManager fm) {
		super(fm);
		tabNames = UIUtils.getStringArray(R.array.tab_names);
	}
	@Override
	public Fragment getItem(int position) {
		Fragment fragment = FragmentFactory.getFragment(position);
		return fragment;
	}
	@Override
	public int getCount() {
		return tabNames.length;
	}
	//PagerSlidingTab要通过该方法获取Tab上的文字
	@Override
	public CharSequence getPageTitle(int position) {
		return tabNames[position];
	}

}

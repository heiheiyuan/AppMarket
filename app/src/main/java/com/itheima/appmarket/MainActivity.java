package com.itheima.appmarket;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.itheima.appmarket.adapter.MainFragmentAdapter;
import com.itheima.appmarket.base.BaseFragment;
import com.itheima.appmarket.factory.FragmentFactory;
import com.itheima.appmarket.ui.PagerSlidingTab;

public class MainActivity extends ActionBarActivity {

	private ViewPager mContentVp;
	private PagerSlidingTab mTabPst;
	private DrawerLayout mMainDrawer;
	private FrameLayout mMainFl;
	private ActionBarDrawerToggle toggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContentVp = (ViewPager) findViewById(R.id.content_vp);
		mTabPst = (PagerSlidingTab) findViewById(R.id.tab_pst);
		mMainDrawer = (DrawerLayout) findViewById(R.id.drawerlayout);
		mMainFl = (FrameLayout) findViewById(R.id.main_fl);
		//给ViewPager设置适配器
		mContentVp.setAdapter(new MainFragmentAdapter(getSupportFragmentManager()));
		//关联tab和viewPager
		mTabPst.setViewPager(mContentVp);
		initActionBar();
		mTabPst.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				BaseFragment fragment = FragmentFactory.getFragment(position);
				fragment.show();
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			@Override
			public void onPageScrollStateChanged(int arg0) {}
		});
	}

	private void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("GoogleMarket");
		actionBar.setIcon(R.drawable.ic_launcher);
		//设置actionBar的home按钮可以被点击
		actionBar.setHomeButtonEnabled(true);
		//设置home按钮的返回按钮
		actionBar.setDisplayHomeAsUpEnabled(true);
		toggle = new ActionBarDrawerToggle(this, mMainDrawer, R.drawable.ic_drawer_am, 0, 0);
		//同步actionBar和drawerLayout的状态
		toggle.syncState();
		
		//图标动画
		mMainDrawer.setDrawerListener(new DrawerListener() {

			@Override
			public void onDrawerStateChanged(int newState) {
				toggle.onDrawerStateChanged(newState);
			}
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				toggle.onDrawerSlide(drawerView, slideOffset);
			}
			@Override
			public void onDrawerOpened(View drawerView) {
				toggle.onDrawerOpened(drawerView);
			}
			@Override
			public void onDrawerClosed(View drawerView) {
				toggle.onDrawerClosed(drawerView);
			}
		});
	}
		//当actionBar的Home按钮被点击,系统会回调onOptionsItemSelected方法
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			if (item.getItemId() == android.R.id.home) {
				toggle.onOptionsItemSelected(item);
			}
			return super.onOptionsItemSelected(item);
		}


	}

package com.itheima.appmarket.base;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.appmarket.ui.LoadingPage;
import com.itheima.appmarket.ui.LoadingPage.ResultState;
import com.itheima.appmarket.utils.UIUtils;


public abstract class BaseFragment extends Fragment {
	private LoadingPage loadingPage;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		loadingPage = new LoadingPage(UIUtils.getContext()) {
			@Override
			public View onCreateSuccessedView() {
				return BaseFragment.this.onCreateSuccessedView();
			}
			//请求网络的返回结果,需要告知loadingPager界面
			@Override
			public ResultState onLoad() {
				return BaseFragment.this.onLoad();
			}

		};
		return loadingPage;
	}

	public abstract View onCreateSuccessedView();
	public abstract ResultState onLoad();
	public void show() {
		//请求网络
		if (loadingPage != null) {
			loadingPage.show();
		}
	}
	public ResultState check(Object object) {
		if (object instanceof List) {
			if (((List<?>)object).size() > 0) {
				return ResultState.STATE_SUCCESSED;
			}else if (((List<?>)object).size() == 0) {
				return ResultState.STATE_EMPTY;
			}
		}
		return ResultState.STATE_ERROR;
	}
}

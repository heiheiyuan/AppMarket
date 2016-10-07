package com.itheima.appmarket.fragment;

import java.util.List;

import android.view.View;

import com.itheima.appmarket.adapter.HomeAdapter;
import com.itheima.appmarket.base.BaseFragment;
import com.itheima.appmarket.bean.AppInfo;
import com.itheima.appmarket.protocol.HomeProtocol;
import com.itheima.appmarket.ui.LoadingPage.ResultState;
import com.itheima.appmarket.ui.MyListView;
import com.itheima.appmarket.utils.UIUtils;

public class HomeFragment extends BaseFragment {

	private List<AppInfo> mDatas;
	@Override
	public View onCreateSuccessedView() {
		MyListView listView = new MyListView(UIUtils.getContext());
		//设置适配器
		//HomeAdapter adapter = new HomeAdapter(mDatas);
		HomeAdapter adapter = new HomeAdapter(mDatas);
		listView.setAdapter(adapter);
		return listView;
	}
	@Override
	public ResultState onLoad() {
		HomeProtocol homeProtocol = new HomeProtocol();
		mDatas = homeProtocol.getData(0);
		return check(mDatas);
	}
}

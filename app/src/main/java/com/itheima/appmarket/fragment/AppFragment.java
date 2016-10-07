package com.itheima.appmarket.fragment;

import java.util.List;

import android.view.View;

import com.itheima.appmarket.adapter.AppAdapter;
import com.itheima.appmarket.base.BaseFragment;
import com.itheima.appmarket.bean.AppInfo;
import com.itheima.appmarket.protocol.AppProtocol;
import com.itheima.appmarket.ui.LoadingPage.ResultState;
import com.itheima.appmarket.ui.MyListView;
import com.itheima.appmarket.utils.UIUtils;

public class AppFragment extends BaseFragment {

	private AppProtocol appProtocol;
	private List<AppInfo> data;
	@Override
	public View onCreateSuccessedView() {
		MyListView listView = new MyListView(UIUtils.getContext());
		AppAdapter adapter = new AppAdapter(data);
		listView.setAdapter(adapter);
		return listView;
	}
	@Override
	public ResultState onLoad() {
		appProtocol = new AppProtocol();
		data = appProtocol.getData(0);
		return check(data);
	}
}

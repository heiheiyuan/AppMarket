package com.itheima.appmarket.fragment;

import java.util.List;

import android.view.View;

import com.itheima.appmarket.adapter.SubjectAdapter;
import com.itheima.appmarket.base.BaseFragment;
import com.itheima.appmarket.bean.SubjectInfo;
import com.itheima.appmarket.protocol.SubjectProtocol;
import com.itheima.appmarket.ui.LoadingPage.ResultState;
import com.itheima.appmarket.ui.MyListView;
import com.itheima.appmarket.utils.UIUtils;

public class SubjectFragment extends BaseFragment {
	private List<SubjectInfo> data;
	@Override
	public View onCreateSuccessedView() {
		MyListView listView = new MyListView(UIUtils.getContext());
		listView.setAdapter(new SubjectAdapter(data));
		return listView;
	}
	@Override
	public ResultState onLoad() {
		SubjectProtocol protocol = new SubjectProtocol();
		data = protocol.getData(0);
		return check(data);
	}

}

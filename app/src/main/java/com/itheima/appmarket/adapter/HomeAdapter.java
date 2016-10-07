package com.itheima.appmarket.adapter;

import java.util.List;

import com.itheima.appmarket.bean.AppInfo;
import com.itheima.appmarket.holder.BaseHolder;
import com.itheima.appmarket.holder.HomeHolder;
import com.itheima.appmarket.protocol.HomeProtocol;

public class HomeAdapter extends MyBaseAdapter<AppInfo> {

	public HomeAdapter(List<AppInfo> data) {
		super(data);
	}
	@Override
	public List<AppInfo> onLoadMore() {
		HomeProtocol protocol = new HomeProtocol();
		List<AppInfo> moreData = protocol.getData(getListSize());
		return moreData;
	}
	@Override
	public BaseHolder<AppInfo> getHolder(int position) {
		return  new HomeHolder();
	}

}

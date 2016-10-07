package com.itheima.appmarket.adapter;

import java.util.List;
import com.itheima.appmarket.bean.AppInfo;
import com.itheima.appmarket.holder.BaseHolder;
import com.itheima.appmarket.holder.HomeHolder;
import com.itheima.appmarket.protocol.AppProtocol;

public class AppAdapter extends MyBaseAdapter<AppInfo> {
	public AppAdapter(List<AppInfo> data) {
		super(data);
	}
	@Override
	public BaseHolder<AppInfo> getHolder(int position) {
		return new HomeHolder();
	}
	@Override
	public List<AppInfo> onLoadMore() {
		AppProtocol protocol = new AppProtocol();
		List<AppInfo> list = protocol.getData(getListSize());
		return list;
	}
}

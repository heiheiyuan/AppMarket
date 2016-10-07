package com.itheima.appmarket.adapter;

import java.util.List;

import com.itheima.appmarket.bean.SubjectInfo;
import com.itheima.appmarket.holder.BaseHolder;
import com.itheima.appmarket.holder.SubjectHolder;
import com.itheima.appmarket.protocol.SubjectProtocol;

public class SubjectAdapter extends MyBaseAdapter<SubjectInfo> {

		public SubjectAdapter(List<SubjectInfo> data) {
			super(data);
		}
		@Override
		public BaseHolder<SubjectInfo> getHolder(int position) {
			return new SubjectHolder();
		}
		@Override
		public List<SubjectInfo> onLoadMore() {
			SubjectProtocol protocol = new SubjectProtocol();
			List<SubjectInfo> data = protocol.getData(getListSize());
			return data;
		}
	}
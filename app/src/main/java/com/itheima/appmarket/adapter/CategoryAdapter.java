package com.itheima.appmarket.adapter;

import java.util.List;

import com.itheima.appmarket.bean.CategoryBean;
import com.itheima.appmarket.holder.BaseHolder;
import com.itheima.appmarket.holder.CategoryHolder;
import com.itheima.appmarket.holder.TitleHolder;

class CategoryAdapter extends MyBaseAdapter<CategoryBean> {
	private static final int ITEM_TITLE = 2;
	public CategoryAdapter(List<CategoryBean> data) {
		super(data);
	}
	@Override
	public BaseHolder<CategoryBean> getHolder(int position) {
		if (getItemViewType(position) == ITEM_TITLE) {
			return new TitleHolder();
		}else {
			return new CategoryHolder();
		}
	}
	@Override
	public boolean hasMore() {
		return false;
	}
	@Override
	public int getViewTypeCount() {
		return super.getViewTypeCount() + 1;
	}
	@Override
	public int getNormalViewType(int position) {
		CategoryBean bean = mDatas.get(position);
		if (bean.getIsTitle()) {
			return ITEM_TITLE;
		}
		return super.getNormalViewType(position);
	}
	@Override
	public List<CategoryBean> onLoadMore() {
		return null;
	}
}
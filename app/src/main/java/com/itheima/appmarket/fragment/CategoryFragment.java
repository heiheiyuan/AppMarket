package com.itheima.appmarket.fragment;

import java.util.List;

import android.view.View;

import com.itheima.appmarket.adapter.MyBaseAdapter;
import com.itheima.appmarket.base.BaseFragment;
import com.itheima.appmarket.bean.CategoryBean;
import com.itheima.appmarket.holder.BaseHolder;
import com.itheima.appmarket.holder.CategoryHolder;
import com.itheima.appmarket.holder.TitleHolder;
import com.itheima.appmarket.protocol.CategoryProtocol;
import com.itheima.appmarket.ui.LoadingPage.ResultState;
import com.itheima.appmarket.ui.MyListView;
import com.itheima.appmarket.utils.UIUtils;

public class CategoryFragment extends BaseFragment {
	
	private List<CategoryBean> data;

	@Override
	public View onCreateSuccessedView() {
		MyListView listView = new MyListView(UIUtils.getContext());
		listView.setAdapter(new CategoryAdapter(data));
		return listView;
	}
	@Override
	public ResultState onLoad() {
		CategoryProtocol protocol = new CategoryProtocol();
		data = protocol.getData(0);
		return check(data);
	}
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
			CategoryBean bean = data.get(position);
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
}

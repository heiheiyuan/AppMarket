package com.itheima.appmarket.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.itheima.appmarket.holder.BaseHolder;
import com.itheima.appmarket.holder.MoreHolder;
import com.itheima.appmarket.manager.ThreadManager;
import com.itheima.appmarket.utils.UIUtils;

public abstract class MyBaseAdapter<T> extends BaseAdapter {

	public static final int NORMAL_ITEM_TYPE = 0;
	public static final int MORE_ITEM_TYPE = 1;
	public List<T> mDatas;
	public List<T> loadMore;
	private BaseHolder<T> holder;
	private MoreHolder moreHolder;
	public MyBaseAdapter(List<T> data) {
		setData(data);
	}
	public void setData(List<T> datas) {
		this.mDatas = datas;
	}
	public List<T> getData() {
		return mDatas;
	}
	@Override
	public int getCount() {
		return mDatas.size() + 1;
	}
	protected int getListSize() {
		return mDatas.size();
	}
	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		//判断当前位置是否为最后一个条目
		if (position == getCount() - 1) {
			return MORE_ITEM_TYPE;
		}
		return getNormalViewType(position);
	}
	public int getNormalViewType(int position) {
		return super.getItemViewType(position);
	}
	/**
	 * 获取到一共有多少种类型
	 * @return
	 */
	@Override
	public int getViewTypeCount() {
		return super.getViewTypeCount() + 1;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView != null) {
			holder = (BaseHolder) convertView.getTag();
		}else {
			if (getItemViewType(position) == MORE_ITEM_TYPE) {
				holder = getMoreHolder();
			} else {
				holder = getHolder(position);
			}
		}
		if (getItemViewType(position) != MORE_ITEM_TYPE) {
			holder.setData(mDatas.get(position));
		}
		return holder.getRootView();
	}
	private BaseHolder getMoreHolder() {
		if (moreHolder == null) {
			moreHolder = new MoreHolder(hasMore(),this);
		}
		return moreHolder;
	}
	public boolean hasMore() {
		return true;
	}
	
	public void loadMore() {
		ThreadManager.getThreadProxyPool().execute(new Runnable() {
			@Override
			public void run() {
				loadMore = onLoadMore();
				UIUtils.runOnMainThread(new Runnable() {
					@Override
					public void run() {
						if (loadMore == null) {
							getMoreHolder().setData(MoreHolder.ERROR);
						} else if (loadMore.size() < 20) {
							getMoreHolder().setData(MoreHolder.NO_MORE);
						} else {
							getMoreHolder().setData(MoreHolder.HAS_MORE);
						}
						if (loadMore != null && mDatas != null) {
							mDatas.addAll(loadMore);
						}
						notifyDataSetChanged();
					}
				});
			}
		});
	}
	public abstract List<T> onLoadMore();
	public abstract BaseHolder<T> getHolder(int position);
}

package com.itheima.appmarket.holder;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.appmarket.R;
import com.itheima.appmarket.adapter.MyBaseAdapter;
import com.itheima.appmarket.utils.UIUtils;

public class MoreHolder extends BaseHolder<Integer> implements OnClickListener {
	public static int HAS_MORE = 1;
	public static int NO_MORE = 2;
	public static int ERROR = 3;
	private LinearLayout mLoadMoreLl;
	private TextView mLoadErrorTv;
	private View view;
	private MyBaseAdapter adapter;
	public MoreHolder(boolean hasMore, MyBaseAdapter myBaseAdapter) {
		setData(hasMore ? HAS_MORE : NO_MORE);
		adapter = myBaseAdapter;
	}
	@Override
	public void refreshView() {
		Integer data = getData();
		mLoadMoreLl.setVisibility(data == HAS_MORE ? View.VISIBLE : View.GONE );
		mLoadErrorTv.setVisibility(data == ERROR ? View.VISIBLE : View.GONE);
	}
	@Override
	public View initView() {
		view = UIUtils.inflate(R.layout.layout_load_more);
		mLoadMoreLl = (LinearLayout) view.findViewById(R.id.load_more_ll);
		mLoadErrorTv = (TextView) view.findViewById(R.id.load_more_error);
		mLoadErrorTv.setOnClickListener(this);
		return view;
	}
	@Override
	public void onClick(View v) {
		adapter.loadMore();
	}
	@Override
	public View getRootView() {
		if (getData() == HAS_MORE && adapter != null) {
			adapter.loadMore();
		}
		return super.getRootView();
	}
}

package com.itheima.appmarket.holder;

import android.view.View;

public abstract class BaseHolder<T> {

	private View view;
	private T mData;
	public BaseHolder() {
		view = initView();
		view.setTag(this);
	}
	public void setData(T t){
		this.mData = t;
		refreshView();
	}
	public T getData() {
		return mData;
	}
	public View getRootView() {
		return view;
	}
	public abstract void refreshView();
	public abstract View initView();
}

package com.itheima.appmarket.ui;

import android.content.Context;
import android.widget.ListView;

public class MyListView extends ListView{

	public MyListView(Context context) {
		super(context);
		initView();
	}

	private void initView() {
		//去掉分割线
		this.setDivider(null);
		//去掉选中背景
		this.setSelector(android.R.color.transparent);
	}

}

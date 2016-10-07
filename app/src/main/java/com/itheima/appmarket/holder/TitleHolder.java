package com.itheima.appmarket.holder;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.itheima.appmarket.bean.CategoryBean;
import com.itheima.appmarket.utils.UIUtils;

public class TitleHolder extends BaseHolder<CategoryBean> {
	private TextView textView;
	@Override
	public View initView() {
		textView = new TextView(UIUtils.getContext());
		textView.setTextColor(Color.BLACK);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		return textView;
	}
	@Override
	public void refreshView() {
		CategoryBean data = getData();
		textView.setText(data.getTitle());
	}

}

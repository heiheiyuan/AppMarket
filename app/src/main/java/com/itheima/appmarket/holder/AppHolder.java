package com.itheima.appmarket.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.itheima.appmarket.R;
import com.itheima.appmarket.bean.AppInfo;
import com.itheima.appmarket.helper.BitmapHelper;
import com.itheima.appmarket.http.HttpHelper;
import com.itheima.appmarket.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

public class AppHolder extends BaseHolder<AppInfo> {
	private TextView app_des,app_name,app_size;
	private ImageView app_icon;
	private RatingBar rating_bar;
	private BitmapUtils bitmapUtils;
	private View view;
	@Override
	public void refreshView() {
		AppInfo data = getData();
		app_des.setText(data.getDes());
		app_name.setText(data.getName());
		app_size.setText(data.getSize()+"");
		rating_bar.setRating(data.getStars());
		bitmapUtils = BitmapHelper.getBitmapUtils();
		bitmapUtils.display(app_icon, HttpHelper.URL + "image?name=" + data.getIconUrl());
	}

	@Override
	public View initView() {
		view = UIUtils.inflate(R.layout.layout_home_item);
		app_des = (TextView) view.findViewById(R.id.app_des);
		app_icon = (ImageView) view.findViewById(R.id.app_icon);
		app_name = (TextView) view.findViewById(R.id.app_name);
		app_size = (TextView) view.findViewById(R.id.app_size);
		rating_bar = (RatingBar) view.findViewById(R.id.rating_bar);
		return view;
	}

}

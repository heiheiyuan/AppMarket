package com.itheima.appmarket.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.appmarket.R;
import com.itheima.appmarket.bean.SubjectInfo;
import com.itheima.appmarket.helper.BitmapHelper;
import com.itheima.appmarket.http.HttpHelper;
import com.itheima.appmarket.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

public class SubjectHolder extends BaseHolder<SubjectInfo> {

	private ImageView mPicRiv;
	private TextView mDesTv;
	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.layout_subject);
		mPicRiv = (ImageView) view.findViewById(R.id.pic_riv);
		mDesTv = (TextView) view.findViewById(R.id.des_tv);
		return view;
	}
	@Override
	public void refreshView() {
		SubjectInfo data = getData();
		BitmapUtils utils = BitmapHelper.getBitmapUtils();
		utils.display(mPicRiv, HttpHelper.URL + "image?name=" + data.getUrl());
		mDesTv.setText(data.getDes());
	}

}

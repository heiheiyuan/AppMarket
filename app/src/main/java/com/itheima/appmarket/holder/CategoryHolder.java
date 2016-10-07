package com.itheima.appmarket.holder;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.appmarket.R;
import com.itheima.appmarket.bean.CategoryBean;
import com.itheima.appmarket.helper.BitmapHelper;
import com.itheima.appmarket.http.HttpHelper;
import com.itheima.appmarket.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

public class CategoryHolder extends BaseHolder<CategoryBean> implements OnClickListener {

	private LinearLayout mMenu1,mMenu2,mMenu3;
	private TextView mName1,mName2,mName3;
	private ImageView mImg1,mImg2,mImg3;
	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.layout_category_item);
		mMenu1 = (LinearLayout) view.findViewById(R.id.menu1_ll);
		mMenu2 = (LinearLayout) view.findViewById(R.id.menu2_ll);
		mMenu3 = (LinearLayout) view.findViewById(R.id.menu3_ll);
		mName1 = (TextView) view.findViewById(R.id.name1_tv);
		mName2 = (TextView) view.findViewById(R.id.name2_tv);
		mName3 = (TextView) view.findViewById(R.id.name3_tv);
		mImg1 = (ImageView) view.findViewById(R.id.img1_iv);
		mImg2 = (ImageView) view.findViewById(R.id.img2_iv);
		mImg3 = (ImageView) view.findViewById(R.id.img3_iv);
		mMenu1.setOnClickListener(this);
		mMenu2.setOnClickListener(this);
		mMenu3.setOnClickListener(this);
		return view;
	}
	@Override
	public void refreshView() {
		CategoryBean data = getData();
		BitmapUtils utils = BitmapHelper.getBitmapUtils();
		
		String name1 = data.getName1();
		if (name1 != null) {
			mMenu1.setVisibility(View.VISIBLE);
			mName1.setText(name1);
			utils.display(mImg1, HttpHelper.URL + "image?name=" + data.getUrl1());
		}else {
			mMenu1.setVisibility(View.GONE);
		}

		String name2 = data.getName2();
		if (name2 != null) {
			mMenu2.setVisibility(View.VISIBLE);
			mName2.setText(name2);
			utils.display(mImg2, HttpHelper.URL + "image?name=" + data.getUrl2());	
		} else {
			mMenu2.setVisibility(View.GONE);
		}


		String name3 = data.getName3();
		if (name3 != null) {
			mMenu3.setVisibility(View.VISIBLE);
			mName3.setText(name3);
			utils.display(mImg3, HttpHelper.URL + "image?name=" + data.getUrl3());	
		} else {
			mMenu3.setVisibility(View.GONE);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu1_ll:
			Toast.makeText(UIUtils.getContext(), mName1.getText(), Toast.LENGTH_SHORT).show();
			break;
		case R.id.menu2_ll:
			Toast.makeText(UIUtils.getContext(), mName2.getText(), Toast.LENGTH_SHORT).show();
			break;
		case R.id.menu3_ll:
			Toast.makeText(UIUtils.getContext(), mName3.getText(), Toast.LENGTH_SHORT).show();
			break;
		}

	}

}

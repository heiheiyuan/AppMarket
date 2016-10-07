package com.itheima.appmarket.holder;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.itheima.appmarket.R;
import com.itheima.appmarket.bean.AppInfo;
import com.itheima.appmarket.bean.DownloadInfo;
import com.itheima.appmarket.helper.BitmapHelper;
import com.itheima.appmarket.http.HttpHelper;
import com.itheima.appmarket.manager.DownloadManager;
import com.itheima.appmarket.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

public class HomeHolder extends BaseHolder<AppInfo> {

	private View view;
	private TextView app_des;
	private ImageView app_icon;
	private TextView app_name;
	private TextView app_size;
	private RatingBar rating_bar;
	private FrameLayout fl;
	private TextView tv_download;
	private AppInfo appInfo;
	private DownloadManager downloadManager;
	private float progress;
	private int state;
	@Override
	public View initView() {
		view = UIUtils.inflate(R.layout.layout_home_item);
		app_des = (TextView) view.findViewById(R.id.app_des);
		app_icon = (ImageView) view.findViewById(R.id.app_icon);
		app_name = (TextView) view.findViewById(R.id.app_name);
		app_size = (TextView) view.findViewById(R.id.app_size);
		rating_bar = (RatingBar) view.findViewById(R.id.rating_bar);
		fl = (FrameLayout) view.findViewById(R.id.fl);
		tv_download = (TextView) view.findViewById(R.id.tv_download);
		return view;
	}
	@Override
	public void refreshView() {
		appInfo = getData();
		app_des.setText(appInfo.getDes());
		app_name.setText(appInfo.getName());
		app_size.setText(appInfo.getSize() + "");
		rating_bar.setRating(appInfo.getStars());
		BitmapUtils bitmapUtils = BitmapHelper.getBitmapUtils();
		bitmapUtils.display(app_icon, HttpHelper.URL + "image?name=" + appInfo.getIconUrl());
		refreshMainThreadUI(state,progress);
	}
	private void refreshMainThreadUI(int state, float progress) {
		this.state = state;
		this.progress = progress;
		switch (state) {
		case DownloadManager.STATE_NONE:

			break;
		case DownloadManager.STATE_WAITTING:

			break;
		case DownloadManager.STATE_DOWNLOADING:

			break;
		case DownloadManager.STATE_DOWNLOADED:

			break;
		case DownloadManager.STATE_ERROR:

			break;
		case DownloadManager.STATE_PAUSE:

			break;
		}

	}
	@Override
	public void setData(AppInfo data) {
		//通过appInfo获取Id
		long id = data.getId();
		//通过id在downloadManager中封装downloadinfoMap中的downloadinfo对象
		if (downloadManager == null) {
			downloadManager = DownloadManager.getInstance();
		}
		DownloadInfo downloadInfo = downloadManager.getDownloadInfo(id);
		if (downloadInfo != null) {
			//说明已经下载过
			state = downloadInfo.getCurrentState();
			progress = downloadInfo.getProgress();
		} else {
			state = DownloadManager.STATE_NONE;
			progress = 0;
		}
		super.setData(data);
	}


}

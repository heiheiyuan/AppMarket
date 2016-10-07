package com.itheima.appmarket.ui;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.itheima.appmarket.R;
import com.itheima.appmarket.manager.ThreadManager;
import com.itheima.appmarket.utils.UIUtils;

public abstract class LoadingPage extends FrameLayout {
	private int STATE_UNLOAD = 0;
	private int STATE_LOADING = 1;
	private int STATE_ERROR = 2;
	private int STATE_EMPTY = 3;
	private int STATE_SUCCESSED = 4;
	private int state;
	private LayoutParams params;
	private View loadingView;
	private View errorView;
	private View emptyView;
	private View successedView;

	public LoadingPage(Context context) {
		super(context);
		params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
		initView();
	}

	private void initView() {
		state = STATE_UNLOAD;
		if (loadingView == null) {
			loadingView = UIUtils.inflate(R.layout.layout_loading);
			//添加到fragmentLayout
			addView(loadingView,params);
		}
		if (errorView == null) {
			errorView = UIUtils.inflate(R.layout.layout_error);
			Button error = (Button) errorView.findViewById(R.id.error_btn);
			error.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					show();
				}
			});
			addView(errorView,params);
		}
		if (emptyView == null) {
			emptyView = UIUtils.inflate(R.layout.layout_empty);
			addView(emptyView,params);
		}
		//安全显示UI的操作
		showSafePage();
	}
	public void showSafePage() {
		UIUtils.runOnMainThread(new Runnable() {
			@Override
			public void run() {
				showPage();
			}
		});
	}
	private void showPage() {
		// 在默认状态下展示loadingView
		if (loadingView != null){
			loadingView.setVisibility((state == STATE_UNLOAD || state == STATE_LOADING) ? View.VISIBLE : View.INVISIBLE);
		}
		//错误界面
		if (errorView != null) {
			errorView.setVisibility((state == STATE_ERROR)? View.VISIBLE : View.INVISIBLE);
		}
		//空界面
		if (emptyView != null) {
			emptyView.setVisibility((state == STATE_EMPTY)? View.VISIBLE : View.INVISIBLE);
		}
		//成功加载界面
		if (successedView == null && state == STATE_SUCCESSED) {
			successedView = onCreateSuccessedView();
			addView(successedView,params);
		}
		if (successedView != null) {
			successedView.setVisibility((state == STATE_SUCCESSED)? View.VISIBLE : View.INVISIBLE);
		}
	}
	class RunnableTask implements Runnable {
		@Override
		public void run() {
			// 请求网络操作
			final ResultState resultState = onLoad();
			UIUtils.runOnMainThread(new Runnable() {
				@Override
				public void run() {
					if (resultState != null) {
						state = resultState.getValue();
						//根据请求网络状态,作业面的展示
						showPage();
					}
				}
			});
		}
	}
	public void show() {
		if (state == STATE_EMPTY || state == STATE_ERROR || state == STATE_SUCCESSED) {
			state = STATE_UNLOAD;
		}
		if (state == STATE_UNLOAD) {
			/**
			 * 获取CPU核数
			 * Runtime.getRuntime().availableProcessors();
			 * 开启线程数:核数 * 2 - 1;
			 */
			RunnableTask task = new RunnableTask();
			ThreadManager.getThreadProxyPool().execute(task);
			/*new Thread() {
				public void run() {
					//请求网络
					final ResultState resultState = onLoad();
					UIUtils.runOnMainThread(new Runnable() {
						@Override
						public void run() {
							if (resultState != null) {
								state = resultState.getValue();
								//根据请求网络的状态,做页面的展示
								showPage();
							}
						}
					});
				};
			}.start();*/
		}
	}
	//界面成功创建
	public abstract View onCreateSuccessedView();
	//失败,为空,成功
	public abstract ResultState onLoad();
	//枚举决定返回值类型
	public enum ResultState{
		STATE_ERROR(2),
		STATE_EMPTY(3),
		STATE_SUCCESSED(4);
		private int value;
		private ResultState(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}

}

package com.itheima.appmarket.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

public class BaseApplication extends Application {
	private static Context context;
	private static int mainThradId;
	private static Thread mainThread;
	private static Handler handler;
	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		mainThradId = android.os.Process.myTid();
		mainThread = Thread.currentThread();
		handler = new Handler();
	}
	public static Context getContext() {
		return context;
	}
	public static int getMainThradId() {
		return mainThradId;
	}
	public static Thread getMianThread() {
		return mainThread;
	}
	public static Handler getHandler() {
		return handler;
	}

}

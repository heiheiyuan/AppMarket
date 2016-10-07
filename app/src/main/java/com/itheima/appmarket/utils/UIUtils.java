package com.itheima.appmarket.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;

import com.itheima.appmarket.application.BaseApplication;

public class UIUtils {
	public static Context getContext() {
		return BaseApplication.getContext();
	}
	public static int getMainThreadId() {
		return BaseApplication.getMainThradId();
	}
	public static Thread getMainThread() {
		return BaseApplication.getMianThread();
	}
	public static Handler getHandler() {
		return BaseApplication.getHandler();
	}
	//string
	public static String getString(int id) {
		return getContext().getResources().getString(id);
	}
	//drawable
	public static Drawable getDrawable(int id) {
		return getContext().getResources().getDrawable(id);
	}
	//StringArray
	public static String[] getStringArray(int id) {
		return getContext().getResources().getStringArray(id);
	}
	//dipToPx
	public static int dipToPx(int dip) {
		return (int) (dip * getContext().getResources().getDisplayMetrics().density + 0.5);
	}
	//pxToDip
	public static int pxToDip (int px) {
		return (int) (px / getContext().getResources().getDisplayMetrics().density + 0.5);
	}
	//判断是否是主线程
	public static boolean isRunOnMainThread() {
		return getMainThreadId() == android.os.Process.myTid();
	}
	//保证当前UI操作在主线程里面进行
	public static void runOnMainThread(Runnable task) {
		if (isRunOnMainThread()) {
			task.run();
		}else {
			getHandler().post(task);
		}
	}
	//java代码区设置颜色选择器的方法
	public static ColorStateList getColorStateList(int id) {
		return getContext().getResources().getColorStateList(id);
	}
	public static View inflate(int id) {
		return View.inflate(getContext(), id, null);
	}
	//根据dimens中提供的id,将其对应的dp值转换为相应的像素值大小
	public static int getDimens(int id) {
		return getContext().getResources().getDimensionPixelSize(id);
	}
	public static void postDelayed(Runnable task,long delayTime) {
		getHandler().postDelayed(task, delayTime);
	}
	public static void removeCallBack(Runnable task) {
		getHandler().removeCallbacks(task);
	}
	public static int getColor(int id) {
		return getContext().getResources().getColor(id);
	}
}

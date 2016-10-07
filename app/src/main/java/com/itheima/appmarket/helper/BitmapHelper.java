package com.itheima.appmarket.helper;

import com.itheima.appmarket.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

public class BitmapHelper {
	private static BitmapUtils bitmapUtils;
	private BitmapHelper() {}
	public static BitmapUtils getBitmapUtils () {
		if (bitmapUtils == null) {
			synchronized (BitmapUtils.class) {
				if (bitmapUtils == null) {
					bitmapUtils = new BitmapUtils(UIUtils.getContext());
				}
			}
		}
		return bitmapUtils;
	}
}

package com.itheima.appmarket.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.itheima.appmarket.bean.AppInfo;

public class HomeProtocol extends BaseProtocol<List<AppInfo>>{
	private List<AppInfo> appInfoList = new ArrayList<AppInfo>();
	private List<String> pictureList = new ArrayList<String>();
	//获取图片链接方法
	public List<String> getPicList() {
		return pictureList;
	}
	@Override
	public List<AppInfo> parseJson(String result) {
		//手动解析
		if (!TextUtils.isEmpty(result)) {
			try {
				JSONObject jsonObject = new JSONObject(result);
				if (jsonObject.has("list")) {
					JSONArray jsonArray = jsonObject.getJSONArray("list");
					appInfoList.clear();
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject object = jsonArray.getJSONObject(i);
						AppInfo appInfo = new AppInfo();
						appInfo.setDes(object.getString("des"));
						appInfo.setDownloadUrl(object.getString("downloadUrl"));
						appInfo.setIconUrl(object.getString("iconUrl"));
						appInfo.setId(object.getLong("id"));
						appInfo.setName(object.getString("name"));
						appInfo.setPackageName(object.getString("packageName"));
						appInfo.setSize(object.getLong("size"));
						appInfo.setStars((float)object.getDouble("stars"));
						appInfoList.add(appInfo);
					}
				}
				if (jsonObject.has("picture")) {
					JSONArray jsonArray = jsonObject.getJSONArray("picture");
					pictureList.clear();
					for (int i = 0; i < jsonArray.length(); i++) {
						String string = jsonArray.getString(i);
						pictureList.add(string);
					}
				}
				return appInfoList;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	@Override
	public String getKey() {
		return "home";
	}
	@Override
	public String getParams() {
		return "";
	}
}

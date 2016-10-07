package com.itheima.appmarket.protocol;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import com.itheima.appmarket.bean.AppInfo;
import com.itheima.appmarket.utils.StringUtils;

public class AppProtocol extends BaseProtocol<List<AppInfo>> {
private List<AppInfo> appInfoList = new ArrayList<AppInfo>();
	@Override
	public String getKey() {
		return "app";
	}
	@Override
	public String getParams() {
		return "";
	}
	@Override
	public List<AppInfo> parseJson(String result) {
		if (StringUtils.isEmpty(result)) {
			return null;
		}
		try {
			JSONArray jsonArray = new JSONArray(result);
			appInfoList.clear();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				AppInfo appInfo = new AppInfo();
				appInfo.setDes(jsonObject.getString("des"));
				appInfo.setDownloadUrl(jsonObject.getString("downloadUrl"));
				appInfo.setIconUrl(jsonObject.getString("iconUrl"));
				appInfo.setId(jsonObject.getLong("id"));
				appInfo.setName(jsonObject.getString("name"));
				appInfo.setPackageName(jsonObject.getString("packageName"));
				appInfo.setSize(jsonObject.getLong("size"));
				appInfo.setStars((float) jsonObject.getDouble("stars"));
				appInfoList.add(appInfo);
			}
			return appInfoList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

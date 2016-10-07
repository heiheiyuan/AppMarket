package com.itheima.appmarket.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.itheima.appmarket.bean.CategoryBean;

public class CategoryProtocol extends BaseProtocol<List<CategoryBean>> {
	private List<CategoryBean> categoryInfoList = new ArrayList<CategoryBean>();
	@Override
	public String getKey() {
		return "category";
	}
	@Override
	public String getParams() {
		return "";
	}
	@Override
	public List<CategoryBean> parseJson(String result) {
		if (!TextUtils.isEmpty(result)) {
			try {
				JSONArray jsonArray = new JSONArray(result);
				categoryInfoList.clear();
				for(int i = 0; i < jsonArray.length();i ++) {
					JSONObject jsonObject = jsonArray.optJSONObject(i);
					if (jsonObject.has("title")) {
						CategoryBean bean = new CategoryBean();
						String title = jsonObject.optString("title");
						bean.setTitle(title);
						bean.setIsTitle(true);
						categoryInfoList.add(bean);
					}
					if (jsonObject.has("infos")) {
						JSONArray arrayInfo = jsonObject.optJSONArray("infos");
						for(int j = 0;j < arrayInfo.length(); j ++ ) {
							JSONObject objectInfo = arrayInfo.optJSONObject(j);
							CategoryBean bean = new CategoryBean();
							bean.setName1(objectInfo.optString("name1"));
							bean.setName2(objectInfo.optString("name2"));
							bean.setName3(objectInfo.optString("name3"));
							bean.setUrl1(objectInfo.optString("url1"));
							bean.setUrl2(objectInfo.optString("url2"));
							bean.setUrl3(objectInfo.optString("url3"));
							categoryInfoList.add(bean);
						}
					}
				}
				return categoryInfoList;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}

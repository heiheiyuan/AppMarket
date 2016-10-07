package com.itheima.appmarket.protocol;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.text.TextUtils;
import com.itheima.appmarket.bean.SubjectInfo;

public class SubjectProtocol extends BaseProtocol<List<SubjectInfo>> {
	private List<SubjectInfo> subData = new ArrayList<SubjectInfo>();
	@Override
	public String getKey() {
		return "subject";
	}
	@Override
	public String getParams() {
		return "";
	}
	@Override
	public List<SubjectInfo> parseJson(String result) {
		if (!TextUtils.isEmpty(result)) {
			try {
				JSONArray jsonArray = new JSONArray(result);
				subData.clear();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					SubjectInfo info = new SubjectInfo();
					info.setDes(jsonObject.optString("des"));
					info.setUrl(jsonObject.optString("url"));
					subData.add(info);
				}
				return subData;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}

package com.itheima.appmarket.protocol;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import android.text.TextUtils;
import com.itheima.appmarket.http.HttpHelper;
import com.itheima.appmarket.http.HttpHelper.HttpResult;
import com.itheima.appmarket.utils.IOUtils;
import com.itheima.appmarket.utils.UIUtils;
public abstract class BaseProtocol<T> {
	//请求网路对应方法
	public T getData(int index) {
		//从缓存中取
		String dataLocal = getDataFromLocal(index);
		String result = null;
		if (!TextUtils.isEmpty(dataLocal)) {
			result = dataLocal;
		}else {
			//从网络中获取
			result = getDataFromNet(index);
		}
		return parseJson(result);
	}
	private String getDataFromNet(int index) {
		//发送请求
		HttpResult httpResult = HttpHelper.get(HttpHelper.URL + getKey() + "?index=" + index + getParams());
		String string = null;
		if (httpResult != null) {
			//获取返回的json字符串
			string = httpResult.getString();
			//将读取的文件存储至本地
			if (!TextUtils.isEmpty(string)) {
				writeToLoacal(index,string);
			}
		}
		return string;
	}
	//将json写入文件,并写入有效时长
	private void writeToLoacal(int index, String string) {
		BufferedWriter bw = null;
		try {
			//要写入文件的路径
			File cacheDir = UIUtils.getContext().getCacheDir();
			File file = new File(cacheDir,getKey() + index + getParams());
			bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
			//存储时长
			long time = System.currentTimeMillis() + 10 * 60 * 1000;
			//将存储时长写入第一行
			bw.write(time + "\r\n");
			//写入json
			bw.write(string.toCharArray());
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			IOUtils.close(bw);
		}
	}
	private String getDataFromLocal(int index) {
		BufferedReader br = null;
		try {
			//怎么存怎么取
			File cacheDir = UIUtils.getContext().getCacheDir();
			File file = new File(cacheDir,getKey() + index + getParams());
			br = new BufferedReader(new FileReader(file.getAbsoluteFile()));
			//读取有效时间
			String readLine = br.readLine();
			Long time = Long.valueOf(readLine);
			//判断是否在有效期内
			if (System.currentTimeMillis() < time) {
				//有效
				StringBuffer buffer = new StringBuffer();
				String temp;
				while ((temp = br.readLine()) != null) {
					buffer.append(temp);
				}
				return buffer.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(br);
		}
		return null;
	}
	//	请求的网络地址
	public abstract String getKey();
	//请求的参数
	public abstract String getParams();
	//每个页面解析的对象不同
	public abstract T parseJson(String result);
}

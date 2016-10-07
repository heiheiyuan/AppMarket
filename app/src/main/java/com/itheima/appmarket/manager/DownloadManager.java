package com.itheima.appmarket.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.content.Intent;
import android.net.Uri;

import com.itheima.appmarket.bean.AppInfo;
import com.itheima.appmarket.bean.DownloadInfo;
import com.itheima.appmarket.http.HttpHelper;
import com.itheima.appmarket.http.HttpHelper.HttpResult;
import com.itheima.appmarket.utils.IOUtils;
import com.itheima.appmarket.utils.UIUtils;

public class DownloadManager {
	//1,默认状态
	public static final int STATE_NONE = 1;
	//2,等待执行执行状态
	public static final int STATE_WAITTING = 2;
	//3，下载状态
	public static final int STATE_DOWNLOADING = 3;
	//4,暂停
	public static final int STATE_PAUSE = 4;
	//5，失败
	public static final int STATE_ERROR = 5;
	//6,成功状态
	public static final int STATE_DOWNLOADED = 6;

	//单例模式
	private DownloadManager(){};
	//创建唯一的一个对象
	private static DownloadManager downloadManager = new DownloadManager();
	//提供一个方法返回对应对象
	public static DownloadManager getInstance(){
		return downloadManager;
	}

	//封装下载过的对象的集合,如果是真实开发需要去创建数据库存储对应的DownloadInfo信息
	private Map<Long,DownloadInfo> downloadInfoMap = new ConcurrentHashMap<Long, DownloadInfo>();

	private List<DownloadObserver> observerList = new ArrayList<DownloadObserver>();

	//下载任务所在的集合
	private Map<Long,DownloadTask> downloadTaskMap = new ConcurrentHashMap<Long,DownloadTask>();

	//下载过程中伴随着状态的改变，进度条的改变，观察者模式(如果有跟之前操作有差异的话，就需要告知UI)

	public DownloadInfo getDownloadInfo(long id){
		return downloadInfoMap.get(id);
	}

	//注册观察者的方法
	public void registerObserver(DownloadObserver downloadObserver){
		synchronized(observerList){
			if(downloadObserver!=null){
				//1.1，添加对应观察者
				if(!observerList.contains(downloadObserver)){
					observerList.add(downloadObserver);
				}
			}
		}
	}

	//反注册观察者
	public void unRegisterObserver(DownloadObserver downloadObserver){
		synchronized(observerList){
			if(downloadObserver!=null){
				if(observerList.contains(downloadObserver)){
					observerList.remove(downloadObserver);
				}
			}
		}
	}

	//状态发生改变的时候调用的方法
	public void notifyDownloadStateChange(DownloadInfo downloadInfo){
		synchronized(observerList){
			//2，等到状态切换的时候，调用了当前方法
			if(downloadInfo!=null){
				//2.1 循环遍历
				for(DownloadObserver downloadObserver:observerList){
					//告知那个javabean做对应状态转变操作
					//2.2，观察者中的具体实现
					downloadObserver.onDownloadStateChange(downloadInfo);
				}
			}
		}
	}

	//进度条发生改变的时候调用的方法
	public void notifyDownloadProgressChange(DownloadInfo downloadInfo){
		synchronized(observerList){
			if(downloadInfo!=null){
				for(DownloadObserver downloadObserver:observerList){
					//告知那个javabean做对应状态转变操作
					downloadObserver.onDownloadProgressChange(downloadInfo);
				}
			}
		}
	}


	//创建一个观察者对象(状态的改变，监听进度条的改变)
	public interface DownloadObserver{
		public void onDownloadStateChange(DownloadInfo downloadInfo);
		public void onDownloadProgressChange(DownloadInfo downloadInfo);
	}

	public synchronized void download(AppInfo appInfo){
		if(appInfo!=null){
			//
			DownloadInfo downloadInfo = downloadInfoMap.get(appInfo.getId());
			if(downloadInfo==null){
				//之前没有下载过当前的应用，重新构建downloadInfo对象，用作下载，并且将其维护在map集合中
				downloadInfo = DownloadInfo.copy(appInfo);
				downloadInfoMap.put(appInfo.getId(), downloadInfo);
			}
			//下载逻辑,初始将状态变为等待，并且通知UI改变
			downloadInfo.setCurrentState(STATE_WAITTING);
			notifyDownloadStateChange(downloadInfo);
			//下载任务的维护
			DownloadTask downloadTask = new DownloadTask(downloadInfo);
			ThreadManager.getThreadProxyPool().execute(downloadTask);
			downloadTaskMap.put(downloadInfo.getId(), downloadTask);
		}
	}


	public synchronized void pause(AppInfo appInfo){
		if(appInfo!=null){
			DownloadInfo downloadInfo = downloadInfoMap.get(appInfo.getId());
			stopDownload(appInfo);
			//由下载状态，等待状态变成暂停，
			if(downloadInfo.getCurrentState() == STATE_WAITTING || 
					downloadInfo.getCurrentState() == STATE_DOWNLOADING){
				downloadInfo.setCurrentState(STATE_PAUSE);
				notifyDownloadStateChange(downloadInfo);
			}
		}
	}

	//安装方法，安装一个新的apk
	public synchronized void install(AppInfo appInfo){
		//转换成AppInfo--->downloadInfo,下载apk的存储路径，开启activity做安装操作
		stopDownload(appInfo);
		DownloadInfo downloadInfo = downloadInfoMap.get(appInfo.getId());
		if(downloadInfo!=null){
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setDataAndType(Uri.parse("file://"+downloadInfo.getPath()),"application/vnd.android.package-archive");
			UIUtils.getContext().startActivity(intent);
		}
	}

	private void stopDownload(AppInfo appInfo) {
		DownloadTask downloadTask = downloadTaskMap.get(appInfo.getId());
		if(downloadTask!=null){
			ThreadManager.getThreadProxyPool().cancel(downloadTask);
		}
	}

	class DownloadTask implements Runnable{
		private DownloadInfo downloadInfo;
		//构造方法中需要传递DownloadInfo对象，去确定下载的是那个一个apk
		public DownloadTask(DownloadInfo downloadInfo) {
			this.downloadInfo = downloadInfo;
		}
		public void run() {
			//正在下载操作,线程等待状态---->下载状态
			HttpResult httpResult;
			//读取流数据操作
			FileOutputStream fileOutputStream = null;
			InputStream inputStream = null;

			downloadInfo.setCurrentState(STATE_DOWNLOADING);
			notifyDownloadStateChange(downloadInfo);

			//1,重头下载
			String filePath = downloadInfo.getPath();
			File file = new File(filePath);
			if(!file.exists() || file.length()!=downloadInfo.getCurretPosition() 
					|| downloadInfo.getCurretPosition() == 0){
				downloadInfo.setCurretPosition(0);
				//原有文件删除
				file.delete();
				httpResult =  HttpHelper.download(HttpHelper.URL+"download?name="+downloadInfo.getDownloadUrl());
			}else{
				//2,断点续传
				httpResult = HttpHelper.download(HttpHelper.URL+"download?name="+downloadInfo.getDownloadUrl()
						+"&range="+downloadInfo.getCurretPosition());
			}
			if(httpResult!=null && (inputStream = httpResult.getInputStream())!=null){
				try {
					//true 续上原有的文件数据，接着向后写入
					fileOutputStream = new FileOutputStream(file,true);

					byte[] buffer = new byte[1024];
					int temp = -1;

					while((temp = inputStream.read(buffer))!=-1 && downloadInfo.getCurrentState() == STATE_DOWNLOADING){
						fileOutputStream.write(buffer, 0, temp);
						fileOutputStream.flush();

						downloadInfo.setCurretPosition(downloadInfo.getCurretPosition()+temp);
						notifyDownloadProgressChange(downloadInfo);
					}

				} catch (Exception e) {
					e.printStackTrace();
					//删除已经下载的文件
					file.delete();
					downloadInfo.setCurretPosition(0);
					//修改下载状态
					downloadInfo.setCurrentState(STATE_ERROR);
					notifyDownloadStateChange(downloadInfo);
				}finally{
					//关闭流，关闭连接操作
					IOUtils.close(fileOutputStream);
					IOUtils.close(inputStream);

					if(httpResult!=null){
						httpResult.close();
					}
				}

				//下载完毕，准备安装
				if(downloadInfo.getCurretPosition() == downloadInfo.getSize()){
					downloadInfo.setCurrentState(STATE_DOWNLOADED);
					notifyDownloadStateChange(downloadInfo);
				}else if(downloadInfo.getCurrentState() == STATE_PAUSE){
					//下载暂停
					notifyDownloadStateChange(downloadInfo);
				}else{
					//删除已经下载的文件
					file.delete();
					downloadInfo.setCurretPosition(0);
					//修改下载状态
					downloadInfo.setCurrentState(STATE_ERROR);
					notifyDownloadStateChange(downloadInfo);
				}
				//下载出错
			}else{
				//删除已经下载的文件
				file.delete();
				downloadInfo.setCurretPosition(0);
				//修改下载状态
				downloadInfo.setCurrentState(STATE_ERROR);
				notifyDownloadStateChange(downloadInfo);
			}

			//移除现有执行完操作的任务
			downloadTaskMap.remove(downloadInfo.getId());
		};
	}
}

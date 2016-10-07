package com.itheima.appmarket.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

public class ThreadManager {
	private static ThreadProxyPool threadProxyPool;
	private static Object object = new Object();
	
	public static ThreadProxyPool getThreadProxyPool(){
		synchronized (object) {
			if(threadProxyPool == null){
				threadProxyPool = new ThreadProxyPool(3,3, 5L);
			}
			return threadProxyPool;
		}
	}
	
	public static class ThreadProxyPool{
		private int corePoolSize;
		private int maximumPoolSize;
		private long keepAliveTime;
		private ThreadPoolExecutor threadPoolExecutor;
		public ThreadProxyPool(int corePoolSize,int maximumPoolSize,long keepAliveTime) {
			this.corePoolSize = corePoolSize;
			this.maximumPoolSize = maximumPoolSize;
			this.keepAliveTime = keepAliveTime;
		}
		//在子线程中执行任务的方法
		public void execute(Runnable runnable){
			if(runnable == null){
				return ;
			}
			if(threadPoolExecutor==null || threadPoolExecutor.isShutdown()){
				threadPoolExecutor = new ThreadPoolExecutor(
						//核心线程数
						corePoolSize, 
						//最大线程数
						maximumPoolSize, 
						//线程存活时间
						keepAliveTime, 
						//存活时间单位
						TimeUnit.MILLISECONDS, 
						//存放任务的队列
						new LinkedBlockingQueue<Runnable>(), 
						//创建线程工程
						Executors.defaultThreadFactory(), 
						//异常处理方式
						new AbortPolicy());
			}
			
			threadPoolExecutor.execute(runnable);
		}
		
		public void cancel(Runnable runnable){
			if(runnable!=null && threadPoolExecutor!=null && !threadPoolExecutor.isShutdown()){
				//从线程池中将任务移除出去
				threadPoolExecutor.getQueue().remove(runnable);
			}
		}
	}
}

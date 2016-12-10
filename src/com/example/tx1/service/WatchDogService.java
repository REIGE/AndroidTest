package com.example.tx1.service;

import java.util.ArrayList;
import java.util.List;

import com.example.tx1.EnterPsdActivity;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class WatchDogService extends Service {
	private boolean isWatch;
	private List<String> mPacknameList = new ArrayList<String>();
	private InnerReceiver mInnerReceiver;
	private String mSkipPackagename;

	@Override
	public void onCreate() {
		// 维护一个看门狗的死循环,让其时刻监测现在开启的应用,是否为程序锁中要去拦截的应用

		isWatch = true;
		watch();

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.intent.action.SKIP");

		mInnerReceiver = new InnerReceiver();

		// 需要注册的接收者 广播接收者过滤器
		registerReceiver(mInnerReceiver, intentFilter);

		super.onCreate();
	}

	class MyContentObserver extends ContentObserver {

		public MyContentObserver(Handler handler) {
			super(handler);
		}

		// 一旦数据库发生改变时候调用方法,重新获取包名所在集合的数据
		@Override
		public void onChange(boolean selfChange) {
			new Thread() {
				public void run() {

				};
			}.start();
			super.onChange(selfChange);
		}
	}

	class InnerReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// 获取发送广播过程中传递过来的包名,跳过次包名检测过程
			mSkipPackagename = intent.getStringExtra("packagename");
		}
	}

	private void watch() {

		mPacknameList.add("com.tencent.mqq");
		mPacknameList.add("com.tencent.mqq.SplashActivity");
		mPacknameList.add("com.tencent.qq");
		mPacknameList.add("com.example.tx1");

		// 1,子线程中,开启一个可控死循环
		new Thread() {
			public void run() {
				while (isWatch) {
					Log.w("aaa", "gogogogogogogoggoog");
					// 监测现在正在开启的应用,任务栈
					// 获取activity管理者对象
					ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
					// 获取正在开启应用的任务栈
					List<RunningTaskInfo> runningTasks = am.getRunningTasks(1);

					Log.w("aa", "" + runningTasks.size() + "");

					RunningTaskInfo runningTaskInfo = runningTasks.get(0);
					// 获取栈顶的activity,然后在获取此activity所在应用的包名
					String packagename = runningTaskInfo.topActivity.getPackageName();

					// 拿此包名在已加锁的包名集合中去做比对,如果包含次包名,则需要弹出拦截界面
					if (mPacknameList.contains(packagename)) {
						// 如果现在检测的程序,已经解锁了,则不需要去弹出拦截界面
						if (!packagename.equals(mSkipPackagename)) {
							// 弹出拦截界面
							Log.w("aa", "******************************************");
							Intent intent = new Intent(getApplicationContext(), EnterPsdActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							intent.putExtra("packagename", packagename);
							startActivity(intent);
						}
					}
					// 睡眠一下,时间片轮转
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();

	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onDestroy() {
		// 停止看门狗循环
		isWatch = false;
		// 注销广播接受者
		if (mInnerReceiver != null) {
			unregisterReceiver(mInnerReceiver);
		}
		super.onDestroy();
	}
}

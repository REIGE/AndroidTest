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
		// ά��һ�����Ź�����ѭ��,����ʱ�̼�����ڿ�����Ӧ��,�Ƿ�Ϊ��������Ҫȥ���ص�Ӧ��

		isWatch = true;
		watch();

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.intent.action.SKIP");

		mInnerReceiver = new InnerReceiver();

		// ��Ҫע��Ľ����� �㲥�����߹�����
		registerReceiver(mInnerReceiver, intentFilter);

		super.onCreate();
	}

	class MyContentObserver extends ContentObserver {

		public MyContentObserver(Handler handler) {
			super(handler);
		}

		// һ�����ݿⷢ���ı�ʱ����÷���,���»�ȡ�������ڼ��ϵ�����
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
			// ��ȡ���͹㲥�����д��ݹ����İ���,�����ΰ���������
			mSkipPackagename = intent.getStringExtra("packagename");
		}
	}

	private void watch() {

		mPacknameList.add("com.tencent.mqq");
		mPacknameList.add("com.tencent.mqq.SplashActivity");
		mPacknameList.add("com.tencent.qq");
		mPacknameList.add("com.example.tx1");

		// 1,���߳���,����һ���ɿ���ѭ��
		new Thread() {
			public void run() {
				while (isWatch) {
					Log.w("aaa", "gogogogogogogoggoog");
					// ����������ڿ�����Ӧ��,����ջ
					// ��ȡactivity�����߶���
					ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
					// ��ȡ���ڿ���Ӧ�õ�����ջ
					List<RunningTaskInfo> runningTasks = am.getRunningTasks(1);

					Log.w("aa", "" + runningTasks.size() + "");

					RunningTaskInfo runningTaskInfo = runningTasks.get(0);
					// ��ȡջ����activity,Ȼ���ڻ�ȡ��activity����Ӧ�õİ���
					String packagename = runningTaskInfo.topActivity.getPackageName();

					// �ô˰������Ѽ����İ���������ȥ���ȶ�,��������ΰ���,����Ҫ�������ؽ���
					if (mPacknameList.contains(packagename)) {
						// ������ڼ��ĳ���,�Ѿ�������,����Ҫȥ�������ؽ���
						if (!packagename.equals(mSkipPackagename)) {
							// �������ؽ���
							Log.w("aa", "******************************************");
							Intent intent = new Intent(getApplicationContext(), EnterPsdActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							intent.putExtra("packagename", packagename);
							startActivity(intent);
						}
					}
					// ˯��һ��,ʱ��Ƭ��ת
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
		// ֹͣ���Ź�ѭ��
		isWatch = false;
		// ע���㲥������
		if (mInnerReceiver != null) {
			unregisterReceiver(mInnerReceiver);
		}
		super.onDestroy();
	}
}

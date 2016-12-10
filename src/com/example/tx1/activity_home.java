package com.example.tx1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class activity_home extends Activity {
	private static final int MUSIC_PLAYER = 0;
	private String[] mTitileStrs;
	private int[] mDrawableIds;
	private GridView gv_home;
	Context mContext = this;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		// 初始化UI
		initUI();
		// 初始化数据
		initData();
	}

	private void initUI() {
		gv_home = (GridView) findViewById(R.id.gv_home);
	}

	private void initData() {
		mTitileStrs = new String[] { "播放器", "摄像头", "电筒", "短信", "网络", "定位", "安全锁", "地图", "计算器" };
		mDrawableIds = new int[] { R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
				R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
				R.drawable.ic_launcher, R.drawable.ic_launcher };
		gv_home.setAdapter(new MyAdapter());
		gv_home.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case MUSIC_PLAYER:
					MusicPlayer();
					break;
				case 1:
					Intent intent = new Intent();
					intent.setClass(mContext, activity_carmer.class);
					startActivity(intent);
					break;
				case 2:
					Intent intent_flash = new Intent(mContext, activity_flash.class);
					startActivity(intent_flash);

					break;
				case 3:
					Intent intent_sms = new Intent(mContext, activity_sms.class);
					startActivity(intent_sms);
					break;
				case 4:
					Intent intent_network = new Intent(mContext, activity_network.class);
					startActivity(intent_network);

					break;
				case 5:
					Intent intent_location = new Intent(mContext, activity_location.class);
					startActivity(intent_location);

					break;

				case 6:
					Intent intent_appLock = new Intent(mContext, activity_appLock.class);
					startActivity(intent_appLock);

					break;
				case 7:
					Intent intent_baidu = new Intent(mContext, activity_baidu.class);
					startActivity(intent_baidu);
					
					break;
				default:
					break;
				}
			}
		});
	}

	protected void MusicPlayer() {
		Intent intent = new Intent();
		intent.setClass(mContext, activity_player.class);
		startActivity(intent);

	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mTitileStrs.length;
		}

		@Override
		public Object getItem(int position) {
			return mTitileStrs[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 复用convertView
			View view = null;
			if (convertView != null) {
				view = convertView;
			} else {
				view = View.inflate(mContext, R.layout.gridview_item, null);
			}

			ImageView iv_item = (ImageView) view.findViewById(R.id.iv_item);
			TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
			iv_item.setBackgroundResource(mDrawableIds[position]);
			tv_title.setText(mTitileStrs[position]);

			return view;
		}

	}
}

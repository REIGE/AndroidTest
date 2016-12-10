package com.example.tx1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author cr
 *
 */
public class activity_network extends Activity {
	private ConnectivityManager manager;
	private TextView tv_network_on;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_network);

		Button bt_network_setting = (Button) findViewById(R.id.bt_network_setting);
		tv_network_on = (TextView) findViewById(R.id.tv_network_on);

		bt_network_setting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				NetworkSet();
			}
		});

		isNetworkAvailable();
	}

	protected void NetworkSet() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("网络提示信息");
		builder.setMessage("将要跳转至网络设置界面！");
		builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = null;
				// 判断手机系统的版本！如果API大于10 就是3.0+ 因为3.0以上的版本的设置和3.0以下的设置不一样，调用的方法不同
				if (android.os.Build.VERSION.SDK_INT > 10) {
					intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
				} else {
					intent = new Intent();
					ComponentName component = new ComponentName("com.android.settings",
							"com.android.settings.WirelessSettings");
					intent.setComponent(component);
					intent.setAction("android.intent.action.VIEW");
				}
				startActivity(intent);
			}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.create();
		builder.show();
	}

	/**
	 * 网络是否连接
	 */
	private boolean isNetworkAvailable() {
		boolean flag = false;
		manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		// 去进行判断网络是否连接
		if (manager.getActiveNetworkInfo() != null) {
			flag = manager.getActiveNetworkInfo().isAvailable();
		}

		if (!flag) {
			tv_network_on.setText("网络未连接");
		} else {
			// 网络已连接，检查网络状态
			checkNetworkState();
		}
		return flag;
	}

	/**
	 * 判断网络类型
	 */
	private void checkNetworkState() {

		State gprs = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		// 判断为gprs状态
		if (gprs == State.CONNECTED || gprs == State.CONNECTING) {
			tv_network_on.setText("网络已连接：GPRS");
		}
		// 判断为wifi状态
		if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
			tv_network_on.setText("网络已连接：WIFI");
		}
	}
}

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
		builder.setTitle("������ʾ��Ϣ");
		builder.setMessage("��Ҫ��ת���������ý��棡");
		builder.setPositiveButton("����", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = null;
				// �ж��ֻ�ϵͳ�İ汾�����API����10 ����3.0+ ��Ϊ3.0���ϵİ汾�����ú�3.0���µ����ò�һ�������õķ�����ͬ
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

		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.create();
		builder.show();
	}

	/**
	 * �����Ƿ�����
	 */
	private boolean isNetworkAvailable() {
		boolean flag = false;
		manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		// ȥ�����ж������Ƿ�����
		if (manager.getActiveNetworkInfo() != null) {
			flag = manager.getActiveNetworkInfo().isAvailable();
		}

		if (!flag) {
			tv_network_on.setText("����δ����");
		} else {
			// ���������ӣ��������״̬
			checkNetworkState();
		}
		return flag;
	}

	/**
	 * �ж���������
	 */
	private void checkNetworkState() {

		State gprs = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		// �ж�Ϊgprs״̬
		if (gprs == State.CONNECTED || gprs == State.CONNECTING) {
			tv_network_on.setText("���������ӣ�GPRS");
		}
		// �ж�Ϊwifi״̬
		if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
			tv_network_on.setText("���������ӣ�WIFI");
		}
	}
}

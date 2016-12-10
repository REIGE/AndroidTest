package com.example.tx1;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class activity_sms extends Activity {
	private TextView et_sms_num;
	private TextView et_sms_data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms);

		Button bt_sms_send = (Button) findViewById(R.id.bt_sms_send);
		et_sms_num = (TextView) findViewById(R.id.et_sms_num);
		et_sms_data = (TextView) findViewById(R.id.et_sms_data);

		bt_sms_send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String num = et_sms_num.getText().toString();
				String data = et_sms_data.getText().toString();

				//如果有一个输入框是空的 不做任何操作
				if (TextUtils.isEmpty(num) || TextUtils.isEmpty(data))
					return;
				SmsManager SM = SmsManager.getDefault();
				SM.sendTextMessage(num, null, data, null, null);
				et_sms_num.setText("");
				et_sms_data.setText("");
				Toast.makeText(getApplicationContext(), "已发送", Toast.LENGTH_SHORT).show();
			}
		});

	}
}

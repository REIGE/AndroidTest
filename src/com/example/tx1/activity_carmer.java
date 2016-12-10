package com.example.tx1;

import com.google.zxing.WriterException;
import com.zxing.activity.CaptureActivity;
import com.zxing.encoding.EncodingHandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class activity_carmer extends Activity {
	Context mContext = this;
	private TextView tv_QR_data;
	private EditText et_QR;
	private ImageView iv_QR;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_carmer);
		Button bt_open_camera = (Button) findViewById(R.id.bt_open_camera);
		Button bt_creat_QR = (Button) findViewById(R.id.bt_creat_QR);
		iv_QR = (ImageView) findViewById(R.id.iv_QR);
		et_QR = (EditText) findViewById(R.id.et_QR);
		tv_QR_data = (TextView) findViewById(R.id.tv_QR_data);
		bt_open_camera.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mContext, CaptureActivity.class);
				startActivityForResult(intent, 0);
			}
		});
		bt_creat_QR.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String in = et_QR.getText().toString().trim();
				if (TextUtils.isEmpty(in)) {
					Toast.makeText(mContext, "输入需要转换的文字 蠢货", 0).show();
				} else {
					try {
						Bitmap QRCode = EncodingHandler.createQRCode(in, 800);
						iv_QR.setImageBitmap(QRCode);
					} catch (WriterException e) {
						e.printStackTrace();
					}
				}

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			String result = data.getExtras().getString("result").trim();
			tv_QR_data.setText(result);
		}
	}

}

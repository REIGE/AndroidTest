package com.example.tx1;



import com.example.tx1.service.WatchDogService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class activity_appLock extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_lock);

		Button bt_start_lock = (Button) findViewById(R.id.bt_start_lock);
		Button bt_end_lock = (Button) findViewById(R.id.bt_end_lock);
		bt_start_lock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startService(new Intent(getApplicationContext(), WatchDogService.class));
			}
		});

		bt_end_lock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				stopService(new Intent(getApplicationContext(), WatchDogService.class));
			}
		});
	}
}

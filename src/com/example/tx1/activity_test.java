package com.example.tx1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class activity_test extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView textView = new TextView(this);
		textView.setText("activity_test");
		textView.setTextSize(20);
		setContentView(textView);
	}

}

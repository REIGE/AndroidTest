package com.example.tx1;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class activity_location extends Activity {
	private TextView tv_location;

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		setContentView(R.layout.activity_location);

		tv_location = (TextView) findViewById(R.id.tv_location);

		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setCostAllowed(true);
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		String bestProvider = lm.getBestProvider(criteria, true);

		MyLocationListener myLocationListener = new MyLocationListener();
		lm.requestLocationUpdates(bestProvider, 0, 0, myLocationListener);

	}

	class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			// 经度
			double longitude = location.getLongitude();
			// 纬度
			double latitude = location.getLatitude();

			// 获取经纬度需要添加权限
			tv_location.setText("longitude = " + longitude + "\nlatitude = " + latitude);

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// GPS状态发生改变的事件监听
		}

		@Override
		public void onProviderEnabled(String provider) {
			// GPS关闭的时候的事件监听
			tv_location.setText("GPS已关闭");

		}

		@Override
		public void onProviderDisabled(String provider) {
			// GPS关闭的时候的事件监听
		}

	}
}

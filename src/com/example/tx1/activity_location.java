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
			// ����
			double longitude = location.getLongitude();
			// γ��
			double latitude = location.getLatitude();

			// ��ȡ��γ����Ҫ���Ȩ��
			tv_location.setText("longitude = " + longitude + "\nlatitude = " + latitude);

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// GPS״̬�����ı���¼�����
		}

		@Override
		public void onProviderEnabled(String provider) {
			// GPS�رյ�ʱ����¼�����
			tv_location.setText("GPS�ѹر�");

		}

		@Override
		public void onProviderDisabled(String provider) {
			// GPS�رյ�ʱ����¼�����
		}

	}
}

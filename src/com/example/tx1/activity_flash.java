package com.example.tx1;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

public class activity_flash extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash);
		Switch sch_flash = (Switch) findViewById(R.id.sch_flash);

		sch_flash.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			private Camera camera;
			private Parameters parameter;

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					camera = Camera.open();
					camera.startPreview();
					parameter = camera.getParameters();
					parameter.setFlashMode(Parameters.FLASH_MODE_TORCH);
					camera.setParameters(parameter);
				} else {
					// πÿ±’ ÷µÁÕ≤
					if (camera == null)
						camera = Camera.open();
					parameter = camera.getParameters();
					parameter.setFlashMode(Parameters.FLASH_MODE_OFF);
					camera.setParameters(parameter);
					camera.release();
				}
			}
		});
	}
}

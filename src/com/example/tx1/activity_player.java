package com.example.tx1;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class activity_player extends Activity {
	private MediaPlayer mPlayer;
	private Button bt_start;
	private Button bt_pause;
	private Button bt_stop;
	private Button bt_up;
	private Button bt_down;
	private Button bt_loop;
	private SeekBar sb_progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		mPlayer = MediaPlayer.create(this, R.raw.music);
		initUI();
	}

	private void initUI() {
		bt_start = (Button) findViewById(R.id.bt_start);
		bt_pause = (Button) findViewById(R.id.bt_pause);
		bt_stop = (Button) findViewById(R.id.bt_stop);
		bt_up = (Button) findViewById(R.id.bt_up);
		bt_down = (Button) findViewById(R.id.bt_down);
		bt_loop = (Button) findViewById(R.id.bt_loop);
		sb_progress = (SeekBar) findViewById(R.id.sb_progress);

		bt_start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ����
				mPlayer.start();
			}
		});
		bt_pause.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ��ͣ
				mPlayer.pause();
			}
		});
		bt_stop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ֹͣ
				mPlayer.stop();
			}
		});
		bt_up.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ��������
				mPlayer.setVolume(1.0f, 1.0f);
			}
		});
		bt_down.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ��С����
				mPlayer.setVolume(0.0f, 0.0f);
			}
		});
		bt_loop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ��������ѭ������
				mPlayer.setLooping(true);
			}
		});
		sb_progress.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// ��ȡseekbar��ǰ����ֵ
				int progress = seekBar.getProgress();
				// ��ȡ������ʱ��
				int mMax = mPlayer.getDuration();
				// ��ȡseekbar������ֵ
				int sMax = seekBar.getMax();
				// ���ŵ�ǰseekbar��������Ӧ������
				mPlayer.seekTo(mMax * progress / sMax);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// TODO Auto-generated method stub

			}
		});

	}
}

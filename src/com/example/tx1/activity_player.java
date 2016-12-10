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
				// 播放
				mPlayer.start();
			}
		});
		bt_pause.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 暂停
				mPlayer.pause();
			}
		});
		bt_stop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 停止
				mPlayer.stop();
			}
		});
		bt_up.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 增大音量
				mPlayer.setVolume(1.0f, 1.0f);
			}
		});
		bt_down.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 减小音量
				mPlayer.setVolume(0.0f, 0.0f);
			}
		});
		bt_loop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 设置音乐循环播放
				mPlayer.setLooping(true);
			}
		});
		sb_progress.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// 获取seekbar当前进度值
				int progress = seekBar.getProgress();
				// 获取音乐总时长
				int mMax = mPlayer.getDuration();
				// 获取seekbar最大进度值
				int sMax = seekBar.getMax();
				// 播放当前seekbar进度所对应的内容
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

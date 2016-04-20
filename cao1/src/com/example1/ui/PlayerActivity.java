package com.example1.ui;

import java.io.File;

import com.example.R;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.VideoView;

public class PlayerActivity extends Activity implements OnClickListener{
	
	private Button playButton,pauseButton,stopButton,button1,button2,button3;
	private MediaPlayer mediaPlayer=new MediaPlayer();
	private VideoView videoView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player);
		playButton=(Button)this.findViewById(R.id.play);
		pauseButton=(Button)this.findViewById(R.id.pause);
		stopButton=(Button)this.findViewById(R.id.stop);
		button1=(Button)this.findViewById(R.id.pbutton1);
		button2=(Button)this.findViewById(R.id.pbutton2);
		button3=(Button)this.findViewById(R.id.pbutton3);
		videoView=(VideoView)this.findViewById(R.id.videoView1);
		playButton.setOnClickListener(this);
		pauseButton.setOnClickListener(this);
		stopButton.setOnClickListener(this);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);

		initmedia();
		initvideo();
	}
	void initvideo(){
		File file=new File(Environment.getExternalStorageDirectory(),"netroid/download/m.mp4");
		videoView.setVideoPath(file.getPath());
		
	}
	void initmedia(){
		try {
			File file=new File(Environment.getExternalStorageDirectory(),"MIUI/music/mp3/不语_许嵩_不语.mp3");
			mediaPlayer.setDataSource(file.getPath());
			mediaPlayer.prepare();//准备状态
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.play:
			if (!mediaPlayer.isPlaying()) {
				mediaPlayer.start();
			}
			break;
		case R.id.pause:
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.pause();
			}
			break;
		case R.id.stop:
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.reset();
				initmedia();
			}
			break;
		case R.id.pbutton1:
			if (!videoView.isPlaying()) {
				videoView.start();
			}
			break;
		case R.id.pbutton2:
					if (videoView.isPlaying()) {
						videoView.pause();
					}
					break;
		case R.id.pbutton3:
			if (videoView.isPlaying()) {
				videoView.resume();
			}
			break;
		default:
			break;
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mediaPlayer!=null) {
			mediaPlayer.stop();mediaPlayer.release();
		}
	}
}

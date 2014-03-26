package com.sovejo.lexdroidejemplosonidos;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.graphics.Color;

public class MainActivity extends Activity implements OnTouchListener, android.view.View.OnClickListener
{
	//SoundPool
	private SoundPool soundPool;
	boolean loaded = false;
	private int soundID;
	
	//MediaPlayer
	public MediaPlayer mediaPlayer;
	Button btPlay, btStop;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//SoundPool
		final TextView textView = (TextView)findViewById(R.id.textView01);
		textView.setOnTouchListener(this);
		
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() 
		{	
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId, int status) 
			{
				// TODO Auto-generated method stub
				loaded = true;
				textView.setTextColor(Color.GREEN);
			}
		});
		
		soundID = soundPool.load(this, R.raw.main_theme, 1);
		
		
		//MediaPlayer
		btPlay = (Button) findViewById(R.id.btPlay);
		btStop = (Button) findViewById(R.id.btStop);
		
		btPlay.setOnClickListener(this);
		btStop.setOnClickListener(this);
		
		this.statePressed(btStop);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) 
	{
		// TODO Auto-generated method stub
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
			
			float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
			float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			float volume = actualVolume/maxVolume;
			
			if(loaded)
			{
				soundPool.play(soundID, volume, volume, 1, 0, 1f);
				Log.e("Prueba", "Played sound");
			}
		}
		
		return false;
	}

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		switch (v.getId()) 
		{
			case R.id.btPlay:
				play_mp();
				break;

			case R.id.btStop:
				stop_mp();
				break;	
				
			default:
				break;
		}
	}

	private void play_mp()
	{
		mediaPlayer = MediaPlayer.create(this, R.raw.main_theme);
		mediaPlayer.start();
		
		this.statePressed(btPlay);
		this.stateNormal(btStop);
	}
	
	private void stop_mp()
	{
		if(mediaPlayer != null && mediaPlayer.isPlaying())
		{
			mediaPlayer.stop();
			
			this.statePressed(btStop);
			this.stateNormal(btPlay);
		}
	}
	
	private void statePressed(Button bt)
	{
		bt.setClickable(false);
		bt.setTextColor(Color.GRAY);
	}
	
	private void stateNormal(Button bt)
	{
		bt.setClickable(true);
		bt.setTextColor(Color.BLACK);
	}
}

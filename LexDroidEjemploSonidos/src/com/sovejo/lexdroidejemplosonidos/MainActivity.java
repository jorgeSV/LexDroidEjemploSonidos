package com.sovejo.lexdroidejemplosonidos;

import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.app.Activity;

public class MainActivity extends Activity implements OnTouchListener
{
	private SoundPool soundPool;
	boolean loaded = false;
	private int soundID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		View view = findViewById(R.id.textView01);
		view.setOnTouchListener(this);
		
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() 
		{	
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId, int status) 
			{
				// TODO Auto-generated method stub
				loaded = true;
			}
		});
		
		soundID = soundPool.load(this, R.raw.main_theme, 1);
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
	
	
}

package com.example.edmdroid;

import java.io.IOException;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.os.Debug;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.media.SoundPool;

public class MainActivity extends Activity implements SensorEventListener, OnClickListener{
	private boolean mInitialized; 
	private SensorManager mSensorManager; 
	private Sensor mAccelerometer; 
	private final float NOISE = (float) 2.0;
	private MediaPlayer mp, mp2, mp3;
	private boolean loaded = false;
	float fSpeed = 1.0f;
	private float mLastX, mLastY, mLastZ;
	private boolean canPlay = false;
	private Button b;
	
    private int soundID, soundID2, soundID3;
    private boolean isPlaying1, isPlaying2, isPlaying3 = false;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mInitialized = false;
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		b = (Button) findViewById(R.id.play);
		b.setOnClickListener(this);
        mp = new MediaPlayer();
		
	}

	

	

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		
		//mp = MediaPlayer.create(getBaseContext(), R.raw.skrillex1);
        //mp.start();
        TextView tvX= (TextView)findViewById(R.id.x_axis);
        TextView tvY= (TextView)findViewById(R.id.y_axis);
        TextView tvZ= (TextView)findViewById(R.id.z_axis);
      
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        
        float deltaX = Math.abs(mLastX - x);
        float deltaY = Math.abs(mLastY - y);
        float deltaZ = Math.abs(mLastZ - z);
        if (deltaX < NOISE) deltaX = (float)0.0;
        if (deltaY < NOISE) deltaY = (float)0.0;
        if (deltaZ < NOISE) deltaZ = (float)0.0;
        mLastX = x;
        mLastY = y;
        mLastZ = z;
        tvX.setText(Float.toString(x));
        tvY.setText(Float.toString(y));
        tvZ.setText(Float.toString(z));
        
        if(canPlay)
        {
        	playMusic(x, y, z);	
        }
        
        

		
	}
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		}
		protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
		}





		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}
		
		public void playMusic(float x, float y, float z)
		{
			
	        
			if(y < 8)
			{
				
		        if(!mp.isPlaying())
		        {
		           mp = MediaPlayer.create(this, R.raw.skrillex1);
		           mp.start();
		        }
			}else if(y > 9)
			{
				if(!mp.isPlaying())
		        {
		           mp = MediaPlayer.create(this, R.raw.skrillex2);
		           mp.start();
		        }
			}else if(y > 7 && y < 9)
			{
				if(!mp.isPlaying())
		        {
		           mp = MediaPlayer.create(this, R.raw.skrillex3);
		           mp.start();
		        }
			}else if(x > 3)
			{
				if(!mp.isPlaying())
		        {
		           mp = MediaPlayer.create(this, R.raw.knifeparty1);
		           mp.start();
		        }
			}else if(x < -3)
			{
				if(!mp.isPlaying())
		        {
		           mp = MediaPlayer.create(this, R.raw.knifeparty2);
		           mp.start();
		        }
			}
				
		}





		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId())
			{
			case(R.id.play):
				
				if(canPlay)
				{
					canPlay = false;
					b.setText("Play");
				}else
				{canPlay = true;
				b.setText("Stop");
				}
			}
			
		}

}

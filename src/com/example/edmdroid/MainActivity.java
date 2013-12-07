package com.example.edmdroid;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener, OnClickListener{
	private SensorManager mSensorManager; 
	private Sensor mAccelerometer; 
	private MediaPlayer mp;
	float fSpeed = 1.0f;
	private boolean canPlay = false;
	private Button b;

	@SuppressLint("Wakelock")
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		b = (Button) findViewById(R.id.play);
		b.setOnClickListener(this);
		mp = new MediaPlayer();
		
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		@SuppressWarnings("deprecation")
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
		wl.acquire();
	}


	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub

		
		
		TextView tvX= (TextView)findViewById(R.id.x_axis);
		TextView tvY= (TextView)findViewById(R.id.y_axis);
		TextView tvZ= (TextView)findViewById(R.id.z_axis);

		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];


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

	}

	public void playMusic(float x, float y, float z)
	{

		if ( x > -1 && x < 1)
		{
			///Vertical
			
			if(y < 8 && z > 3) //tilt back
			{

				if(!mp.isPlaying()) 
				{
					mp = MediaPlayer.create(this, R.raw.skrillex1);
					mp.start();
				}
			}else if(y > 9 && z > -3 && z < 3) // straight up
			{
				if(!mp.isPlaying())
				{
					mp = MediaPlayer.create(this, R.raw.skrillex2);
					mp.start();
				}
			}else if(y > 7 && y < 9 && z < 0)   // tilt closer
			{
				if(!mp.isPlaying())
				{
					mp = MediaPlayer.create(this, R.raw.skrillex3);
					mp.start();
				}
			}
		}else if( x > 1) // tilted left
		{
			if(z > 3)
			{
				if(!mp.isPlaying())
				{
					mp = MediaPlayer.create(this, R.raw.goingtodie); //left tilt back
					mp.start();
				}
			}else if( z > -3 && z < 3)
			{
				if(!mp.isPlaying())
				{
					mp = MediaPlayer.create(this, R.raw.knifeparty2); // left 
					mp.start();
				}
			}else if( z < 0)
			{
				if(!mp.isPlaying())
				{
					mp = MediaPlayer.create(this, R.raw.bassdrop); // left tilt closer
					mp.start();
				}
			}

		}else if( x < -1)
		{
			// Tilted Right
			if(z > 3)
			{
				if(!mp.isPlaying())
				{
					mp = MediaPlayer.create(this, R.raw.knifeparty3); //right tilt back
					mp.start();
				}
			}else if( z > -3 && z < 3)
			{
				if(!mp.isPlaying())
				{
					mp = MediaPlayer.create(this, R.raw.knifeparty2); // right
					mp.start();
				}
			}else if( z < -3)
			{
				if(!mp.isPlaying())
				{
					mp = MediaPlayer.create(this, R.raw.knifeparty4); // right tilt closer
					mp.start();
				}
			}
		}
	}


	@Override
	public void onClick(View v) {
		// enables and disables playback
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

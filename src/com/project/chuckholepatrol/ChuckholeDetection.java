package com.project.chuckholepatrol;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.achartengine.GraphicalView;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;


public class ChuckholeDetection extends Activity implements SensorEventListener, OnClickListener {

	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private static GraphicalView view;
	private ArrayList<AccelData> sensordata;
	private LocationManager locationmanager;
	public static volatile  double lat, lng;
	private Handler mhandler;
	private boolean enabled;
	MediaPlayer mp = null;
	//private Thread tgps;
	//private HandlerThread mThread;
	LineGraph line = new LineGraph();
	TextView x_acc , y_acc , z_acc, gps;
	ImageView stop, start, send, place;
	DatabaseConnector dbConnector;
	int i = 1;
	
	
	private synchronized double getlat()
	{
		return lat;
	}
	
	private synchronized double getlng()
	{
		return lng;
	}
	
	private synchronized void setlat(double lati)
	{
		lat = lati;
	}
	
	private synchronized void setlng(double lngi)
	{
		lng = lngi;
	}
	
	  private LocationListener mLocationListener = new
			  LocationListener() {

				public void onLocationChanged(Location location) {
					// TODO Auto-generated method stub
				//	synchronized(this) {
						
					setlat(location.getLatitude());
					setlng(location.getLongitude());
					System.out.println("lat " + lat + "lng " + lng);
				//	}
					/*long timestamp = System.currentTimeMillis();
					Log.d("GPS time", String.valueOf(timestamp));
					Log.d("GPS Lat", String.valueOf(lat));
					Log.d("GPS Lng", String.valueOf(lng));*/
				}

				public void onProviderDisabled(String provider) {
					// TODO Auto-generated method stub
					
				}

				public void onProviderEnabled(String provider) {
					// TODO Auto-generated method stub
					
				}

				public void onStatusChanged(String provider, int status,
						Bundle extras) {
					// TODO Auto-generated method stub
					
				}

			            
			          };
			          			          			          			        
			          
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuckhole_detection);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
       dbConnector = new DatabaseConnector(ChuckholeDetection.this);
       mp = MediaPlayer.create(this, R.raw.beep);
       locationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
       enabled = locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER);
       
        if (!enabled) {
        	
        	Intent intent = new Intent(ChuckholeDetection.this, StartGps.class);
        	startActivityForResult(intent, 0);
			 
			} 
        
        else
        	startDetection();
        
    /*    mThread = new HandlerThread("GPS Thread");
        mThread.start();
        new Handler(mThread.getLooper()).post(
                new Runnable() {
                        public void run() {

                        	locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
                        }
                });*/
//}
        
       // getActionBar().setDisplayHomeAsUpEnabled(true);
    }
	
	
	public void startDetection()
	{
		  /*tgps = new Thread(){
	        	@Override
	        	public void run()
	        	{
	        		Looper.prepare();
	        		mhandler = new Handler();
	        		System.out.println("Inside thread " + getId());
	        		locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, mLocationListener, mhandler.getLooper());
	        		Looper.loop();
	        	}
	        };
		 tgps.start();*/
		startGps tgps = new startGps();
		tgps.start();
	        
	        i = 1;
	        sensordata = new ArrayList<AccelData>();
	        LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
	    	view = line.getView(this);
	    	layout.addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	        x_acc = (TextView) findViewById(R.id.x_acc);
	        y_acc = (TextView) findViewById(R.id.y_acc);
	        z_acc = (TextView) findViewById(R.id.z_acc);
	        stop = (ImageView) findViewById(R.id.btn_stop);
	        start = (ImageView) findViewById(R.id.btn_play);
	        place = (ImageView) findViewById(R.id.btn_location);
	        send = (ImageView) findViewById(R.id.btn_send);
	        start.setOnClickListener(ChuckholeDetection.this);
			place.setOnClickListener(ChuckholeDetection.this);
			send.setOnClickListener(ChuckholeDetection.this);
	        start.setEnabled(false);
	        place.setEnabled(false);
	        send.setEnabled(false);
	        stop.setOnClickListener(this);
	        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_CANCELED)
			ChuckholeDetection.this.finish();
		else
		{
			enabled = locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			if(enabled)
				startDetection();
			else
				ChuckholeDetection.this.finish();
				
		}
		
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		dbConnector.deleteData();
	}


	private class SaveData extends AsyncTask<String, Object, Object > 
    {       

       @Override
       protected Object doInBackground(String... pass)
       {
		
          //dbConnector.open();
    	  // String[] data = pass;
          dbConnector.insertContact(pass);
          //return dbConnector.getAllContacts(); 
		return null;
       } 
       
       @Override
       protected void onPostExecute(Object result)
       {
        //  conAdapter.changeCursor(result); // set the adapter's Cursor
          dbConnector.close();
       } 
    } 

    public void onClick(View src) {
		// TODO Auto-generated method stub
    	switch(src.getId())
    	{
    	
    		case R.id.btn_play:
    			startGps tgps = new startGps();
    			tgps.start();
    			stop.setEnabled(true);
    			start.setEnabled(false);
    			place.setEnabled(false);
    			send.setEnabled(false);
    			mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    			break;
    			
    		case R.id.btn_stop:
    			mSensorManager.unregisterListener(this);
    			locationmanager.removeUpdates(mLocationListener);
    			mhandler.getLooper().quit();
    			stop.setEnabled(false);
    			start.setEnabled(true);
    			place.setEnabled(true);
    			send.setEnabled(true);
    			break;
    			
    		case R.id.btn_location:    			
    			Intent viewchk = new Intent(ChuckholeDetection.this, ViewMapActivity.class);
    			startActivity(viewchk);
    			
    		case R.id.btn_send:    			
    			Intent send = new Intent(ChuckholeDetection.this, SendData.class);
    			startActivity(send);
    			
    	}
    	
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
		
			mSensorManager.unregisterListener(this);
			locationmanager.removeUpdates(mLocationListener);		
			mhandler.getLooper().quit();
			
		}
		//mThread.getLooper().quit();
		
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(!locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
		
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChuckholeDetection.this);

			   // set title
			   alertDialogBuilder.setTitle("Chuckhole Patrol");
			   alertDialogBuilder
			    .setMessage("Gps is disabled!")
				.setCancelable(false)
				.setPositiveButton("OK",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						
						ChuckholeDetection.this.finish();
					}
				  });
			   
			   AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			
		}
		
		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_chuckhole_detection, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		double x = event.values[0]/9.81;
		double y = event.values[1]/9.81;
		double z = event.values[2]/9.81;
		x_acc.setText(" " + String.format("%.4f", x) + " g");
		y_acc.setText(" " + String.format("%.4f", y) + " g");
		z_acc.setText(" " + String.format("%.4f", z) + " g");
		long timestamp = System.currentTimeMillis();
		AccelData data = new AccelData(timestamp, x, y, z);
		sensordata.add(data);
		line.addNewPoints(data, sensordata.get(0).getTimestamp());
		view.repaint();
		//if((x < 0.8) && (y < 2) && (z < 0.8))
		/*if(i == 10 || i == 20)
		{
			/*String format = String.format("%%0%dd", 2);  
		     timestamp = timestamp / 1000;  
		     String seconds = String.format(format, timestamp % 60);  
		     String minutes = String.format(format, (timestamp % 3600) / 60);  
		     String hours = String.format(format, timestamp / 3600);  
		     String time =  hours + ":" + minutes + ":" + seconds;  
			
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(timestamp);
			/*String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timestamp),
		            TimeUnit.MILLISECONDS.toMinutes(timestamp) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timestamp)),
		            TimeUnit.MILLISECONDS.toSeconds(timestamp) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timestamp)));

			final String[] d = new String[]{String.valueOf(getlat()), String.valueOf(getlng()), formatter.format(calendar.getTime()), String.valueOf(x), String.valueOf(y), String.valueOf(z)};
		
			new SaveData().execute(d);
			mp.start();
			
		}*/
		//i++;
		if((x < 0.8) && (y < 0.8) && (z < 0.8))
		{
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(timestamp);

			final String[] d = new String[]{String.valueOf(getlat()), String.valueOf(getlng()), formatter.format(calendar.getTime()), String.valueOf(x), String.valueOf(y), String.valueOf(z)};
		
			new SaveData().execute(d);			
			mp.start();
			
		}
				
		
	}
	
	private class startGps extends Thread
	{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			Looper.prepare();
    		mhandler = new Handler();
    		System.out.println("Inside thread " + getId());
    		locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
    		Looper.loop();
		}
		
	}
		 
	

}



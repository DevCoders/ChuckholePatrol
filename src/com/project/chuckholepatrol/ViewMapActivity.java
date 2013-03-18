package com.project.chuckholepatrol;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.SupportMapFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Build;

public class ViewMapActivity extends FragmentActivity {

	private LatLng l;
	private GoogleMap map;
	private Cursor cs;
	DatabaseConnector dbConnector = new DatabaseConnector(ViewMapActivity.this);
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		map = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

		dbConnector.open();
        cs = dbConnector.getAllData();
        if(cs!=null)  
        {  
             cs.moveToFirst();   
             while (cs.isAfterLast() == false) {
             
            	 double lat = Double.parseDouble(cs.getString(cs.getColumnIndex("lat")));
            	 double lng = Double.parseDouble(cs.getString(cs.getColumnIndex("lng")));
            	 l = new LatLng(lat, lng);
            	 map.addMarker(new MarkerOptions()
 	            .position(l)
 	            .title("Chuckhole")
 	            .snippet("Time : " + cs.getString(cs.getColumnIndex("time")))
 	            .icon(BitmapDescriptorFactory
 	                .fromResource(R.drawable.ic_launcher)));

             }
          // Move the camera instantly to hamburg with a zoom of 15.
 	        map.moveCamera(CameraUpdateFactory.newLatLngZoom(l, 10));

 	        // Zoom in, animating the camera.
 	        map.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);
        }
        else {
        	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewMapActivity.this);

			   // set title
			   alertDialogBuilder.setTitle("Chuckhole Patrol");
			   alertDialogBuilder
			    .setMessage("No chuckholes detected!")
				.setCancelable(false)
				.setPositiveButton("OK",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						
					ViewMapActivity.this.finish();
					}
				  });
			   
			   AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
        }
        

	        
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}

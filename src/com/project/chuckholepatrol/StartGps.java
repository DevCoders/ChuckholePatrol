package com.project.chuckholepatrol;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

public class StartGps extends Activity {

	final Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_gps);
		// Show the Up button in the action bar.
		//setupActionBar();
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

		   // set title
		   alertDialogBuilder.setTitle("GPS");

		  // set dialog message
		  alertDialogBuilder
		    .setMessage("GPS is turned off! You must enable GPS to work this app normally.")
			.setCancelable(false)
			.setPositiveButton("Settings",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, close
					// current activity
					//dialog.cancel();
					Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivityForResult(intent, 1);
					Intent returnIntent = new Intent();
					setResult(RESULT_OK, returnIntent);
					finish();
				  }
				})
			  
		  .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, just close
					// the dialog box and do nothing
					//ChuckholeDetection.this.finish();
					Intent returnIntent = new Intent();
					setResult(RESULT_CANCELED, returnIntent);
					finish();
					
				}

		  });	

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
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
		getMenuInflater().inflate(R.menu.start_gps, menu);
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

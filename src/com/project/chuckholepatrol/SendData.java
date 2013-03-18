package com.project.chuckholepatrol;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
//import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

public class SendData extends ListActivity implements OnClickListener{
	
	 private CursorAdapter conAdapter;
	 
	 private Cursor cs;
	 private static String KEY_SUCCESS = "success"; 
	 private String res = "0";
	 Button bsend;
     // private final AlertDialog no_data = new AlertDialog(this);
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_data);
		String[] from = new String[] { "lat", "lng", "time" };
        int[] to = new int[] { R.id.lat, R.id.lng, R.id.time };
        conAdapter = new SimpleCursorAdapter(this, R.layout.list_layout, null, from, to);
        setListAdapter(conAdapter); 
        bsend = (Button) findViewById(R.id.bsend);
        bsend.setOnClickListener(this);
        new GetData().execute((Object[])null);
		
	}
	
	private class ServerData extends AsyncTask<Object, Object, JSONObject>
	{
		private final ProgressDialog dia = new ProgressDialog(SendData.this);
	    @Override
	   	protected void onPreExecute() {
	   		// TODO Auto-generated method stub
	   		super.onPreExecute();
	   		dia.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	   		dia.setMessage("Sending...");
	           dia.setCancelable(false);
	           dia.show();

	   	}
	       
		@Override
		protected JSONObject doInBackground(Object... arg0) {
			// TODO Auto-generated method stub
			SendServer data = new SendServer();
			JSONObject json = data.send(cs);
			
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			//if (dia.isShowing()) {
	               dia.dismiss();
	         //  }
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SendData.this);

			   // set title
			   alertDialogBuilder.setTitle("Chuckhole Patrol");
			
			try { 
                if (result.getString(KEY_SUCCESS) != null) {                      
                    res = result.getString(KEY_SUCCESS); 
                    if(Integer.parseInt(res) == 1){ 

                    	 alertDialogBuilder
            			    .setMessage("Data successfully sended to server!")
            				.setCancelable(false)
            				.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            					public void onClick(DialogInterface dialog,int id) {
            						// if this button is clicked, close
            						// current activity
            						DatabaseConnector dbconnector = new DatabaseConnector(SendData.this);
            						dbconnector.deleteData();
            						SendData.this.finish();
            					}
            				  });
            				

            				// create alert dialog
            				AlertDialog alertDialog = alertDialogBuilder.create();
            				alertDialog.show();
                    }
                    else
                    {
                    	 alertDialogBuilder
            			    .setMessage("Sending data to server failed!")
            				.setCancelable(false)
            				.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            					public void onClick(DialogInterface dialog,int id) {
            						// if this button is clicked, close
            						// current activity
            						//SendData.this.finish();
            					}
            				  });
            				

            				// create alert dialog
            				AlertDialog alertDialog = alertDialogBuilder.create();
            				alertDialog.show();
                    }
                }
			}
			catch (JSONException e) { 
                e.printStackTrace(); 
            } 

		}
		
	}
	
	
	private class GetData extends AsyncTask<Object, Object, Cursor> 
    {
       DatabaseConnector dbConnector = new DatabaseConnector(SendData.this);
       private final ProgressDialog dialog = new ProgressDialog(SendData.this);
       @Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();

	}

	@Override
       protected Cursor doInBackground(Object... params)
       {
          dbConnector.open();
          return dbConnector.getAllData(); 
       } 
       
       @Override
       protected void onPostExecute(Cursor result)
       {
    	  // if (dialog.isShowing()) {
               dialog.dismiss();
          // }
    	   
    	   if(result.getCount() == 0)
    	   {
    		   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SendData.this);

   			   // set title
   			   alertDialogBuilder.setTitle("Chuckhole Patrol");

   			  // set dialog message
   			  alertDialogBuilder
   			    .setMessage("No chuckholes detected!")
   				.setCancelable(false)
   				.setPositiveButton("OK",new DialogInterface.OnClickListener() {
   					public void onClick(DialogInterface dialog,int id) {
   						// if this button is clicked, close
   						// current activity
   						SendData.this.finish();
   					}
   				  });
   				

   				// create alert dialog
   				AlertDialog alertDialog = alertDialogBuilder.create();
   				alertDialog.show();

    	   }
    	   
          conAdapter.changeCursor(result); // set the adapter's Cursor
          cs = result;
          dbConnector.close();
       } 
    } 

	
	@Override
    protected void onStop() 
    {
       Cursor cursor = conAdapter.getCursor();
       
       if (cursor != null) 
          cursor.deactivate();
       
       conAdapter.changeCursor(null);
       super.onStop();
    }    


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send_data, menu);
		return true;
	}


	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.bsend)
			new ServerData().execute((Object[])null);
	}

}

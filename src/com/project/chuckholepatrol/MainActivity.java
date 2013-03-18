package com.project.chuckholepatrol;


import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {
	
	//Button button1, Button01, Button02;
     String[] values = new String[] {"Start Chuckhole Detection", "View Chuckholes", "About Us", "Quit"};
	

	
	 @Override
	public void onCreate(Bundle icicle) {
		    super.onCreate(icicle);
		    getListView().setBackgroundResource(R.drawable.background);
		   // ListView lv = getListView();
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		        R.layout.activity_main, R.id.label , values);
		    setListAdapter(adapter);
		  //  ((ListView)findViewById(android.R.id.list)).setBackgroundResource(R.drawable.background);
		   // getListView().setBackgroundResource(R.drawable.background);
		  }


	
	
   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_main);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_main, android.R.id.text1, stdView));
        
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        Button01 = (Button) findViewById(R.id.Button01);
        Button01.setOnClickListener(this);
        Button02 = (Button) findViewById(R.id.Button02);
        Button02.setOnClickListener(this);
    }*/

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		switch(position)
		{
			case 0:
				Intent ourIntent = new Intent(MainActivity.this, ChuckholeDetection.class);
				startActivity(ourIntent);
				break;
			
			case 1:
				Intent in = new Intent(MainActivity.this, View_graph.class);
				startActivity(in);
				break;
			
			case 2:
				Intent aboutIntent = new Intent(MainActivity.this, AboutUs.class);
				startActivity(aboutIntent);
				break;
				
			case 3:
				finish();
				break;
		}	
		
	}

	/*public void onClick(View src) {
		// TODO Auto-generated method stub

		switch(src.getId())
		{
		case R.id.button1:
			//Intent intent = new Intent(this, ChuckholeDetection.class);
			//startActivity(intent);
			break;
			
		case R.id.Button01:
			Intent i = new Intent(this, AboutUs.class);
			startActivity(i);
			break;
		
		case R.id.Button02:
			finish();
			break;
		}

		
		
	}*/
}

package com.project.chuckholepatrol;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.content.Intent;

public class SplashScreen extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        findViewById(R.id.mainSpinner1).setVisibility(View.VISIBLE);
        Thread splashTimer = new Thread(){
        	@Override
        	public void run(){
        		try{
        			sleep(5000);
        			Intent i = new Intent(SplashScreen.this , MainActivity.class);
        			startActivity(i);
        		} 
        		catch(InterruptedException e) {
        			e.printStackTrace();
        		}
        		finally {
        			finish();
        		}
        	}
        };
        splashTimer.start();
        }  

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_splash_screen, menu);
        return true;
    }
}
